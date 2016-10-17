package FLMfiles;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Michael on 10/09/2016.
 */
public class PlayController implements Initializable{

    public static Transaction T;
    public static boolean Result; //True if win, otherwise false

    HomeController homeController = new HomeController();
    PrePlayController prePlayController = new PrePlayController();
    ObservableList<liveFeedElement> obsFeed = FXCollections.observableArrayList();

    PostFixture pf = new PostFixture();

    public ListView listLivefeed;

    public Label lblHomeTeam;
    public Label lblAwayTeam;
    public Label lblScore;
    public Label lblTime;

    public Button btnOK;
    IntegerProperty TimeProperty = new SimpleIntegerProperty(0);
    ProgressBar Progressbar = new ProgressBar();
    League league = new League();
    //<editor-fold desc="InGame">
    public void onOKFinishedGame(Event event) throws IOException, ClassNotFoundException {
        PrePlayController.DisplayGame = PrePlayController.getNextLeagueGame(league);
        League.addPostFixture(pf);
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }
    //</editor-fold>


    //<editor-fold desc="ALGORITHM">
    public PostFixture LMAlogrithm(PreFixture preFixture){
        MyTeam home = preFixture.getHomeTeam();
        MyTeam away = preFixture.getAwayTeam();


        return Game(home, away);
    }

