package FLMfiles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Michael on 10/08/2016.
 */
public class MyAccount implements Serializable{//money related


    private double BankBalance;
    private ArrayList<Transaction> transactions;
    private double WeeklySalaries;
    private int InDebt; //Keep track of how many weeks the user is in debt for
    //In debt for 8 weeks, user is fired!
    private ArrayList<Player> StillOwe; //ArrayList containing the players that the user still owes salaries for

    static boolean fired = false;


    public MyAccount() {
        transactions = new ArrayList<>();
        StillOwe = new ArrayList<>();
        InDebt = 0;
        BankBalance = 500000;
        WeeklySalaries = 0;
    }



    /*Pass through a transaction to update the bank balance
    transaction can be purchase, sell, win game etc.
     */
    public void UpdateBank(Transaction T) throws FileNotFoundException {
        if(T.getInc()==true){
            BankBalance += T.getAmnt();
        }
        else{
            BankBalance -= T.getAmnt();
        }
        transactions.add(T);
        saveAccountDetails();
        //c.UpdateUI();
    }
   public double GetBankBalance(){
        return BankBalance;
    }

    //Get the weekly salaries
    public double GetWeeklyExpenditure() throws IOException, ClassNotFoundException {
        Database db = new Database();
        MyTeam curteam = db.loadMyTeam();
        WeeklySalaries = 0;

        ArrayList<Player> cur = curteam.getMySquad();

        for(Player P: cur){
            double Salary = P.getPSalary();
            WeeklySalaries += Salary;
        }
        return WeeklySalaries;
    }

    //Method that will determine how much the user has to spend on more wages. i.e. can the user purchase a new player
    public double CalculateNet(double value) throws IOException, ClassNotFoundException {

        double WeekSalaries = GetWeeklyExpenditure();
        double debt = CalculateDebt();
        double net = BankBalance - WeekSalaries-debt - value;
        return net;

    }
    //Pay the weekly salaries NB WEEKLY!
    //First pay the debt then current salaries
    public void PaySalaries(boolean Win) throws IOException, ClassNotFoundException {
        Database db = new Database();
        MyTeam curteam = db.loadMyTeam();

        User user = new User();
        user = user.readUser();

        MyNewsFeed myNewsFeed = new MyNewsFeed();
        myNewsFeed = myNewsFeed.readNews();

        //readAccount();

        if (StillOwe.size() != 0) {

            double AmountPaid = 0;
            double tempBank = BankBalance;

            ArrayList<Player> temp = StillOwe;

            for (int x = 0; x < StillOwe.size(); x++) {
                Player P = StillOwe.get(x);
                double Salary = P.getPSalary();
                Salary += (P.getPerformanceBonus() *Salary);
                if (tempBank > Salary) {
                    tempBank -= Salary;
                    temp.add(P);
                    AmountPaid += Salary;

                }
            }

            for (int x = 0; x < temp.size(); x++) {
                StillOwe.remove(temp.get(x));
            }

            if (StillOwe.size() == 0)
                InDebt = 0;

            if(AmountPaid > 0) {
                Transaction T = new Transaction(AmountPaid, "Debt paid", false, user.getWeek());
                UpdateBank(T);

                NewsFeedElement N = new NewsFeedElement(user.getWeek(), "Debt paid", false);
                myNewsFeed.AddNews(N);
            }
        }

        double amountPaid = 0;
        double tempBank = BankBalance;

        ArrayList<Player> cur = curteam.getMySquad();

        for(int x = 0; x<  cur.size(); x++){
            Player P = cur.get(x);
            double Salary = P.getPSalary();
            if(Win == true){
                Salary += (P.getPerformanceBonus() *Salary);
            }
            if (tempBank > Salary) {
                tempBank -= Salary;
                amountPaid += Salary;
            } else {
                StillOwe.add(P);
                InDebt++;
            }
        }

        if(amountPaid > 0) {
            Transaction T = new Transaction(amountPaid, "Salaries paid", false, user.getWeek());
            UpdateBank(T);

            NewsFeedElement N = new NewsFeedElement(user.getWeek(), "Salaries paid", false);
            myNewsFeed.AddNews(N);
        }

        boolean Fire = DetermineFiring();
        if(Fire){
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
            primaryStage.setTitle("Football League Manager");
            primaryStage.setScene(new Scene(root, 483, 448));
            primaryStage.show();

            Calculate.OpenConfirmationWindow("The board has decided that your management skills are inadequate and due to your recent performance we have decided to fire you.");
            //user is fired
        }
        fired = Fire;

        saveAccountDetails();
        //c.UpdateUI();
    }
    //current debt

    public static boolean DetermineFireBool()
    {
        return fired;
    }



    public double CalculateDebt(){

        if(StillOwe.size() == 0)
            return 0;
        else {
            double debt = 0;
            for (Player P : StillOwe) {
                double Salary = P.getPSalary();
                debt += Salary;
            }
            if (debt != 0)
                InDebt++;
            return debt;
        }
    }
    //returns true if the user has been in debt for 8 weeks or if the debt total is = 500000. Therefore, the user should be fired.
    public boolean DetermineFiring(){
        if (InDebt == 8)
            return true;
        if (CalculateDebt() == 1000000)
            return true;
        else
            return false;
    }

    //Get the transactions for the past 4 weeks, including current week
    public ArrayList<Transaction> GetTransactions1() {
        User user = new User();
        int Week = 0;
        try {
            user = user.readUser();
            Week = user.Week;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Transaction> Trans = new ArrayList<>();
        ArrayList<Transaction> Return = new ArrayList<>();
        if ((Week == 1) || (Week == 2) || (Week == 3)) {
            for (int i = 0; i < transactions.size(); i++) {
                Transaction cur = new Transaction(transactions.get(i).getDescrip(),
                        transactions.get(i).getAmnt(),
                        transactions.get(i).getInc(),
                        transactions.get(i).getWk());
                Return.add(cur);
            }
            return Return;
        } else {
            for (Transaction T : transactions) {
                if (T.getWk() > Week - 4)
                    Trans.add(T);
            }
            if (Trans.size() > 0) {
                for (int i = 0; i < Trans.size(); i++) {
                    Transaction cur = new Transaction(Trans.get(i).getDescrip(),
                            Trans.get(i).getAmnt(),
                            Trans.get(i).getInc(),
                            Trans.get(i).getWk());
                    Return.add(cur);
                }
            }

            return Return;

        }
    }

    //Get all the transactions
    public ArrayList<Transaction> GetTransactions2(){

        ArrayList<Transaction> Return = new ArrayList<>();
        for(int i = 0; i<transactions.size(); i++){
            Transaction cur = new Transaction(transactions.get(i).getDescrip(),
                    transactions.get(i).getAmnt(),
                    transactions.get(i).getInc(),
                    transactions.get(i).getWk());
            Return.add(cur);
        }
        return Return;
    }

    public void saveAccountDetails() throws FileNotFoundException {

        try{
            File file = new File("accountDetails.obj");
            file.delete();

            FileOutputStream fout = new FileOutputStream("accountDetails.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            System.out.println("Saved");

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    public MyAccount readAccount() throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream("accountDetails.obj");
        ObjectInputStream ois = new ObjectInputStream(fin);
        MyAccount myAccount = (MyAccount) ois.readObject();
        fin.close();
        ois.close();
        return myAccount;
    }
}

