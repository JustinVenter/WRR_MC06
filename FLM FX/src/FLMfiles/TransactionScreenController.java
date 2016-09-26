package FLMfiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Michael on 14/09/2016.
 */


public class TransactionScreenController implements Initializable {

    public TableView tblTransactions;
    public TableColumn transactionWeek;
    public TableColumn transactionType;
    public TableColumn transactionAmount;
    public TableColumn transactionDescription;

    private MyAccount account;

    // ArrayList of transactions
    private ArrayList<Transaction> transactions2;

    //Arraylist wrapped in observable list
    private ObservableList<Transaction>observableTransactions2;

    public TransactionScreenController(){
        account = AccountTest.account;
        transactions2 = account.GetTransactions2();
        observableTransactions2 =  FXCollections.observableArrayList(transactions2);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableSetup();
        tblTransactions.setItems(observableTransactions2);


    }
    private void tableSetup(){

        transactionWeek.setCellValueFactory(new PropertyValueFactory<>("Week"));
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        transactionType.setCellValueFactory(new PropertyValueFactory<>("Income"));
        transactionDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

}
