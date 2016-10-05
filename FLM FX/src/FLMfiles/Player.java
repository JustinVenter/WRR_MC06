package FLMfiles;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Created by Michael on 09/08/2016.
 */
public class Player implements Serializable{

    // The following is the String and int properties needed to display information in a table
    transient IntegerProperty playerNum= new SimpleIntegerProperty();
    transient StringProperty Surname = new SimpleStringProperty();
    transient StringProperty Name= new SimpleStringProperty();
    transient StringProperty playerPos= new SimpleStringProperty();
    transient IntegerProperty playerAtt= new SimpleIntegerProperty();
    transient  IntegerProperty playerDef= new SimpleIntegerProperty();
    transient DoubleProperty playerAverage= new SimpleDoubleProperty();
    transient DoubleProperty playerPrice = new SimpleDoubleProperty();
    transient DoubleProperty playerSalary= new SimpleDoubleProperty();
    transient DoubleProperty playerInjuryPenalty= new SimpleDoubleProperty();

    // for MarketController, this is needed
    public Player(int playerNum, String surname, String name, String playerPos, int playerAtt, int playerDef, double playerAverage, double playerPrice, double playerSalary) {
        this.playerNum.set(playerNum) ;
        Surname.set(surname);
        Name.set(name);
        this.playerPos.set(playerPos);
        this.playerAtt.set(playerAtt);
        this.playerDef.set(playerDef);
        this.playerAverage.set(playerAverage);
        this.playerPrice.set(playerPrice);
        this.playerSalary.set(playerSalary);
        this.PInjury = false;
    }
    // getters for marketController do not remove !!! (used through reflection when adding the data to the table)
    public int getPlayerNum() {
        return playerNum.get();
    }

    public IntegerProperty playerNumProperty() {
        return playerNum;
    }

    public String getSurname() {
        return Surname.get();
    }

    public StringProperty surnameProperty() {
        return Surname;
    }

    public String getName() {
        return Name.get();
    }

    public StringProperty nameProperty() {
        return Name;
    }

    public String getPlayerPos() {
        return playerPos.get();
    }

    public StringProperty playerPosProperty() {
        return playerPos;
    }

    public int getPlayerAtt() {
        return playerAtt.get();
    }

    public IntegerProperty playerAttProperty() {
        return playerAtt;
    }

    public int getPlayerDef() {
        return playerDef.get();
    }

    public IntegerProperty playerDefProperty() {
        return playerDef;
    }

    public double getPlayerAverage() {
        return playerAverage.get();
    }

    public DoubleProperty playerAverageProperty() {
        return playerAverage;
    }

    public double getPlayerPrice() {
        return playerPrice.get();
    }

    public DoubleProperty playerPriceProperty() {
        return playerPrice;
    }

    public double getPlayerSalary() {
        return playerSalary.get();
    }

    public DoubleProperty playerSalaryProperty() {
        return playerSalary;
    }

    Database db = new Database();
    //<editor-fold desc="Attributes found in Database">
    int PlayerID;
    int TeamID;
    boolean StartLineUp;
    String PName;
    String PSurname;
    double PAvgRating;
    int PAttRating;
    int PDefRating;
    String PPos;
    String PSkill;  //actual name of the skill [Normal(default)/Striker(attack)/Playmaker(midfield)/PowerDefender(defense)]
    int PFatigue; //his current fatigue level, with 100 being max and, 0 meaning injury.
    double PSalary;
    double PValue; //the player's buy and sell value
    int PContract; //time remaining in weeks
    boolean PInjury;
    int PFatigueLvl; //1 being wost and 4 being max
    //</editor-fold>

    //<editor-fold desc="Methods">
    public Player(int playerID, int teamID, boolean startLineUp, String PName, String PSurname, double PAvgRating, int PAttRating, int PDefRating, String PPos, String PSkill, int PFatigue, double PSalary, double PValue, int PContract, boolean PInjury, int PFatigueLvl) {
        PlayerID = playerID;
        TeamID = teamID;
        StartLineUp = startLineUp;
        this.PName = PName;
        this.PSurname = PSurname;
        this.PAvgRating = PAvgRating;
        this.PAttRating = PAttRating;
        this.PDefRating = PDefRating;
        this.PPos = PPos;
        this.PSkill = PSkill;
        this.PFatigue = PFatigue;
        this.PSalary = PSalary;
        this.PValue = PValue;
        this.PContract = PContract;
        this.PInjury = PInjury;
        this.PFatigueLvl = PFatigueLvl;
    }

    public Player(String PName, double PAvgRating, int PAttRating, int PDefRating)//used for bots
    {
        this.PName = PName;
        this.PAvgRating = PAvgRating;
        this.PAttRating = PAttRating;
        this.PDefRating = PDefRating;
    }
    public Player(double PAvgRating, int PAttRating, int PDefRating)//used for bots
    {
        this.PAvgRating = PAvgRating;
        this.PAttRating = PAttRating;
        this.PDefRating = PDefRating;
    }


