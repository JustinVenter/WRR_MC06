package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * Created by rjoos on 9/25/2016.
 */
public class CalendarController implements Initializable {

    public ListView lbxPrefixtures;
    public Button btnBack;

    //Instance of class League
    League league= new League();
    // ArrayList to be wrapped by observable list.
    private ArrayList PreMatches= new ArrayList<>();
    private ObservableList observablePreMatches= FXCollections.observableArrayList(PreMatches);

    public void PopulateList() throws IOException, ClassNotFoundException {

        // this will generate the PreFixtures. Might have to do this once the game is started by the user.
      //  league.MyTeamPreFixture();
       // league.SaveMyPreFixtures();

        Stack<PreFixture> MyTeamPreFixtures = league.LoadMyPreFixtures();

        while(!MyTeamPreFixtures.isEmpty())
        {
            PreFixture popped= MyTeamPreFixtures.pop();
            PreFixture cur= new PreFixture(popped.getHomeTeam(),popped.getAwayTeam());
            observablePreMatches.add(cur.toString());
        }



    }
    public void setupList() throws IOException, ClassNotFoundException {

        PopulateList();
        lbxPrefixtures.setItems(observablePreMatches);
    }



    public void onBackClicked(Event event) throws IOException {

        AnchorPane mainAnchor = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManageScreen.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            setupList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
