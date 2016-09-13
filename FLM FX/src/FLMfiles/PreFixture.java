package FLMfiles;

import java.io.Serializable;

/**
 * Created by Michael on 10/09/2016.
 */
public class PreFixture implements Serializable {

    private MyTeam homeTeam;
    private MyTeam awayTeam;

    public PreFixture(MyTeam homeTeam, MyTeam awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public MyTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(MyTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public MyTeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(MyTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public String toString() {
        return "homeTeam=" + homeTeam + " V"+ '\'' +
                ", awayTeam=" + awayTeam +
                '}';
    }
}
