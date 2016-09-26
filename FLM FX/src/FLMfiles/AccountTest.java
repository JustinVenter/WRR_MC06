package FLMfiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Justin on 2016/09/25.
 */
public class AccountTest extends Application {

    public static MyAccount account;

    public static void main(String[] args) {
        MyTeam T = new MyTeam();
        account = new MyAccount(T);
        //AccountController Controller = new AccountController(Account);
        Transaction T1 = new Transaction("Paid", 100000, false, 6);
        Transaction T2 = new Transaction("Paid", 50000, false, 5);
        Transaction T3 = new Transaction("Received", 20000, true, 4);
        Transaction T4 = new Transaction("Received", 20000, true, 3);
        Transaction T5 = new Transaction("Received", 20000, true, 2);
        Transaction T6 = new Transaction("Received", 20000, true, 1);
        Transaction T7 = new Transaction("Received", 20000, true, 6);
        Transaction T8 = new Transaction("Received", 20000, true, 5);

        account.UpdateBank(T1);
        account.UpdateBank(T2);
        account.UpdateBank(T3);
        account.UpdateBank(T4);
        account.UpdateBank(T5);
        account.UpdateBank(T6);
        account.UpdateBank(T7);
        account.UpdateBank(T8);



        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AccountScreen.fxml"));
        primaryStage.setTitle("Football League Manager");
        primaryStage.setScene(new Scene(root, 1200, 750));
        primaryStage.show();
    }
}
