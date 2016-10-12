package FLMfiles;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
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

    PostFixture pf = new PostFixture();

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

                    if(goal)
                        MyScore = MyScore + 1;
                    curNode = teamTree.getRoot();
                }
                else
                if( prob < (curPlayer.getPAvgRating() - (curNode.getDefendervalue().getPDefRating()/3)))
                {
                    if(Decisionpass.nextInt(100) < (curPlayer.getPAvgRating()/2))
                        curNode = curNode.bestPass();
                    else
                        curNode.RandomPass();
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
        }

        PostFixture result2 = LMAlogrithm(new PreFixture(preFixture.getAwayTeam(), preFixture.getHomeTeam()));
        lblScore.setText(result.getResult() + " : " + result2.getResult());
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
            //myAccount.UpdateBank(T);

        } else //Lose
        {
            T = new Transaction(20000, "Loss", true,user.getWeek());
            Result = false;
            //myAccount.UpdateBank(T);
        }
    }


    //</editor-fold>
}
