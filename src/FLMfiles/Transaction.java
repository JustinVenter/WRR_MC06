package FLMfiles;

import javafx.beans.property.*;

/**
 * Created by Justin on 2016/09/10.
 */
public class Transaction {


    StringProperty description = new SimpleStringProperty();
    DoubleProperty Amount = new SimpleDoubleProperty();
    IntegerProperty Week = new SimpleIntegerProperty();
    StringProperty Income = new SimpleStringProperty(); //true if the transaction is income


    public Transaction(String d, double a, boolean I, int W){
        description.set(d);
        Amount.set(a);
        Week.set(W);

        if(I == true)
            Income.set("Income");
        else
            Income.set("Expense");
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public double getAmount() {
        return Amount.get();
    }

    public DoubleProperty amountProperty() {
        return Amount;
    }

    public int getWeek() {
        return Week.get();
    }

    public IntegerProperty weekProperty() {
        return Week;
    }

    public String getIncome() {
        return Income.get();
    }

    public StringProperty incomeProperty() {
        return Income;
    }


}