    public PostFixture Game(MyTeam AttackTeam, MyTeam DefendTeam){

        int MyScore = 0;
        int OppositionScore = 0;

        TeamTree teamTree = new TeamTree(AttackTeam, DefendTeam);

        int MaxTime = 90;//Game done when 90
        int Time = 0;//current time


        while(Time < MaxTime)
        {
            PlayerNode curNode = teamTree.getRoot();

            while((curNode.hasnext() != false)&&(Time < MaxTime))
            {
                Random Decisionpass = new Random();
                Player curPlayer = curNode.getValue();
                double prob = (double) Decisionpass.nextInt(100);
                if(curNode.getMiddle().getValue()==null)//shoot
                {
                    boolean goal = curNode.shoot(curNode.getDefendervalue());

                    if(goal) {
                        MyScore = MyScore + 1;
                        if(AttackTeam.getTeamID() == 1||DefendTeam.getTeamID()==1)
                            obsFeed.add(new liveFeedElement(Time, curPlayer.getPName().toString() +  " has scored a Goal!"));
                    }
                    else
                    if(curNode.getDefendervalue() != null)
                        if(AttackTeam.getTeamID() == 1||DefendTeam.getTeamID()==1)
                            obsFeed.add(new liveFeedElement(Time, teamTree.Goalie.getValue().getPName() + " has saved a Goal."));
                    curNode = teamTree.getRoot();
                }
                else
                if( prob < ((curPlayer.getPAvgRating()- curPlayer.PInjuryPenalty) - (curNode.getDefendervalue().getPDefRating() - curNode.getDefendervalue().PInjuryPenalty)/1.5))
                {
                    if(Decisionpass.nextInt(100) < ((curPlayer.getPAvgRating()-curPlayer.PInjuryPenalty)/3)){
                        curNode = curNode.bestPass();
                    }
                    else
                        curNode = curNode.RandomPass();
                }

                Time = Time + 1;
                TimeProperty.setValue(Time);
            }
        }

        //teamTree
        pf = new PostFixture(AttackTeam, DefendTeam, String.valueOf(MyScore));
        return pf;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Load fixture here
        Database db = new Database();
        db.connectToDB();

        PreFixture preFixture = prePlayController.DisplayGame;

        preFixture.getAwayTeam();
        lblHomeTeam.setText(preFixture.getHomeTeam().getTName());
        lblAwayTeam.setText(preFixture.getAwayTeam().getTName());
        lblTime.setText("90:00");

        //Load fixture here
        PostFixture result = LMAlogrithm(preFixture);

        //DECREASE OR INCREASE FATIGUE
        MyTeam myTeam = preFixture.getHomeTeam();
        for(int i = 0; i < myTeam.getMySquad().size(); i++)
        {
            Player curPlayer = myTeam.getMySquad().get(i);
            if(curPlayer.isStartLineUp()==true)
                curPlayer.DecrFatigue();
            else
                curPlayer.RestPlayer();

            db.UpdateFatigue(curPlayer);
            db.UpdatePInjury(curPlayer);
        }

        PostFixture result2 = LMAlogrithm(new PreFixture(preFixture.getAwayTeam(), preFixture.getHomeTeam()));
        lblScore.setText(result.getResult() + " : " + result2.getResult());
        Collections.sort(obsFeed, new Comparator<liveFeedElement>() {
            @Override
            public int compare(liveFeedElement o1, liveFeedElement o2) {
                return o1.Time - o2.Time ;
            }
        });
        listLivefeed.setItems(obsFeed);
        pf.setResult(result.getResult() + " : " + result2.getResult());
        pf.setHome(preFixture.getHomeTeam());
        pf.setAway(preFixture.getAwayTeam());
        Progressbar.progressProperty().bind(TimeProperty);




        //Determine win/loss and create transaction

        String Score = pf.getResult();
        User user = new User();
        try {
            user = user.readUser();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String[] parts = Score.split(":");
        String part1 = parts[0];
        String part2 = parts[1];

        part1 = part1.replaceAll("\\s+","");
        part2 = part2.replaceAll("\\s+","");


        if (part1.equals(part2)) { //Draw

            T = new Transaction(100000, "Draw", true,user.getWeek());
            Result = false;
            //myAccount.UpdateBank(T);

        } else if (part1.compareTo(part2)>0) { //Win

            T = new Transaction(300000, "Win", true,user.getWeek());
            Result = true;
            db.setMatchWinnerLoser(1, preFixture.getAwayTeam().getTeamID());
            //myAccount.UpdateBank(T);
            //update in DB

        } else //Lose
        {
            T = new Transaction(20000, "Loss", true,user.getWeek());
            Result = false;
            db.setMatchWinnerLoser(preFixture.getAwayTeam().getTeamID(),1);
            //myAccount.UpdateBank(T);
        }


        //AI vs AI
        try {
            league.LoadBotPreFixtures();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //load existing results


        //save results in memory
        for(int i = 0; i < 10; i++)
        {
            PreFixture preFixture1 = league.fixtures.pop();
            Player[] playersAway = preFixture1.getAwayTeam().generateTeamPlayers();
            preFixture1.getAwayTeam().setStartingLineUp(playersAway);
            Player[] playersHome = preFixture1.getHomeTeam().generateTeamPlayers();
            preFixture1.getHomeTeam().setStartingLineUp(playersHome);

            //play fixture
            PostFixture postFixture1 = LMAlogrithm(preFixture1);
            //save result in txtfile
            PostFixture postFixture2 = LMAlogrithm(new PreFixture(preFixture1.getAwayTeam(), preFixture1.getHomeTeam()));
            postFixture1.setResult(postFixture1.getResult() + " : " + postFixture2.getResult());
            league.postfixtures.push(postFixture1);

            String[] scorep = postFixture1.getResult().split(":");
            String scorep1 = parts[0];
            String scorep2 = parts[1];

            scorep1 = scorep1.replaceAll("\\s+","");
            scorep2 = scorep2.replaceAll("\\s+","");


            int tID1 = preFixture1.getHomeTeam().getTeamID();
            int tID2 = preFixture1.getAwayTeam().getTeamID();
            //update database

            if (scorep1.compareTo(scorep2)>0)
            {
                //win team/losing team
                db.setMatchWinnerLoser(tID1, tID2);
            }
            if (scorep1.compareTo(scorep2)<0)
            {
                //win team/losing team
                db.setMatchWinnerLoser(tID2, tID1);
            }
        }

        //save results in textfile and database
        try {
            league.SaveBotPostFixtures();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get ruan's fixtures

        //let them play and get result.

        //place results in new textfile

    }


    //</editor-fold>
}
