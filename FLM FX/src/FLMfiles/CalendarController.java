package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by rjoos on 9/25/2016.
 */
public class CalendarController {

    public ListView lbxPrefixtures;
    public Button btnBack;
    //Instance of class League
    League league= new League();
    // ArrayList to be wrapped by observable list.
    private ArrayList<PreFixture> PreMatches= new ArrayList<>();
    private ObservableList<PreFixture> observablePreMatches= FXCollections.observableArrayList(PreMatches);

    public void PopulateList() throws IOException, ClassNotFoundException {
        // this will generate the PreFixtures.
       // league.MyTeamPreFixture();
        //Stack<PreFixture> MyTeamPreFixtures = league.LoadMyPreFixtures();

    }



    public void onBackClicked(Event event) {
    }
}
