package FLMfiles;

import java.util.ArrayList;

/**
 * Created by Michael on 10/08/2016.
 */
public class MyAccount {//money related

    private double BankBalance;
    private ArrayList<Transaction> transactions;
    private double WeeklySalaries;
    private MyTeam myTeam;
    private int InDebt; //Keep track of how many weeks the user is in debt for
    //In debt for 8 weeks, user is fired!
    private ArrayList<Player> StillOwe; //ArrayList containing the players that the user still owes salaries for

    public MyAccount(MyTeam T){
        transactions = new ArrayList<>();
        StillOwe = new ArrayList<>();
        myTeam = T;
        InDebt = 0;
        BankBalance = 500000;
        WeeklySalaries = 0;
    }


    /*Pass through a transaction to update the bank balance
    transaction can be purchase, sell, win game etc.
     */
    public void UpdateBank(Transaction T){
        if(T.getIncome().equals("Income")){
            BankBalance += T.getAmount();
        }
        else{
            BankBalance -= T.getAmount();
        }
        transactions.add(T);
    }
    public double GetBankBalance(){
        return BankBalance;
    }
    //Get the weekly salaries
    public double GetWeeklyExpenditure(){
        WeeklySalaries = 0;

        ArrayList<Player> cur = myTeam.getMySquad();

        for(Player P: cur){
            double Salary = P.getPSalary();
            WeeklySalaries += Salary;
        }
        return WeeklySalaries;
    }

    //Method that will determine how much the user has to spend on more wages. i.e. can the user purchase a new player
    public double CalculateNet(){

        double WeekSalaries = GetWeeklyExpenditure();
        double debt = CalculateDebt();
        double net = BankBalance - WeekSalaries-debt;
        return net;

    }
    //Pay the weekly salaries NB WEEKLY!
    //First pay the debt then current salaries
    public void PaySalaries() {

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

        ArrayList<Player> cur = myTeam.getMySquad();

        for (Player P : cur) {
            double Salary = P.getPSalary();
            if (BankBalance > Salary) {
                BankBalance = BankBalance - Salary;
            } else {
                StillOwe.add(P);
                InDebt++;
            }
        }
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
    //returns true if the user has been in debt for 8 weeks. Therefore, the user should be fired.
    public boolean DetermineFiring(){
        if (InDebt == 8)
            return true;
        else
            return false;
    }

    //Get the transactions for the past 4 weeks, including current week
    public ArrayList<Transaction> GetTransactions1(){
        ArrayList<Transaction> Trans = new ArrayList<>();
        int Week = League.Week;
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
}
