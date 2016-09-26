package FLMfiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Justin on 2016/09/25.
 */
public class AccountTest {

    public static MyAccount account;

    public AccountTest() {
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
    }
}
