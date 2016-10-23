package FLMfiles;

import java.io.Serializable;

/**
 * Created by Michael on 09/08/2016.
 */
public class PostFixture implements Serializable{
    //Attributes found in Database

    int FixtureID;
    int HomeTeamID;
    int AwayTeamID;
    String Result;
    String HomeTeamName;
    String AwayTeamName;

    public MyTeam getHome() {
        return Home;
    }

    public void setHome(MyTeam home) {
        Home = home;
    }

    public MyTeam getAway() {
        return Away;
    }

    public void setAway(MyTeam away) {
        Away = away;
    }

    MyTeam Home;
    MyTeam Away;

    public PostFixture(MyTeam Home, MyTeam Away, String Result){

        // HomeTeamID = Home.getTeamID();
        // AwayTeamID = Away.getTeamID();
        this.Home = Home;
        this.Away = Away;

        HomeTeamName=Home.getTName();
        AwayTeamName=Away.getTName();
        this.Result = Result;
        // Have another parameter called WinningTeam
        // Then use the algorithm to calculate the final result exp WinningResult=CalcGameResult();
        // result=calcGameResult(); -- Could return the winnning team name but must return something else if it is a draw.
        // Got a
    }
    public PostFixture(){}
    public PostFixture(String Home, String Away, String Result){

        HomeTeamName= Home;
        AwayTeamName=Away;
        this.Result= Result;

    }



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

    public String getHomeTeamName() {
        return HomeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        HomeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return AwayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        AwayTeamName = awayTeamName;
    }

    //views (such as display)


    @Override
    public String toString() {
        return  AwayTeamName + "  VS  "+ HomeTeamName+   "           "+ Result;
    }
}
