import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Michael on 09/08/2016.
 */
public class MyTeam extends Team{
    //Custom attributes, data structures
    ArrayList<Player> MySquad;
    Player[] StartingLineUp;


    //Attributes found in Database
    int Confidence;
    int StaffRating;
    double MyAccount;

    private int[] StaffCost = {0, 1000, 5000, 10000, 20000, 50000, 100000, 150000, 200000, 300000, 500000}; //cost for upgrades
    //Methods

    /**
     * call method from "Database" to open database.  Then load you current team
     */
    public void loadSquad()
    {
        //method from DB
    }

    public void StartingLineup(Player[] T)
    {
        int r = 0;
        for (Player p:T)
        {
            StartingLineUp[r] = p;
            r = r +1;
        }
    }

    /**
     *
     * @param Stafflvl your current staff level
     * @param MyAccount  My current account balance
     * @return if true then transaction was successful else not sufficient funds
     */
    public boolean UpgradeStaff(int Stafflvl, double MyAccount)
    {
        switch(Stafflvl)
        {
            //still need to insert benefits
            case 0:
                if(isValidBuy(MyAccount, StaffCost[1]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[1];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 1:
                if(isValidBuy(MyAccount, StaffCost[2]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[2];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 2:
                if(isValidBuy(MyAccount, StaffCost[3]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[3];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 3:
                if(isValidBuy(MyAccount, StaffCost[4]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[4];
                    this.StaffRating = this.StaffRating + 1;
                    return true ;
                }else
                    return false;
            case 4:
                if(isValidBuy(MyAccount, StaffCost[5]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[5];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 5:
                if(isValidBuy(MyAccount, StaffCost[6]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[6];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 6:
                if(isValidBuy(MyAccount, StaffCost[7]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[7];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 7:
                if(isValidBuy(MyAccount, StaffCost[8]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[8];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 8:
                if(isValidBuy(MyAccount, StaffCost[9]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[9];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;
            case 9:
                if(isValidBuy(MyAccount, StaffCost[10]) == true){
                    this.MyAccount = this.MyAccount - StaffCost[10];
                    this.StaffRating = this.StaffRating + 1;
                    return true;
                }else
                    return false;

            default: return false;
        }
    }

    public boolean isValidBuy(double mymoney, int cost)
    {
        if(mymoney > cost)
            return true;
        else
            return false;
    }

    /**
     * structure of the array:
     *      pos[0-2] = attack,      90% attack, 10% defence
     *      pos[3-5] = midfield.    50% attack, 50% defence
     *      pos[6-9] = defence      10% attack, 90% defence
     *      pos[10]  = goalie.      0%  attack, 100% defence.
     *
     * Call if changes are made to lineup!
     */
    public void CalculateAvgAttack()//remember to use use the starting lineup ratings
    {
        int attacktotal = 0;
        int defencetotal = 0;
        int midfieldtotal = 0;

        if(StartingLineUp.length == 11)//full lineup
        {
            for(int i = 0; i <=2; i++)
                attacktotal = StartingLineUp[i].getPAttRating();
            for(int i = 3; i <=5; i++)
                midfieldtotal = StartingLineUp[i].getPAttRating();
            for(int i = 6; i <=9; i++)
                defencetotal = StartingLineUp[i].getPAttRating();
        }

        double averageAttack = ((attacktotal*1.9) + (midfieldtotal) + (defencetotal * 0.1))/10;
        this.setTAttRating((int)(Math.floor(averageAttack))); //might cause issues
    }

    public void CalculateAvgDefence()//remember to use use the starting lineup ratings
    {
        double attacktotal = 0;
        double defencetotal = 0;
        double midfieldtotal = 0;
        double  goalie = 0;

        if(StartingLineUp.length == 11)//full lineup
        {
            for(int i = 0; i <=2; i++)
                attacktotal = StartingLineUp[i].getPDefRating();
            for(int i = 3; i <=5; i++)
                midfieldtotal = StartingLineUp[i].getPDefRating();
            for(int i = 6; i <=9; i++)
                defencetotal = StartingLineUp[i].getPDefRating();
            goalie = StartingLineUp[10].getPDefRating();
        }
        else
        {
            return;
        }
        double averageDefence = ((attacktotal*0.1) + (midfieldtotal) + (defencetotal * 1.9)) + goalie/10;
        this.setTAttRating((int)(Math.floor(averageDefence))); //might cause issues because it is an int and a double
    }

    public void CalculateAvgRating()//remember to use use the starting lineup ratings
    {
        if(StartingLineUp.length == 11)//full lineup
        {
            double total = 0;
            for(int i = 0; i <=10; i++)
                total = StartingLineUp[i].getPAvgRating();
            this.setTRating(total/11);
        }
        else
            return;
    }


}
