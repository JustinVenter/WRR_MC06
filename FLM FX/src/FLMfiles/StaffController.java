package FLMfiles;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by rjoos on 10/6/2016.
 */
public class StaffController implements Initializable {

    public Label lblCurLvl;
    public Label lblNextLvl;
    public Label lblNextCost;

    User user= new User();
    MyTeam myTeam = new MyTeam();

    private void SetupLabels(){

        try {
            user= user.readUser();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(user.getStaffLevel()==10){
            lblNextCost.setText("Highest Level Reached");
            lblNextLvl.setText("");
            lblCurLvl.setText(String.valueOf(user.getStaffLevel()));
        }else{
                 lblCurLvl.setText(String.valueOf(user.getStaffLevel()));
                 lblNextLvl.setText(String.valueOf(user.getStaffLevel()+1));
                 lblNextCost.setText("$ ".concat(String.valueOf(myTeam.StaffCost[user.getStaffLevel()+1])));
        }
    }


    public void onUpgradeClicked(Event event) {

        // Must still subtract the cost of the upgrade i.e myTeam.StaffCost from the bank balance.
        MyAccount myAccount = new MyAccount();

        try {
            myAccount = myAccount.readAccount();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if(myTeam.UpgradeStaff(user.getStaffLevel(),myAccount.GetBankBalance())==true)
        {
            user.IncreaseStaffLevel();
            System.out.println("Level upgraded successfully !!");
        }else {
            System.out.println("False was returned");
        }


        try {
            user.saveUserDetails();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SetupLabels();


    }

    public void onBackClicked(Event event) throws IOException {

        AnchorPane mainAnchor = (AnchorPane) ((Node)event.getSource()).getParent().getParent();
        mainAnchor.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("ManageScreen.fxml")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SetupLabels();

    }
}