    /**
     * Set the current player's team to the market
     * TeamID = 0 is the ID of the Market
     */
    public void SellPlayer(double MyAccount)
    {
        setTeamID(0);
        //need to add more
    }

    /**
     * if starting lineup is false then this will be called on that player
     * Once an injured player has obtained an fatigue level of 60 or higher he is no longer injured
     */
    public void RestPlayer()
    {
        switch (this.PFatigueLvl) {

            case 2:
                if (this.PFatigue < 100) {
                    this.setPFatigue(PFatigue + 15);
                    if (PFatigue > 100)
                        this.setPFatigue(100);
                }
                break;
            case 3:
                if (this.PFatigue < 100) {
                    this.setPFatigue(PFatigue + 20);
                    if (PFatigue > 100)
                        this.setPFatigue(100);
                }
                break;
            case 4:
                if (this.PFatigue < 100) {
                    this.setPFatigue(PFatigue+25);
                    if (PFatigue > 100)
                        this.setPFatigue(100);
                }
                break;
            default:
                if (this.PFatigue < 100) {
                    this.setPFatigue(PFatigue + 10);
                    if (PFatigue > 100)
                        this.setPFatigue(100);
                }
                break;
        }
        if(this.PInjury == true && this.PFatigue >= 60)
            this.setPInjury(false);

    }

    /**
     *
     * @param Weeks how long you would like to extend his/her contract
     * @param MyAccount = the amount of Money you have available
     * @return returns the amount of money you will have left after this transaction.  Returns -1 if you cannot afford it.
     * If you can afford this transaction it should return the new total you have available.
     */
    public double IncreaseContract(int Weeks, double MyAccount)
    {
        double payment = PayPlayer(MyAccount);
        if (payment != -1)
        {
            this.setPContract(this.PContract + Weeks);
            return MyAccount + payment;
        }
        else
            return -1;
    }

    /**
     *
     * @param MyAccount the amount of Money you have available
     * @return returns the amount of money you will have left after this transaction.  Returns -1 if you cannot afford it.
     * If you can afford this transaction it should return the new total you have available.
     * Once a player is payed it is assumed that a week has passed(players will be payed weekly)
     */
    public double PayPlayer(double MyAccount)
    {
        if(PSalary > MyAccount)//cannot afford
            return -1;
        else {
            this.setPContract(PContract -1);
            return MyAccount - PSalary;
        }
    }

    public void DecrFatigue()
    {
        if (this.PFatigue > 0) {
            this.setPFatigue(PFatigue - 10);
            if(this.PFatigue <= 40)
                this.setPInjury(true);
        }
        else
        {
            this.setPInjury(true);
            this.setPFatigue(0);
        }
    }

    public void TerminateContract()
    {
        this.setPContract(0);
        this.setTeamID(0);
    }


    //</editor-fold>

    //<editor-fold desc="getters and setters">
    public int getPlayerID () {
        return PlayerID;
    }

    public int getTeamID() {
        return TeamID;
    }

    public void setTeamID(int teamID) {
        TeamID = teamID;
        db.UpdatePlayerTeam(this, teamID);
    }

    public boolean isStartLineUp() {
        return StartLineUp;
    }

    public void setStartLineUp(boolean startLineUp) {
        StartLineUp = startLineUp;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getPSurname() {
        return PSurname;
    }

    public void setPSurname(String PSurname) {
        this.PSurname = PSurname;
    }

    public double getPAvgRating() {
        return PAvgRating;
    }

    public void setPAvgRating(double PAvgRating) {
        this.PAvgRating = PAvgRating;
    }

    public int getPAttRating() {
        return PAttRating;
    }

    public int getPDefRating() {
        return PDefRating;
    }

    public String getPPos() {
        return PPos;
    }

    public String getPSkill() {
        return PSkill;
    }

    public int getPFatigue() {
        return PFatigue;
    }

    public void setPFatigue(int PFatigue) {
        this.PFatigue = PFatigue;
    }

    public double getPSalary() {
        return PSalary;
    }

    public double getPValue() {
        return PValue;
    }

    public int getPContract() {
        if(PContract == 0)
        {
            this.setTeamID(0);
        }
        return PContract;
    }

    public void setPContract(int PContract) {
        this.PContract = PContract;
        db.UpdateContract(this, PContract);
    }

    public boolean isPInjury() {
        return PInjury;
    }

    public void setPInjury(boolean PInjury) {
        this.PInjury = PInjury;
    }

    public int getPFatigueLvl() {
        return PFatigueLvl;
    }

    public void setPFatigueLvl(int PFatigueLvl) {
        this.PFatigueLvl = PFatigueLvl;
    }
//</editor-fold>

    //<editor-fold desc="views (such as display)">
    public String toString()
    {
        String InjuryStatus = "";
        if(this.PInjury == true)
            InjuryStatus = "(Injured)";
        return  PSurname +", " +PName + ": " + PAvgRating + " " + InjuryStatus;
    }

    //</editor-fold>


}