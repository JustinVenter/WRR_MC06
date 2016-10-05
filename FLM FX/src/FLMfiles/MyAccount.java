package FLMfiles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;

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

    Database db = new Database();

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
        if(T.getIncome().equals("Income")){
            BankBalance += T.getAmount();
        }
        else{
            BankBalance -= T.getAmount();
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
    public double CalculateNet() throws IOException, ClassNotFoundException {

        double WeekSalaries = GetWeeklyExpenditure();
        double debt = CalculateDebt();
        double net = BankBalance - WeekSalaries-debt;
        return net;

    }
    //Pay the weekly salaries NB WEEKLY!
    //First pay the debt then current salaries
    public void PaySalaries() throws IOException, ClassNotFoundException {
        MyTeam curteam = db.loadMyTeam();

        if (StillOwe.size() != 0) {

            for (int x = 0; x < StillOwe.size(); x++) {
                Player P = StillOwe.get(x);
                double Salary = P.getPSalary();
                if (BankBalance > Salary) {
                    BankBalance -= Salary;
                    StillOwe.remove(x);
                }
                if (StillOwe.size() == 0)
                    InDebt = 0;
            }
        }

        ArrayList<Player> cur = curteam.getMySquad();

        for (Player P : cur) {
            double Salary = P.getPSalary();
            if (BankBalance > Salary) {
                BankBalance -= Salary;
            } else {
                StillOwe.add(P);
                InDebt++;
            }
        }
        boolean Fire = DetermineFiring();
        if(Fire){
            //user is fired
        }

        saveAccountDetails();
        //c.UpdateUI();
    }
    //current debt
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
        if (CalculateDebt() == 500000)
            return true;
        else
            return false;
    }

    //Get the transactions for the past 4 weeks, including current week
    public ArrayList<Transaction> GetTransactions1(){
        User user = new User();
        int Week = 0;
        try {
            user = user.readUser();
            Week  = user.Week;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Transaction> Trans = new ArrayList<>();
        if((Week == 1) || (Week == 2)|| (Week == 3))
            return Trans = transactions;
        else{
            for(Transaction T: transactions){
                if(T.getWeek()> Week - 4)
                    Trans.add(T);
            }

        }
        return Trans;
    }
    //Get all the transactions
    public ArrayList<Transaction> GetTransactions2(){

        return transactions;
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
