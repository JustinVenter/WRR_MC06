package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by rjoos on 10/20/2016.
 */
public class LeagueTableController implements Initializable {
    public TableView tblTeams;
    public TableColumn teamPos;
    public TableColumn teamName;
    public TableColumn teamWins;
    public TableColumn teamLosses;


    private static ArrayList<MyTeam> teams= new ArrayList<>();
    private static ObservableList<MyTeam> observableTeams= FXCollections.observableArrayList(teams);


    private void setupTeams(){

        ArrayList<MyTeam> LeagueTeams= League.insertionSort();

        int pos= 1;
        for(int i=0; i<LeagueTeams.size() ;i++){

            LeagueTeams.get(i).setTPos(pos);
            MyTeam cur= new MyTeam(LeagueTeams.get(i).getTPos(),
                    LeagueTeams.get(i).getTName(),
                    LeagueTeams.get(i).getTWins(),
                    LeagueTeams.get(i).getTLosses());

            observableTeams.add(cur);
            pos++;

        }
        tableSetup();
        tblTeams.setItems(observableTeams);
        tblTeams.getSelectionModel().selectFirst();

    }

    private void tableSetup(){

        teamPos.setCellValueFactory(new PropertyValueFactory<>("TPos"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("PtyTName"));
        teamWins.setCellValueFactory(new PropertyValueFactory<>("PtyTWins"));
        teamLosses.setCellValueFactory(new PropertyValueFactory<>("PtyTLosses"));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        observableTeams.clear();
        setupTeams();
    }

    public void btnBackClicked(ActionEvent actionEvent) throws IOException {

        AnchorPane mainAnchor = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManageScreen.fxml")));
    }
}
