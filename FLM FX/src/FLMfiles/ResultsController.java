package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

import static FLMfiles.League.LoadMyPostFixtures;

/**
 * Created by rjoos on 9/29/2016.
 */
public class ResultsController implements Initializable {


    public ListView lbxPostFixtures;


    //Instance of class League
    League league= new League();
    // ArrayList to be wrapped by observable list.
    private ArrayList PostMatches= new ArrayList<>();
    private ObservableList observablePostMatches= FXCollections.observableArrayList(PostMatches);

    public void PopulateList() throws IOException, ClassNotFoundException {


        Stack<PostFixture> PostFixtures = LoadMyPostFixtures();
        Stack<PostFixture> temp= PostFixtures;

        while(!PostFixtures.isEmpty())
        {
            PostFixture popped= temp.pop();
            PostFixture cur= new PostFixture(popped.getHomeTeamName(),popped.getAwayTeamName(),popped.getResult());
            observablePostMatches.add(cur.toString());
        }



    }


    public void onBackClicked(Event event) throws IOException {

        AnchorPane mainAnchor = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManageScreen.fxml")));
    }

    public void setupList() throws IOException, ClassNotFoundException {

        PopulateList();
        lbxPostFixtures.setItems(observablePostMatches);

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
