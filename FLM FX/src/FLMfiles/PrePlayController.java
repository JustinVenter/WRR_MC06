package FLMfiles;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Michael on 27/09/2016.
 */
public class PrePlayController implements Initializable{
    public League league = new League();
    public Button btnPlayMatch;
    public Button btnEdtLineup;
    public Label lblHomeTeam;
    public Label lblHomeAvgRating;
    public Label lblAwayTeam;
    public Label lblAwayAvgRating;
    public Label lblHomeAtt;
    public Label lblHomeDef;
    public Label lblAwayAtt;
    public Label lblAwayDef;

    public AnchorPane mainAnchor;
    public AnchorPane PlayAnchor;
    public AnchorPane AccountAnchor;
    public Pane curPane1;

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
        MyTeam AwayT =  curGame.getAwayTeam();
        MyTeam HomeT = curGame.getHomeTeam();
        HomeT.CalculateAll();
        AwayT.CalculateAll();
        lblHomeTeam.setText(curGame.getHomeTeam().getTName());
        lblHomeAvgRating.setText(String.valueOf((int)Math.floor(curGame.getHomeTeam().getTRating())));
        lblAwayTeam.setText(curGame.getAwayTeam().getTName());
        lblAwayAvgRating.setText(String.valueOf((int)Math.floor(curGame.getAwayTeam().getTRating())));

        lblHomeAtt.setText(String.valueOf(curGame.getHomeTeam().getTAttRating()));
        lblHomeDef.setText(String.valueOf(curGame.getHomeTeam().getTDefRating()));
        lblAwayAtt.setText(String.valueOf(curGame.getAwayTeam().getTAttRating()));
        lblAwayDef.setText(String.valueOf(curGame.getAwayTeam().getTDefRating()));


    }

    public PreFixture getLeagueGame(League league)
    {
        league.loadGames();
        PreFixture oneGame = league.fixtures.pop();

        return oneGame;
    }

    public void onLineupClicked(Event event) throws IOException {
        curPane1 = (Pane) ((Node)event.getSource()).getParent();
        AnchorPane curPane = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        // these two of them return the same stage
        // Swap screen

        curPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("Lineup.fxml")));
    }
}
