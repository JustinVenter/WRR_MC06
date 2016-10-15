package FLMfiles;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Michael on 29/09/2016.
 */
public class LoginController implements Initializable{
    public TextField edtUsername;
    public TextField edtTeam;
    public TextField edtCity;
    public Label lblError;
    public Button btnSubmit;
    public Label lblSuccess;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onSubmitClick(Event event) throws IOException, ClassNotFoundException {

        Database db = new Database();



        String TN = edtTeam.getText().toString();
        String TC = edtCity.getText().toString();
        League.MyTeamPreFixture();
        League.SaveMyPreFixtures();
        League.SaveMyPostFixtures();
        League.BotTeamPreFixture();
        League.SaveBotPreFixtures();
        System.out.println(TN);
        db.connectToDB();
        db.CreateTeam(TN, TC);
        String Message = "*Please fill in all the fields";
        if ((edtUsername.getText().trim().isEmpty()) || (edtCity.getText().trim().isEmpty()) || (edtTeam.getText().trim().isEmpty())) {
            lblError.setText(Message);
            return;
        } else {
            User user = new User(edtUsername.getText().toString(), edtTeam.getText().toString(), edtCity.getText().toString());
            user.saveUserDetails();

            //Create new instance of MyAccount and Save in object file
            MyAccount myAccount = new MyAccount();
            myAccount.saveAccountDetails();

            Market market = new Market();
            market.saveMarketDetails();


            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            primaryStage.setTitle("Football League Manager");
            primaryStage.setScene(new Scene(root, 1200, 750));
            primaryStage.show();

            lblError.setText("");

            lblSuccess.setText("Setting up your new account...");

            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
        }
    }
}
