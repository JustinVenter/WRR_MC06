package FLMfiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //
        User user = new User();
        if(user.DoesUserAccountExist()==true)
        {
            //load existing User
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            primaryStage.setTitle("Football League Manager");
            primaryStage.setScene(new Scene(root, 1200, 750));
            primaryStage.show();
        }
        else
        {
            Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
            primaryStage.setTitle("Football League Manager");
            primaryStage.setScene(new Scene(root, 448, 483));
            primaryStage.show();
            //user.saveUserDetails();
        }
            //user.saveUserDetails();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
