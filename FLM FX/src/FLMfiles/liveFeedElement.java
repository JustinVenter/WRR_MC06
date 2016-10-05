package FLMfiles;

/**
 * Created by Michael on 05/10/2016.
 */
public class liveFeedElement {
    int Time;
    String value;

    public liveFeedElement(int time, String value) {
        Time = time;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + Time +":00)" + value;
    }
}
