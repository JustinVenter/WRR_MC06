package FLMfiles;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    //<editor-fold desc="Editor tab">
    public League league = new League();
    public Button btnPlayMatch;
    public Label lblHomeTeam;
    public Label lblHomeAvgRating;
    public Label lblAwayTeam;
    public Label lblAwayAvgRating;
    public Label lblHomeAtt;
    public Label lblHomeDef;
    public Label lblAwayAtt;
    public Label lblAwayDef;

    public HomeController()
    {}

    public void onPlayClick(Event event) throws InterruptedException {
        //play game
        OpenSimulationWindow();
    }

    public void OpenSimulationWindow()
    {
        Parent root = null;
        Stage secondaryStage = new Stage();
        try {

            root = FXMLLoader.load(getClass().getResource("PlayScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Football League Manager");
        secondaryStage.setScene(new Scene(root, 750, 600));
        secondaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        League league = new League();

        PreFixture curGame = getLeagueGame(league);


        lblHomeTeam.setText(curGame.getHomeTeam().getTName());
        lblHomeAvgRating.setText(String.valueOf((int)Math.floor(curGame.getHomeTeam().getTRating())));
        lblAwayTeam.setText(curGame.getAwayTeam().getTName());
        lblAwayAvgRating.setText(String.valueOf((int)Math.floor(curGame.getAwayTeam().getTRating())));

        lblHomeAtt.setText(String.valueOf(curGame.getHomeTeam().getTAttRating()));
        lblHomeDef.setText(String.valueOf(curGame.getHomeTeam().getTDefRating()));
        lblAwayAtt.setText(String.valueOf(curGame.getAwayTeam().getTAttRating()));;
        lblAwayDef.setText(String.valueOf(curGame.getAwayTeam().getTDefRating()));;
    }

    public PreFixture getLeagueGame(League league)
    {
        league.loadGames();
        PreFixture oneGame = league.fixtures.pop();

        return oneGame;
    }

    //</editor-fold>



}