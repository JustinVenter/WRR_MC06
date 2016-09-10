package FLMfiles;

/**
 * Created by Michael on 09/08/2016.
 */
public class PostFixture {
    //Attributes found in Database

    int FixtureID;
    int HomeTeamID;
    int AwayTeamID;
    String Result;

    public PostFixture(Team Home, Team Away, String Result){

        HomeTeamID=Home.getTeamID();
        AwayTeamID=Away.getTeamID();
        this.Result = Result;
        // Have another parameter called WinningTeam
        // Then use the algorithm to calculate the final result exp WinningResult=CalcGameResult();
        // result=calcGameResult(); -- Could return the winnning team name but must return something else if it is a draw.
        // Got a
    }
    //Methods

    //getters and setters

    public int getFixtureID() {
        return FixtureID;
    }

    public void setFixtureID(int fixtureID) {
        FixtureID = fixtureID;
    }

    public int getHomeTeamID() {
        return HomeTeamID;
    }

    public void setHomeTeamID(int homeTeamID) {
        HomeTeamID = homeTeamID;
    }

    public int getAwayTeamID() {
        return AwayTeamID;
    }

    public void setAwayTeamID(int awayTeamID) {
        AwayTeamID = awayTeamID;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String winningTeam) {
        Result = winningTeam;
    }

    //views (such as display)

    public void display()
    {
        System.out.println("PostFixture ID: " + FixtureID + ", " + Result);
    }
}
