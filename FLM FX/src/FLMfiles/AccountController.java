package FLMfiles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Michael on 14/09/2016.
 */


public class AccountController implements Initializable {

    public Label CurBankBalance;
    public Label CurWeek;
    public Label Salaries;
    public Label Debt;

    public TableView tblTransactions;
    public TableColumn transactionWeek;
    public TableColumn transactionType;
    public TableColumn transactionAmount;
    public TableColumn transactionDescription;

    Database db = new Database();

    public Button ViewTransactions;



    // ArrayList of transactions
    private ArrayList<Transaction> transactions1;
    //Arraylist wrapped in observable list
    private ObservableList<Transaction> observableTransactions1;

    public AccountController(){


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AccountTest AT = new AccountTest();
        MyAccount account = null;
        try {
            account = new MyAccount(db.loadMyTeam());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        transactions1 = account.GetTransactions1();

        observableTransactions1 =  FXCollections.observableArrayList(transactions1);

        CurBankBalance.setText("$" +String.valueOf(account.GetBankBalance()));

        CurWeek.setText(String.valueOf(League.Week));
        try {
            Salaries.setText("$" +String.valueOf(account.GetWeeklyExpenditure()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Debt.setText("$" +String.valueOf(account.CalculateDebt()));

        tableSetup();
        tblTransactions.setItems(observableTransactions1);


    }
    private void tableSetup(){

        transactionWeek.setCellValueFactory(new PropertyValueFactory<>("Week"));
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        transactionType.setCellValueFactory(new PropertyValueFactory<>("Income"));
        transactionDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    }
    public void onTransactionsClick(Event event) throws InterruptedException {
        OpenSimulationWindow();
    }

    public void OpenSimulationWindow()
    {
        Parent root = null;
        Stage secondaryStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("TransactionsScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Football League Manager");
        secondaryStage.setScene(new Scene(root, 750, 600));
        secondaryStage.show();
    }
}
