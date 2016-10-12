package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
   // public TableColumn playerSur;
    public TableColumn playerName;
    public TableColumn playerAge;
    public TableColumn playerPos;
    //public TableColumn playerAtt;
    //public TableColumn playerDef;
    public TableColumn playerAvg;
    public TableColumn playerSkill;
    public TableColumn playerPrice;
    public TableColumn playerSalary;
    public Button btnPurchase;
    public Button btnBack;
    // database
    Database db= new Database();
    // ArrayList of players
    private static ArrayList<Player> players = new ArrayList<>();
    //Arraylist wrapped in observable list
    private static ObservableList<Player> observablePlayers= FXCollections.observableArrayList(players);

    public static ObservableList<Player> get(){
        return observablePlayers;
    }

    MyAccount account = new MyAccount();

    public static Player CurPlayer = null; //Keep track of current player to send through to purchasePlayer Controller


    private void setupPlayers(){

        db.connectToDB();
        ArrayList<Player> MarketPlayers= db.LoadPlayerMarket();

        // run through this arraylist and add to the observable list.

        for(int i=0;i<MarketPlayers.size();i++){

            Player cur= new Player(MarketPlayers.get(i).getPlayerID(),
                    MarketPlayers.get(i).GetFullName(),
                    MarketPlayers.get(i).getPAge(),
                    MarketPlayers.get(i).getPPos(),
                    MarketPlayers.get(i).getPAvgRating(),
                    MarketPlayers.get(i).getPSkill(),
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
       //playerSur.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        playerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        playerAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        playerPos.setCellValueFactory(new PropertyValueFactory<>("playerPos"));
       // playerAtt.setCellValueFactory(new PropertyValueFactory<>("playerAtt"));
        //playerDef.setCellValueFactory(new PropertyValueFactory<>("playerDef"));
        playerAvg.setCellValueFactory(new PropertyValueFactory<>("playerAverage"));
        playerSkill.setCellValueFactory(new PropertyValueFactory<>("playerSkill"));
        playerPrice.setCellValueFactory(new PropertyValueFactory<>("playerPrice"));
        playerSalary.setCellValueFactory(new PropertyValueFactory<>("playerSalary"));

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupPlayers();
    }


    public void onPurchaseClicked() {


        btnPurchase.setOnMouseClicked(Event ->{
            try {
                account.readAccount();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            // Here must still be a section where the users' account is checked to have the sufficient amount of money !!! All else works
            //Check if user can pay initial value.

            double BankBalance = account.GetBankBalance();
            Player P = observablePlayers.get(tblPlayers.getSelectionModel().getSelectedIndex());
            double PValue = P.getPValue();

            if(BankBalance < PValue){
                //user cannot afford to pay initial value.
                //Popup
            }
            else
            {
                CurPlayer = observablePlayers.get(tblPlayers.getSelectionModel().getSelectedIndex());
                //Go to purchasePlayer screen

                Parent root = null;
                Stage secondaryStage = new Stage();
                try {
                    root = FXMLLoader.load(getClass().getResource("PurchasePlayer.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                secondaryStage.setTitle("Football League Manager");
                secondaryStage.setScene(new Scene(root, 750, 600));
                secondaryStage.initModality(Modality.APPLICATION_MODAL); //Cannot interact with any other stage until this stage is closed
                secondaryStage.show();
            }



            //db.UpdatePlayerTeam(observablePlayers.get(tblPlayers.getSelectionModel().getSelectedIndex()),1);
            //refresh the list after the player has been purchased :)
            //observablePlayers.remove(observablePlayers.get(tblPlayers.getSelectionModel().getSelectedIndex()));
            //tblPlayers.refresh();
        } );
    }

    public void onBackClicked(Event event) throws IOException {
        AnchorPane mainAnchor = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManageScreen.fxml")));
    }
}
