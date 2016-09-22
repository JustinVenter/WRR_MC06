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

    public void onMarketClick(ActionEvent actionEvent) throws IOException {
        mainAnchor = (AnchorPane) ((Node)actionEvent.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("MarketScreen.fxml")));
    }
}
