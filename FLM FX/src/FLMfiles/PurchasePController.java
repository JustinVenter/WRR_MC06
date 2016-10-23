package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Michael on 14/09/2016.
 */


public class PurchasePController implements Initializable {

    public Label CurPlayerLbl;
    public Label CurPlayerSalary;
    public TextField Salary;
    public TextField PerformanceBonus;
    public Button PurchasePlayer;

    private Player CurPlayer;

    private Market market = new Market();

    private MyAccount account = new MyAccount();

    Database DB = new Database();

    private boolean purchased;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CurPlayer = MarketController.CurPlayer; //CurPlayer set in the market

        CurPlayerLbl.setText(CurPlayer.getName());
        CurPlayerSalary.setText("$" + String.valueOf(CurPlayer.getPlayerSalary()));

        purchased = false;



    }
    public void onPurchaseClick(Event event) throws InterruptedException, IOException, ClassNotFoundException {

        //Bank will only be affected when the player actually accepts the offer

        Stage stage = (Stage) PurchasePlayer.getScene().getWindow();

        double CurPlayerValue = CurPlayer.getPValue();

        double SalOffer = Double.parseDouble(Salary.getText());
        double BonusOffer = Double.parseDouble(PerformanceBonus.getText());

        account = account.readAccount();

        double net = account.CalculateNet(CurPlayerValue); //Determines the bank balance after taking into initial value, account debt and weekly salaries
        if(net >SalOffer) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure you want to make this offer");

            //ButtonType buttonTypeOne = new ButtonType("Make offer",ButtonBar.ButtonData.OK_DONE);
            //ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            //alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();


            if (result.get() == ButtonType.OK) {

                DeterminePurchase(SalOffer, BonusOffer);
                OpenConfirmationWindow("Offer has been placed. The player will respond within the next few weeks");
                //OpenTransferWindow();


            } else {
                // ... user chose CANCEL or closed the dialog
                OpenConfirmationWindow("Offer canceled");
                //OpenTransferWindow();
            }
        }
        else{
            OpenConfirmationWindow("You do not have sufficient funds to pay this salary");
            //OpenTransferWindow();
        }
        stage.close();

    }
    private void OpenConfirmationWindow(String m){
        Parent root = null;
        Stage newStage = new Stage();

        VBox comp = new VBox();
        Label message = new Label(m);
        //TextField message = new TextField(m);
        Button OK = new Button("OK");
        comp.getChildren().add(message);
        comp.getChildren().add(OK);

        OK.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                newStage.close();
            }
        });

        Scene stageScene = new Scene(comp, 100, 100);
        newStage.setScene(stageScene);
        newStage.show();
    }


    private void DeterminePurchase(double Sal, double B) throws IOException, ClassNotFoundException {
        //If adding player into accepted arrayList, then need to update database to player's new salary and performance bonus

        market = market.readMarket();

        Random R = new Random();
        int X = R.nextInt(2)+0;

        //CurPlayer = MarketController.CurPlayer;

        double PRating = CurPlayer.getPlayerAverage();
        double PSalary = CurPlayer.getPlayerSalary();

        double bonus = (B/100)*Sal;
        double Total = bonus + Sal;

        double WantedTotal;

        if(PRating <=65){
            X += 4;
            WantedTotal = (((X/100)*PSalary) + PSalary);

            if(Total>= WantedTotal){ //Player accepts offer

                AdjustValues(Sal,B);
                //market.AddAcceptedPlayer(CurPlayer);
                purchased = true;
            }
            else{
                purchased = false;
            }

        }
        if((PRating >65)&&(PRating<=73)){
            X += 6;

            WantedTotal = (((X/100)*PSalary) + PSalary);

            if(Total>= WantedTotal){ //Player accepts offer
                AdjustValues(Sal,B);
                //market.AddAcceptedPlayer(CurPlayer);
                purchased = true;
            }
            else{

                purchased = false;
            }
        }
        if((PRating >73)&&(PRating<=79)){
            X += 9;

            WantedTotal = (((X/100)*PSalary) + PSalary);

            if(Total>= WantedTotal){ //Player accepts offer
                AdjustValues(Sal,B);
                //market.AddAcceptedPlayer(CurPlayer);
                purchased = true;
            }
            else{

                purchased = false;
            }

        }
        if((PRating >79)&&(PRating<=85)){
            X += 12;

            WantedTotal = (((X/100)*PSalary) + PSalary);

            if(Total>= WantedTotal){ //Player accepts offer
                AdjustValues(Sal,B);
                //market.AddAcceptedPlayer(CurPlayer);
                purchased = true;
            }
            else{

                purchased = false;
            }

        }
        if(PRating >85){
            X += 15;

            WantedTotal = (((X/100)*PSalary) + PSalary);

            if(Total>= WantedTotal){ //Player accepts offer
                AdjustValues(Sal,B);
                //market.AddAcceptedPlayer(CurPlayer);
                purchased = true;
            }
            else{
                purchased = false;
            }

        }

        if(purchased == true){

            int weeks = SetPendingWeeks();
            Player P = new Player(CurPlayer.getPlayerNum(),weeks,CurPlayer.getPlayerPrice());
            market.AddAcceptedPlayer(P);

            market.saveMarketDetails(); //N.B. Save the new market details!
            MarketController.get().remove(CurPlayer);
            DB.connectToDB();
            DB.UpdatePlayerTeam(CurPlayer, -1);
            DB.Close();
        }
        else{
            int weeks = SetPendingWeeks();
            Player P = new Player(CurPlayer.getPlayerNum(),weeks, CurPlayer.getPlayerPrice());
            market.AddRejectedPlayer(P);

            market.saveMarketDetails(); //N.B. Save the new market details!
            MarketController.get().remove(CurPlayer);
            DB.connectToDB();
            DB.UpdatePlayerTeam(CurPlayer, -1);
            DB.Close();
        }
    }
    private void AdjustValues(double Salary, double bonus){

        DB.connectToDB();
        DB.UpdatePlayerSalary(CurPlayer, Salary);
        DB.UpdatePlayerBonus(CurPlayer, bonus);
        DB.Close();


    }
    private int SetPendingWeeks(){ //Player can take 2 to 4 weeks to respond
        Random R = new Random();
        int X = R.nextInt(3)+2;
        System.out.println("****************************************************************************");
        System.out.println("WEEKS = "+ X);

        return X;
    }

    public void onUpgradeClicked(Event event) {
    }
}
