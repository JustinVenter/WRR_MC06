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

    private Market market;

    private MyAccount account;


    public PurchasePController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CurPlayerLbl.setText(CurPlayer.getName());
        CurPlayerSalary.setText("$" + String.valueOf(CurPlayer.getPlayerSalary()));



    }
    public void onPurchaseClick(Event event) throws InterruptedException, IOException, ClassNotFoundException {

        double SalOffer = Double.parseDouble(Salary.getText());
        double BonusOffer = Double.parseDouble(PerformanceBonus.getText());

        double net = account.CalculateNet(); //Determines the bank balance after taking into account debt and weekly salaries
        if(net >SalOffer) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure you want to make this offer");

            ButtonType buttonTypeOne = new ButtonType("Make offer");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                DeterminePurchase(SalOffer, BonusOffer);
                OpenConfirmationWindow("Offer has been placed. The player will respond within the next few weeks");
                OpenTransferWindow();


            } else {
                // ... user chose CANCEL or closed the dialog
                OpenConfirmationWindow("Offer canceled");
                OpenTransferWindow();
            }
        }
        else{
            OpenConfirmationWindow("You do not have sufficient funds to pay this salary");
            OpenTransferWindow();
        }

    }
    private void OpenConfirmationWindow(String m){
        Parent root = null;
        Stage newStage = new Stage();

        VBox comp = new VBox();
        TextField message = new TextField(m);
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

        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.show();
    }

    public void OpenTransferWindow()
    {
        Parent root = null;
        Stage secondaryStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("MarketScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Football League Manager");
        secondaryStage.setScene(new Scene(root, 750, 600));
        secondaryStage.show();
    }

    private void DeterminePurchase(double Sal, double B){
        double CurSalary = Double.parseDouble(CurPlayerSalary.getText());

        Random R = new Random();
        int X = R.nextInt(2)+0;
        int increase = X;

        double PRating = CurPlayer.getPAvgRating();

        double bonus = (B/100)*Sal;
        double Total = bonus + Sal;

        double WantedTotal;

        if(PRating <=65){
            X += 4;
            WantedTotal = ((X/100)*Sal) + Sal;

            if(Total>= WantedTotal){ //Player accepts offer

                market.AddAcceptedPlayer(CurPlayer);
            }
            else{
                market.AddRejectedPlayer(CurPlayer);
            }

        }
        if((PRating >65)&&(PRating<=73)){
            X += 6;

            WantedTotal = ((X/100)*Sal) + Sal;

            if(Total>= WantedTotal){ //Player accepts offer
                market.AddAcceptedPlayer(CurPlayer);
            }
            else{

                market.AddRejectedPlayer(CurPlayer);
            }
        }
        if((PRating >73)&&(PRating<=79)){
            X += 9;

            WantedTotal = ((X/100)*Sal) + Sal;

            if(Total>= WantedTotal){ //Player accepts offer
                market.AddAcceptedPlayer(CurPlayer);
            }
            else{

                market.AddRejectedPlayer(CurPlayer);
            }

        }
        if((PRating >79)&&(PRating<=85)){
            X += 12;

            WantedTotal = ((X/100)*Sal) + Sal;

            if(Total>= WantedTotal){ //Player accepts offer
                market.AddAcceptedPlayer(CurPlayer);
            }
            else{

                market.AddRejectedPlayer(CurPlayer);
            }

        }
        if(PRating >85){
            X += 15;

            WantedTotal = ((X/100)*Sal) + Sal;

            if(Total>= WantedTotal){ //Player accepts offer
                market.AddAcceptedPlayer(CurPlayer);
            }
            else{
                market.AddRejectedPlayer(CurPlayer);
            }

        }
    }

}
