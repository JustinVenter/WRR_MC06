package FLMfiles;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.security.auth.RefreshFailedException;
import javax.security.auth.Refreshable;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    //<editor-fold desc="Editor tab">
    public AnchorPane manageAnchor;
    public AnchorPane PlayAnchor;
    public AnchorPane AccountAnchor;
    public AnchorPane NewsFeedAnchor;
    AccountController AC = new AccountController();

    public Label lblWeek;
    public Label lblManager;
    public Pane curPane1;
    public Tab AccTab;
    public Tab NewsTab;
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
            NewsFeedAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("NewsFeed.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void onAccClicked(Event event) throws IOException {
        AccountAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("AccountScreen.fxml")));
    }

    public void onNewsClicked(Event event) throws IOException {
        NewsFeedAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("NewsFeed.fxml")));
    }

    public void onManageClicked(Event event) {
        try {
            manageAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManageScreen.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






   /* public void onMarketClick(ActionEvent actionEvent) throws IOException {
        mainAnchor = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("MarketScreen.fxml")));
    }*/


    //</editor-fold>



}