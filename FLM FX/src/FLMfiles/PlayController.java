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

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Michael on 10/09/2016.
 */
public class PlayController implements Initializable{

    HomeController homeController = new HomeController();

    public Label lblHomeTeam;
    public Label lblAwayTeam;
    public Label lblScore;
    public Label lblTime;

    public Button btnOK;
    IntegerProperty TimeProperty = new SimpleIntegerProperty(0);
    ProgressBar Progressbar = new ProgressBar();

    //<editor-fold desc="InGame">
    public void onOKFinishedGame(Event event) {
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

        //teamTree.

        return new PostFixture(AttackTeam, DefendTeam, String.valueOf(MyScore));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        League league = homeController.league;
        PreFixture preFixture =  homeController.getLeagueGame(league);
        lblHomeTeam.setText(preFixture.getHomeTeam().getTName());
        lblAwayTeam.setText(preFixture.getAwayTeam().getTName());
        PostFixture result = LMAlogrithm(preFixture);
        PostFixture result2 = LMAlogrithm(preFixture);
        lblScore.setText(result.getResult() + " : " + result2.getResult());
        Progressbar.progressProperty().bind(TimeProperty);
    }


    //</editor-fold>
}
