package FLMfiles;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Created by Michael on 09/08/2016.
 */
public class League implements Serializable {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        MyTeamPreFixture();
        SaveMyPreFixtures();
        LoadMyPreFixtures();
    }

    static Stack<PreFixture> fixtures = new Stack<>(); // All the AI games.
    static Stack<PreFixture> MyFixtures = new Stack<>();// add another stack called myFixtures -- All the games for the human users
    static int Week = 6;


    public void loadGames() throws IOException, ClassNotFoundException {
        //dummy details for demo
        Database database = new Database();
        // database.UpdatePAverage();

        PlayController playController = new PlayController();

        database.connectToDB();

        MyTeam myteam = database.loadMyTeam();

        myteam.CalculateAvgDefence();
        myteam.CalculateAvgAttack();
        myteam.CalculateAvgRating();

        MyTeam Dummy = new MyTeam(99, "Dummy", 60, 70,50, "DumCity", 5, 0, 0);
        Player[] botTeam = Dummy.generateTeamPlayers();


        Dummy.setStartingLineUp(botTeam);
        PreFixture Game1 = new PreFixture(myteam, Dummy);
        fixtures.push(Game1);
        database.disconnectDB();
        Week = 1;
    }
    public int GetWeek(){
        return Week;
    }
    static void AddWeek(){
        Week++;
    }

    /**
     * This method creates the pre-fixtures for the users team, i.e. MyTeam
     * It loads all the teams into an arraylist of teams called AllTeams.
     * Then using a for loop for safety, we remove MyTeam.
     * Using a while loop to ensure that a seasons of 52 weeks is used we loop through the arraylist and
     * add AI or BOT teams to our pre-fixture and subtract from weeks.
     */
    public static void MyTeamPreFixture() {

        Database db = new Database();
        db.connectToDB();

        // load all the teams
        ArrayList<MyTeam> AllTeams = db.loadTeams();
        MyTeam myTeam = null;
        try {
            myTeam = db.loadMyTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // take out 'MyTeam' which is team ID =1, position 0
        // AllTeams.remove(0);
        for (int i = 0; i < AllTeams.size(); i++) {

            if (AllTeams.get(i).getTeamID() == myTeam.getTeamID()) {

                AllTeams.remove(i);
            }
            // This is only to see if MyTeam is removed.
            System.out.println(AllTeams.get(i).toString());
        }
        int weeksCounter = 52;
        // Using a while loop to ensure an entire season of 52 weeks is used
        while (weeksCounter > 0) {
            //go through arraylist of teams and pair them with your team i.e MyTeam.

            //Collections.shuffle(AllTeams);
            for (int i = 0; i < AllTeams.size(); i++) {
                //One game is played each week, so after a pre-fixture is added a week gets subtracted.

                PreFixture first = new PreFixture(myTeam, AllTeams.get(i));
                weeksCounter--;
                MyFixtures.push(first);
                if (weeksCounter == 0)
                    break;
                if (i <= 19) {
                    PreFixture second = new PreFixture(AllTeams.get(i + 1), myTeam);
                    weeksCounter--;
                    MyFixtures.push(second);
                }
            }
            Collections.shuffle(AllTeams);
        }

        db.disconnectDB();
    }

    /**
     * This method creates the pre-fixtures for all the bots
     * It loads all the teams into an arraylist of teams called AllTeams.
     * Then using a for loop for safety, we remove MyTeam.
     * Using a while loop to ensure that a seasons of 52 weeks is used we loop through the arraylist and
     * add AI or BOT teams to our pre-fixture and subtract from weeks. i.e YourTeam Vs Liverpool etc
     */
    public static void BotTeamPreFixture() {

        Database db = new Database();
        db.connectToDB();

        // load all the teams
        ArrayList<MyTeam> AllTeams = db.loadTeams(); // for this to work, I had to change the loadTeams method in the database class to type MyTeam
        MyTeam myTeam = null;
        try {
            myTeam = db.loadMyTeam();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // take out 'MyTeam' which is team ID =1, position 0
        // AllTeams.remove(0);
        for (int i = 0; i < AllTeams.size(); i++) {

            if (AllTeams.get(i).getTeamID() == myTeam.getTeamID()) {

                AllTeams.remove(i);
            }
            // This is only to see if MyTeam is removed.
            System.out.println(AllTeams.get(i).toString());
        }
        int weeksCounter = 52;
        while (weeksCounter > 0) {
            //go through arraylist of teams and pair them with your team i.e MyTeam.

            Collections.shuffle(AllTeams);
            for (int i = 0; i < AllTeams.size(); i++) {

                //This if statement is to ensure that we do not get an index out of bounds exception and that
                // no more than 52 weeks of pre-fixtures are done.
                if ((i == 20) || (weeksCounter == 0))
                    break;
                PreFixture first = new PreFixture(AllTeams.get(i), AllTeams.get(i + 1));
                weeksCounter--;
                fixtures.push(first);

            }
            Collections.shuffle(AllTeams);
        }
        // Just to see if the correct amount was added. Should be 52 games
        System.out.println("Amount of fixtures is equal to " + fixtures.size());
        db.disconnectDB();
    }

    /**
     * Saves the MyFixtures stack as an object file.
     *
     * @throws IOException
     */
    public static void SaveMyPreFixtures() throws IOException {

        //For testing purposes
        System.out.println("MyFixtures stack currently has " + MyFixtures.size() + " objects before being saved !!!!!!!!!!!!");
        // save the file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("MyFixtures.obj"));
        out.writeObject(MyFixtures);
        System.out.println("Save successful !!!!!!!!!!!");


    }

    /**
     * Loads the MyFixtures object back into a stack.
     * Two empty constructers were made in class Team and MyTeam for this to work.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void LoadMyPreFixtures() throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("MyFixtures.obj"));
        MyFixtures = (Stack<PreFixture>) in.readObject();
        // display to see if save and load worked.
        System.out.println("Load successful !!!!!!!!!!!");
        System.out.println("After MyFixtures was loaded it has " + MyFixtures.size() + " objects");
    }

    public static void SaveBotPreFixtures() throws IOException {
        //For testing purposes
        System.out.println("BotFixtures stack currently has " + fixtures.size() + " objects before being saved !!!!!!!!!!!!");
        // save the file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("fixtures.obj"));
        out.writeObject(fixtures);
        System.out.println("Save successful !!!!!!!!!");

    }

    public static void LoadBotPreFixtures() throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("fixtures.obj"));
        fixtures = (Stack<PreFixture>) in.readObject();
        // display size to see if save and load worked.
        System.out.println("Load successful !!");
        System.out.println("After fixtures was loaded it has " + fixtures.size() + " objects");
    }


}


//Methods

//getters and setters

//views (such as display)
