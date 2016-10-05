package FLMfiles;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Created by Justin on 2016/10/05.
 */
public class AccountProperties {

    private DoubleProperty Bank = new SimpleDoubleProperty();

    public AccountProperties(MyAccount A){
        Bank.setValue(A.GetBankBalance());
    }


    public double getBank() {
        return Bank.get();
    }

    public DoubleProperty bankProperty() {
        return Bank;
    }
}
