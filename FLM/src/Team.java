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
    int TDraw;


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

    public int getTDraw() {
        return TDraw;
    }

    public void setTDraw(int TDraw) {
        this.TDraw = TDraw;
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


//</editor-fold>

    //Methods

    //views (such as display)
}
