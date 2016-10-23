package FLMfiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Michael on 22/09/2016.
 */
public class ManageController {
    AnchorPane mainAnchor;
    AnchorPane mainAnchorCalendar;
    AnchorPane mainAnchorResults;

    public void onMarketClick(ActionEvent actionEvent) throws IOException {
        mainAnchor = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("MarketScreen.fxml")));
    }
    public void onCalendarClicked(ActionEvent actionEvent) throws IOException {

        mainAnchorCalendar = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchorCalendar.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("CalendarScreen.fxml")));
    }

    public void onResultsClicked(ActionEvent actionEvent) throws IOException {

        mainAnchorResults = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchorResults.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ResultScreen.fxml")));
    }
    public void onStaffClicked(ActionEvent actionEvent) throws IOException {

        mainAnchorResults = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchorResults.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("UpgradeStaffScreen.fxml")));
    }
    public void onSquadClicked(ActionEvent actionEvent) throws IOException {

        mainAnchorResults = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchorResults.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("SquadScreen.fxml")));
    }
    public void onLeagueClicked(ActionEvent actionEvent) throws IOException {
        mainAnchorResults = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchorResults.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("LeagueTable.fxml")));
    }

}
