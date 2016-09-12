package FLMfiles;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by Michael on 12/09/2016.
 */
public class LineupController {
    public Button btnCancel;


    public void onCancelClick(Event event) throws IOException {
        AnchorPane curPane = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        curPane.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("PrePlay.fxml")));
    }
}
