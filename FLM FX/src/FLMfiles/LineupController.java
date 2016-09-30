package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Michael on 12/09/2016.
 */
public class LineupController implements Initializable{
    MyTeam myteam;
    Database db = new Database();
    Player[] oldLineup;

    //Position ArrayLists
    ArrayList<Player> LFList = new ArrayList<>();
    ArrayList<Player> CFList = new ArrayList<>();
    ArrayList<Player> RFList = new ArrayList<>();
    ArrayList<Player> LMList = new ArrayList<>();
    ArrayList<Player> CMList = new ArrayList<>();
    ArrayList<Player> RMList = new ArrayList<>();
    ArrayList<Player> LBList = new ArrayList<>();
    ArrayList<Player> LMBList = new ArrayList<>();
    ArrayList<Player> RBList = new ArrayList<>();
    ArrayList<Player> RMBList = new ArrayList<>();
    ArrayList<Player> GKList = new ArrayList<>();
    
    //UI Items
    public ComboBox<Player> LFbox;
    public ComboBox<Player> CFbox;
    public ComboBox<Player> RFbox;
    public ComboBox<Player> LMbox;
    public ComboBox<Player> CMbox;
    public ComboBox<Player> RMbox;
    public ComboBox<Player> LBbox;
    public ComboBox<Player> RMBbox;
    public ComboBox<Player> RBbox;
    public ComboBox<Player> LMBbox;
    public ComboBox<Player> GKbox;

    public Label lblOverallR;
    public Label lblAttackR;
    public Label lblDefenceR;
    public Label lblStaff;
    public Label lblConfidence;


    public Button btnCancel;

    public void onCancelClick(Event event) throws IOException {
        AnchorPane curPane = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        curPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("PrePlay.fxml")));
        myteam.setStartingLineUp(oldLineup);
    }

    public void divideTeam(){
        LFList = myteam.getPosPlayers("LF");
        CFList = myteam.getPosPlayers("CF");
        RFList = myteam.getPosPlayers("RF");

        LMList = myteam.getPosPlayers("LM");
        CMList = myteam.getPosPlayers("CM");
        RMList = myteam.getPosPlayers("RM");

        LBList = myteam.getPosPlayers("LB");
        RMBList = myteam.getPosPlayers("RMB");
        RBList = myteam.getPosPlayers("RB");
        LMBList = myteam.getPosPlayers("LMB");

        GKList = myteam.getPosPlayers("GK");
    }

    public void setupComboboxes(){
        divideTeam();

        Player[] myStart = myteam.getStartingLineUp();

        LFbox.setItems(FXCollections.observableList(LFList));
        LFbox.getSelectionModel().select(myStart[0]);

        CFbox.setItems(FXCollections.observableList(CFList));
        CFbox.getSelectionModel().select(myStart[1]);

        RFbox.setItems(FXCollections.observableList(RFList));
        RFbox.getSelectionModel().select(myStart[2]);


        LMbox.setItems(FXCollections.observableList(LMList));
        LMbox.getSelectionModel().select(myStart[3]);
        CMbox.setItems(FXCollections.observableList(CMList));
        CMbox.getSelectionModel().select(myStart[4]);
        RMbox.setItems(FXCollections.observableList(RMList));
        RMbox.getSelectionModel().select(myStart[5]);

        LBbox.setItems(FXCollections.observableList(LBList));
        LBbox.getSelectionModel().select(myStart[6]);
        LMBbox.setItems(FXCollections.observableList(LMBList));
        LMBbox.getSelectionModel().select(myStart[7]);
        RMBbox.setItems(FXCollections.observableList(RMBList));
        RMBbox.getSelectionModel().select(myStart[8]);
        RBbox.setItems(FXCollections.observableList(RBList));
        RBbox.getSelectionModel().select(myStart[9]);

        GKbox.setItems(FXCollections.observableList(GKList));
        GKbox.getSelectionModel().select(myStart[10]);
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db.connectToDB();
        try {
            myteam = db.loadMyTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setupComboboxes();

        oldLineup = myteam.StartingLineup;
        recalc();
    }

    public void recalc()
    {
        myteam.CalculateAll();
        lblOverallR.setText(String.valueOf(myteam.getTRating()));
        lblAttackR.setText(String.valueOf(myteam.getTAttRating()));
        lblDefenceR.setText(String.valueOf(myteam.getTDefRating()));
        lblStaff.setText(String.valueOf(myteam.StaffRating));
        lblConfidence.setText(String.valueOf(myteam.Confidence));
    }

    public void CFaction(ActionEvent actionEvent) {
        Player selected = CFbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[1]= selected;
        recalc();
    }

    public void RFaction(ActionEvent actionEvent) {
        Player selected = RFbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[2]= selected;
        recalc();
    }

    public void LFaction(ActionEvent actionEvent) {
        Player selected = LFbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[0]= selected;
        recalc();
    }


    public void LMaction(ActionEvent actionEvent) {
        Player selected = LMbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[3]= selected;
        recalc();
    }

    public void CMaction(ActionEvent actionEvent) {
        Player selected = CMbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[4]= selected;
        recalc();
    }

    public void RMaction(ActionEvent actionEvent) {
        Player selected = RMbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[5]= selected;
        recalc();
    }

    public void RBaction(ActionEvent actionEvent) {
        Player selected = RBbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[9]= selected;
        recalc();
    }

    public void LMBaction(ActionEvent actionEvent) {
        Player selected = LMBbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[7]= selected;
        recalc();
    }

    public void LBaction(ActionEvent actionEvent) {
        Player selected = LBbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[6]= selected;
        recalc();
    }

    public void RMBaction(ActionEvent actionEvent) {
        Player selected = RMBbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[8]= selected;
        recalc();
    }

    public void GKaction(ActionEvent actionEvent) {
        Player selected = GKbox.getSelectionModel().getSelectedItem();
        myteam.StartingLineUp[10]= selected;
        recalc();
    }

    public void onSaveExitClick(Event event) throws IOException, SQLException {
        db.UpdateStartingLineUp(myteam.getMySquad(), myteam.getStartingLineUp());
        onCancelClick(event);
    }

    public void onSaveClick(Event event) throws SQLException {
        db.UpdateStartingLineUp(myteam.getMySquad(), myteam.getStartingLineUp());
    }
}
