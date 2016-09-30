package FLMfiles;

import java.io.IOException;

/**
 * Created by Michael on 25/08/2016.
 */
public class MainConsole {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Database database = new Database();

        Calculate calculate = new Calculate();
        HomeController homeController = new HomeController();
        PrePlayController prePlayController = new PrePlayController();
        PlayController playController = new PlayController();

        database.connectToDB();

        MyTeam myteam = null;
        try {
            myteam = database.loadMyTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        myteam.CalculateAvgDefence();
        myteam.CalculateAvgAttack();
        myteam.CalculateAvgRating();

        int homewin = 0;
        int awaywin = 0;
        int draw = 0;

        MyTeam Dummy = new MyTeam(99, "Dummy", 55, 55, 55, "DumCity", 5, 0, 0);
        Player[] botTeam = Dummy.generateTeamPlayers();



        Dummy.setStartingLineUp(botTeam);
        System.out.println("Def: " + String.valueOf(myteam.getTDefRating()));
        System.out.println("Att: " + String.valueOf(myteam.getTAttRating()));
        System.out.println("Ovr: " + String.valueOf(myteam.getTRating()));

        PreFixture preFixture = prePlayController.getLeagueGame(prePlayController.league);

        for(int i = 0; i < 300; i++) {
            PostFixture result = playController.LMAlogrithm(preFixture);
            PostFixture result2 = playController.LMAlogrithm(preFixture);
            System.out.println(result.getResult() + " : " + result2.getResult());
            if(Integer.parseInt(result.getResult()) > Integer.parseInt(result2.getResult()))
            {
                homewin = homewin + 1;
            }
            else
            if(Integer.parseInt(result.getResult()) < Integer.parseInt(result2.getResult()))
            {
                awaywin= awaywin + 1;
            }
            else
                draw = draw + 1;
        }
        Dummy.CalculateAvgAttack();
        Dummy.CalculateAvgDefence();
        Dummy.CalculateAvgRating();
        System.out.println("Dummy rating = " + Dummy.getTRating());
        System.out.println("Dummy att rating = " + Dummy.getTAttRating());
        System.out.println("Dummy def rating = " + Dummy.getTDefRating());

        System.out.println("Home rating = " + myteam.getTRating());
        System.out.println("Home att rating = " + myteam.getTAttRating());
        System.out.println("Home def rating = " + myteam.getTDefRating());

        System.out.println("Win: " + homewin);
        System.out.println("Lose: " + awaywin);
        System.out.println("Draw: " + draw);
        database.disconnectDB();
    }
}
