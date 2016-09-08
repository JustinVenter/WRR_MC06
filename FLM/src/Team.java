import java.util.Random;

/**
 * Created by Michael on 09/08/2016.
 */
public class Team {
    //Attributes found in Database
    int TeamID;
    String TName;
    double TRating;
    int TAttRating;
    int TDefRating;
    String TCity;
    int TRank;
    int TWins;
    int TLosses;

    Player[] StartingLineup = new Player[11];

    public Team(int teamID, String TName, double TRating, int TAttRating, int TDefRating, String TCity, int TRank, int TWins, int TLosses) {
        TeamID = teamID;
        this.TName = TName;
        this.TRating = TRating;
        this.TAttRating = TAttRating;
        this.TDefRating = TDefRating;
        this.TCity = TCity;
        this.TRank = TRank;
        this.TWins = TWins;
        this.TLosses = TLosses;
    }


    //<editor-fold desc="getters and setters">
    public double getTRating() {
        return TRating;
    }

    public void setTRating(double TRating) {
        this.TRating = TRating;
    }

    public int getTRank() {
        return TRank;
    }

    public void setTRank(int TRank) {
        this.TRank = TRank;
    }

    public int getTWins() {
        return TWins;
    }

    public void setTWins(int TWins) {
        this.TWins = TWins;
    }

    public int getTLosses() {
        return TLosses;
    }

    public void setTLosses(int TLosses) {
        this.TLosses = TLosses;
    }

    public String getTCity() {
        return TCity;
    }

    public void setTCity(String TCity) {
        this.TCity = TCity;
    }

    public int getTDefRating() {
        return TDefRating;
    }

    public void setTDefRating(int TDefRating) {
        this.TDefRating = TDefRating;
    }

    public int getTAttRating() {
        return TAttRating;
    }

    public void setTAttRating(int TAttRating) {
        this.TAttRating = TAttRating;
    }

    public String getTName() {
        return TName;
    }

    public void setTName(String TName) {
        this.TName = TName;
    }

    public int getTeamID() {
        return TeamID;
    }

    public void setTeamID(int teamID) {
        TeamID = teamID;
    }

    /**
     * structure of the array:
     *      pos[0-2] = attack,      90% attack, 10% defence
     *      pos[3-5] = midfield.    50% attack, 50% defence
     *      pos[6-9] = defence      10% attack, 90% defence
     *      pos[10]  = goalie.      0%  attack, 100% defence.
     *
     */
    public Player[] generateTeamPlayers()
    {

        Random random =  new Random();
        Player[] botTeam = new Player[11];

        for(int i = 0; i < 11; i++)
        {
            if(i < 3) {
                int AttR = this.getTAttRating() + random.nextInt(10);
                int DefR = (int)(Math.floor(this.getTDefRating()*.4));
                botTeam[i] = new Player((((AttR * 1.9)+(DefR * 0.1))/2), AttR, DefR);
            }
            if(3<=i && i<6)
            {
                int AttR = (int)(Math.floor(this.getTRating()));
                int DefR = (int)(Math.floor(this.getTRating()));
                botTeam[i] = new Player(this.getTRating(), AttR, DefR);
            }
            if(6<=i && i<=9)
            {
                int DefR = this.getTDefRating() + random.nextInt(10);
                int AttR = (int)(Math.floor(this.getTAttRating()*.4));
                botTeam[i] = new Player((((DefR * 1.9)+(AttR * 0.1))/2), AttR, DefR);
            }
            if(i == 10) {
                botTeam[i] = new Player(this.getTRating(), (int)(Math.floor(this.getTRating())), (int)(Math.floor(this.getTRating())));
            }
        }
        return botTeam;
    }


//</editor-fold>

    //Methods

    //views (such as display)
}
