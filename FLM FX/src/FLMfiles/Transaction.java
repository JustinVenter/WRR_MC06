package FLMfiles;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Created by Justin on 2016/09/10.
 */
public class Transaction implements Serializable {


    private String Descrip;
    private double amnt;
    private int wk;
    private String inc;

    public Transaction(double a, String D, boolean I, int W){
        Descrip = D;
        amnt = a;
        wk = W;

        if(I == true)
            inc = "Income";
        else
            inc = "Expense";
    }


    public String getDescrip() {
        return Descrip;
    }

    public double getAmnt() {
        return amnt;
    }

    public int getWk() {
        return wk;
    }

    public String toString()
    {
        return Descrip + ", " + amnt + "," + wk;
    }

    public boolean getInc() {
        if(inc.equals("Income"))
            return true;
        else
            return false;
    }

    transient StringProperty description = new SimpleStringProperty();
    transient DoubleProperty Amount = new SimpleDoubleProperty();
    transient IntegerProperty Week = new SimpleIntegerProperty();
    transient StringProperty Income = new SimpleStringProperty(); //true if the transaction is income


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
