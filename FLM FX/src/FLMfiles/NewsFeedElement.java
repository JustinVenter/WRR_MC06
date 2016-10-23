package FLMfiles;

import javafx.beans.property.*;

import java.io.Serializable;

/**
 * Created by Justin on 2016/10/17.
 */
public class NewsFeedElement implements Serializable {

    private int wk;
    private String Activity;
    private boolean MatchResult;

    public NewsFeedElement(int W, String Act, boolean Match){
        wk = W;
        Activity = Act;
        MatchResult = Match;

    }


    public String getActivity() {
        return Activity;
    }

    public int getWk() {
        return wk;
    }

    public boolean getResult(){
        return MatchResult;
    }


    transient IntegerProperty Wk = new SimpleIntegerProperty();
    transient StringProperty Act = new SimpleStringProperty();
    transient BooleanProperty Match = new SimpleBooleanProperty();


    public NewsFeedElement(String A, boolean M, int W){
        Act.set(A);
        Match.set(M);
        Wk.set(W);
    }

    public int getWeek() {
        return Wk.get();
    }

    public IntegerProperty weekProperty() {
        return Wk;
    }

    public String getAct() {
        return Act.get();
    }

    public StringProperty actProperty() {
        return Act;
    }

    public boolean isMatch() {
        return Match.get();
    }

    public BooleanProperty matchProperty() {
        return Match;
    }




}
