package FLMfiles;

import java.util.Stack;

/**
 * Created by Michael on 09/08/2016.
 */
public class League {
    Stack<PreFixture> myfixtures = new Stack<>();
    Stack<PreFixture> fixtures = new Stack<>();

    public void loadGames()
    {
        //dummy details for demo
        Database database = new Database();
            // database.UpdatePAverage();

        PlayController playController = new PlayController();

            database.connectToDB();

            MyTeam myteam = database.loadMyTeam();

            myteam.CalculateAvgDefence();
            myteam.CalculateAvgAttack();
            myteam.CalculateAvgRating();

            MyTeam Dummy = new MyTeam(99, "Dummy", 55, 60, 50, "DumCity", 5, 0, 0);
            Player[] botTeam = Dummy.generateTeamPlayers();



            Dummy.setStartingLineUp(botTeam);
            PreFixture Game1 = new PreFixture(myteam, Dummy);
            fixtures.add(Game1);
            database.disconnectDB();
        }
    }

    //Methods

    //getters and setters

    //views (such as display)
