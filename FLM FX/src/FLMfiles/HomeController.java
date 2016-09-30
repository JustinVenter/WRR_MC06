package FLMfiles;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    //<editor-fold desc="Editor tab">
    public AnchorPane manageAnchor;
    public AnchorPane PlayAnchor;
    public AnchorPane AccountAnchor;

    public Label lblWeek;
    public Label lblManager;

    public Pane curPane1;
    public HomeController()
    {}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //set up Username/week etc.
            User user = new User();
            user = user.readUser();

            lblManager.setText("Mr. " + user.getUserName());
            lblWeek.setText(String.valueOf(user.Week));

            PlayAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("PrePlay.fxml")));
            manageAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManageScreen.fxml")));
            AccountAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("AccountScreen.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


   /* public void onMarketClick(ActionEvent actionEvent) throws IOException {
        mainAnchor = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("MarketScreen.fxml")));
    }*/


    //</editor-fold>



}