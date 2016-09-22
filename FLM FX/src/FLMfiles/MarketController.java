package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by rjoos on 9/20/2016.
 */
public class MarketController implements Initializable{
    /**
     * For Market related code, changes were made this this class ( which is new), changes were made to class player, and a correction had to be made to database.
     * Everything works perfectly, all that needs to change is at the purchase button we must check that the user playing the game has enough money etc.
     */

    public TableView tblPlayers;
    public TableColumn playerSur;
    public TableColumn playerName;
    public TableColumn playerPos;
    public TableColumn playerAtt;
    public TableColumn playerDef;
    public TableColumn playerAvg;
    public TableColumn playerPrice;
    public TableColumn playerSalary;
    public Button btnPurchase;

    // database
    Database db= new Database();
    // ArrayList of players
    private ArrayList<Player> players = new ArrayList<>();
    //Arraylist wrapped in observable list
    private ObservableList<Player> observablePlayers= FXCollections.observableArrayList(players);



    private void setupPlayers(){

        db.connectToDB();
        ArrayList<Player> MarketPlayers= db.LoadPlayerMarket();

        // run through this arraylist and add to the observable list.

        for(int i=0;i<MarketPlayers.size();i++){

            Player cur= new Player(MarketPlayers.get(i).getPlayerID(),
                    MarketPlayers.get(i).getPSurname(),
                    MarketPlayers.get(i).getPName(),
                    MarketPlayers.get(i).getPPos(),
                    MarketPlayers.get(i).getPAttRating(),
                    MarketPlayers.get(i).getPDefRating(),
                    MarketPlayers.get(i).getPAvgRating(),
                    MarketPlayers.get(i).getPValue(),
                    MarketPlayers.get(i).getPSalary());

            observablePlayers.add(cur);

        }

        // set players into the table
        tableSetup();
        tblPlayers.setItems(observablePlayers);
        // event handlers
        tblPlayers.getSelectionModel().selectFirst();

    }

    public void tableSetup(){

        // playerID.setCellValueFactory(new PropertyValueFactory<>("playerNum"));
        playerSur.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        playerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        playerPos.setCellValueFactory(new PropertyValueFactory<>("playerPos"));
        playerAtt.setCellValueFactory(new PropertyValueFactory<>("playerAtt"));
        playerDef.setCellValueFactory(new PropertyValueFactory<>("playerDef"));
        playerAvg.setCellValueFactory(new PropertyValueFactory<>("playerAverage"));
        playerPrice.setCellValueFactory(new PropertyValueFactory<>("playerPrice"));
        playerSalary.setCellValueFactory(new PropertyValueFactory<>("playerSalary"));

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupPlayers();
    }


    public void onPurchaseClicked() {


        btnPurchase.setOnMouseClicked(Event ->{

            // Here must still be a section where the users' account is checked to have the sufficient amount of money !!! All else works

            db.UpdatePlayerTeam(observablePlayers.get(tblPlayers.getSelectionModel().getSelectedIndex()),1);
            //refresh the list after the player has been purchased :)
            observablePlayers.remove(observablePlayers.get(tblPlayers.getSelectionModel().getSelectedIndex()));
            tblPlayers.refresh();


        } );


    }
}
