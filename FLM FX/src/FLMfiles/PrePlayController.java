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
import java.util.Stack;

import static FLMfiles.League.LoadMyPostFixtures;

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
    static PreFixture  curGame;
    static PreFixture DisplayGame = null;
    HomeController hc = new HomeController();

    public AnchorPane mainAnchor;
    public AnchorPane PlayAnchor;
    public AnchorPane AccountAnchor;
    public Pane curPane1;

    public void onPlayClick(Event event) throws InterruptedException, IOException, ClassNotFoundException {

        //A week passes

        MyAccount myAccount = new MyAccount();
        try {
            myAccount = myAccount.readAccount();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //Let all the games be played that should be played (All)

        //update Post/pre game fixtures and save and check if there are any games remaining.  If not then Start new Season (Ruan)

        //update next team to be played(Michael/Ruan)

        //Change to next fixture (Michael)

        //update player injury and rest stats

        //play game

        OpenSimulationWindow();


        //Determine if win or loss
        myAccount.UpdateBank(PlayController.T);


       /* Stack<PostFixture> PostFixtures = LoadMyPostFixtures();
        Stack<PostFixture> temp = PostFixtures;
        PostFixture popped = temp.pop();
        PostFixture cur = new PostFixture(popped.getHomeTeamName(), popped.getAwayTeamName(), popped.getResult());
        */

        /*String result = cur.getResult();

        User user = new User();

        user = user.readUser();

        String[] parts = result.split(":");
        String part1 = parts[0];
        String part2 = parts[1];

        part1 = part1.replaceAll("\\s+","");
        part2 = part2.replaceAll("\\s+","");


        if (part1.equals(part2)) { //Draw

            Transaction T = new Transaction(100000, "Draw", true,user.getWeek());
            myAccount.UpdateBank(T);

        } else if (part1.compareTo(part2)>0) { //Win

            Transaction T = new Transaction(300000, "Win", true,user.getWeek());
            myAccount.UpdateBank(T);

        } else //Lose
        {
            Transaction T = new Transaction(20000, "Lose", true,user.getWeek());
            myAccount.UpdateBank(T);
        }
        */

        //Pay all players (Justin)


        myAccount.PaySalaries(PlayController.Result);

        //Check for any responses from pending players (market stuff!)
        Market market = new Market();
        market = market.readMarket();

        market.AddWeek();
        market.DetermineAccepted();
        market.DetermineRejected();
        market.DetermineSold();

        market.saveMarketDetails();

        newWeek();

    }

    public void OpenSimulationWindow()
    {
        PreFixture preFixture = null;

        try {
            DisplayGame = getLeagueGame(league);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lblHomeTeam.setText(DisplayGame.getHomeTeam().getTName());
        lblHomeAvgRating.setText(String.valueOf((int)Math.floor(DisplayGame.getHomeTeam().getTRating())));
        lblAwayTeam.setText(DisplayGame.getAwayTeam().getTName());
        lblAwayAvgRating.setText(String.valueOf((int)Math.floor(DisplayGame.getAwayTeam().getTRating())));

        lblHomeAtt.setText(String.valueOf(DisplayGame.getHomeTeam().getTAttRating()));
        lblHomeDef.setText(String.valueOf(DisplayGame.getHomeTeam().getTDefRating()));
        lblAwayAtt.setText(String.valueOf(DisplayGame.getAwayTeam().getTAttRating()));
        lblAwayDef.setText(String.valueOf(DisplayGame.getAwayTeam().getTDefRating()));


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

        try {
            DisplayGame = getLeagueGame(league);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MyTeam AwayT = DisplayGame.getAwayTeam();
        MyTeam HomeT = DisplayGame.getHomeTeam();
        HomeT.CalculateAll();
        AwayT.CalculateAll();
        lblHomeTeam.setText(DisplayGame.getHomeTeam().getTName());
        lblHomeAvgRating.setText(String.valueOf((int)Math.floor(DisplayGame.getHomeTeam().getTRating())));
        lblAwayTeam.setText(DisplayGame.getAwayTeam().getTName());
        lblAwayAvgRating.setText(String.valueOf((int)Math.floor(DisplayGame.getAwayTeam().getTRating())));

        lblHomeAtt.setText(String.valueOf(DisplayGame.getHomeTeam().getTAttRating()));
        lblHomeDef.setText(String.valueOf(DisplayGame.getHomeTeam().getTDefRating()));
        lblAwayAtt.setText(String.valueOf(DisplayGame.getAwayTeam().getTAttRating()));
        lblAwayDef.setText(String.valueOf(DisplayGame.getAwayTeam().getTDefRating()));


    }

    public static PreFixture getLeagueGame(League league) throws IOException, ClassNotFoundException {
        PreFixture oneGame = league.loadCurGame();
        curGame = oneGame;
        return oneGame;
    }

    public static PreFixture getNextLeagueGame(League league) throws IOException, ClassNotFoundException {
        PreFixture oneGame = league.loadNextGame();
        curGame = oneGame;

        try {
            DisplayGame = getLeagueGame(league);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return oneGame;
    }

    public void onLineupClicked(Event event) throws IOException {
        curPane1 = (Pane) ((Node)event.getSource()).getParent();
        AnchorPane curPane = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        // these two of them return the same stage
        // Swap screen

        curPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("Lineup.fxml")));
    }

    public void newWeek()
    {
        try {
            User user = new User();
            user = user.readUser();
            int w = user.getWeek() + 1;
            user.setWeek(w);
            user.saveUserDetails();
            Label lblw = (Label) btnPlayMatch.getParent().getParent().getParent().getParent().getParent().lookup("#lblWeek");
            System.out.println(lblw.toString());
            lblw.setText(String.valueOf(w));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
