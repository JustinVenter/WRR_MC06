package FLMfiles;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Created by Michael on 09/08/2016.
 */
public class League implements Serializable {

    static Stack<PreFixture> fixtures = new Stack<>(); // All the AI games.
    static Stack<PostFixture> postfixtures = new Stack<>(); // All the AI games.
    static Stack<PreFixture> MyFixtures = new Stack<>();// add another stack called myFixtures -- All the games for the human users
    // Stacks which contain all the Post fixtures.
    static Stack<PostFixture> MyPostFixtures= new Stack();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

         if(!FixturesExists()){
         MyTeamPreFixture();
         SaveMyPreFixtures();
         SaveMyPostFixtures();
         }


        /**
         *  Database db = new Database();
         db.connectToDB();
         db.ClearTeamWinsAndLosses();
          */


    }

    public static boolean FixturesExists(){

        File file= new File("MyFixtures.obj");
        File file2= new File("fixtures.obj");
        if((file.exists()) && (file2.exists()))
            return  true;
        return false;
    }

    public PreFixture loadCurGame() throws IOException, ClassNotFoundException {
        Database database = new Database();
        // database.UpdatePAverage();

        database.connectToDB();
        LoadMyPreFixtures();

        MyTeam myteam = database.loadMyTeam();

        myteam.CalculateAvgDefence();
        myteam.CalculateAvgAttack();
        myteam.CalculateAvgRating();

        //load dummy team from textfile

        PreFixture pf= MyFixtures.peek();

        MyTeam Dummy = pf.getAwayTeam();
        Player[] botTeam = pf.getAwayTeam().generateTeamPlayers();


        Dummy.setStartingLineUp(botTeam);
        PreFixture Game1 = new PreFixture(myteam, Dummy);
        database.disconnectDB();

        return Game1;
    }



    public PreFixture loadNextGame() throws IOException, ClassNotFoundException {


        //dummy details for demo
        Database database = new Database();
        // database.UpdatePAverage();

        PlayController playController = new PlayController();

        database.connectToDB();
        LoadMyPreFixtures();

        MyTeam myteam = database.loadMyTeam();

        myteam.CalculateAvgDefence();
        myteam.CalculateAvgAttack();
        myteam.CalculateAvgRating();

        //load dummy team from textfile

        PreFixture pf= MyFixtures.pop();

        SaveMyPreFixtures();
        MyTeam Dummy = pf.getAwayTeam();
        Player[] botTeam = pf.getAwayTeam().generateTeamPlayers();


        Dummy.setStartingLineUp(botTeam);
        PreFixture Game1 = new PreFixture(myteam, Dummy);
        database.disconnectDB();

        return Game1;
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
                    PreFixture second = new PreFixture(myTeam, AllTeams.get(i + 1));
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
    public static Stack<PreFixture> LoadMyPreFixtures() throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("MyFixtures.obj"));
        MyFixtures = (Stack<PreFixture>) in.readObject();
        // display to see if save and load worked.
        System.out.println("Load successful !!!!!!!!!!!");
        System.out.println("After MyFixtures was loaded it has " + MyFixtures.size() + " objects");
        return MyFixtures;
    }

    public static void SaveBotPreFixtures() throws IOException {
        //For testing purposes
        System.out.println("BotFixtures stack currently has " + fixtures.size() + " objects before being saved !!!!!!!!!!!!");
        // save the file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("fixtures.obj"));
        out.writeObject(fixtures);
        System.out.println("Save successful !!!!!!!!!");

    }


    public static void SaveBotPostFixtures() throws IOException {
        //For testing purposes
        // save the file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("postfixtures.obj"));
        out.writeObject(postfixtures);
        System.out.println("Save successful !!!!!!!!!");

    }

    public static void LoadBotPreFixtures() throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("fixtures.obj"));
        fixtures = (Stack<PreFixture>) in.readObject();
        // display size to see if save and load worked.
        System.out.println("Load successful !!");
        System.out.println("After fixtures was loaded it has " + fixtures.size() + " objects");
    }

    /**
     * This method takes as input one post fixture and adds it to the stack of post-fixtures which can be viewed in Results.
     * @param newOne - Postfixture.
     */
    public void addMyPostFixture(PostFixture newOne){

        try {
            MyPostFixtures=LoadMyPostFixtures();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MyPostFixtures.add(newOne);

        try {
            SaveMyPostFixtures();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SaveMyPostFixtures() throws IOException {

        //For testing purposes
        System.out.println("MyFixtures stack currently has " + MyPostFixtures.size() + " objects before being saved !!!!!!!!!!!!");
        // save the file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("MyPostFixtures.obj"));
        out.writeObject(MyPostFixtures);
        System.out.println("Save successful !!!!!!!!!!!");

    }
    public static Stack<PostFixture> LoadMyPostFixtures() throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("MyPostFixtures.obj"));
        MyPostFixtures = (Stack<PostFixture>) in.readObject();
        // display to see if save and load worked.
        System.out.println("Load successful !!!!!!!!!!!");
        System.out.println("After MyPostFixtures was loaded it has " + MyPostFixtures.size() + " objects");
        return MyPostFixtures;

    }

    public static ArrayList<MyTeam> insertionSort() {

        Database db = new Database();
        db.connectToDB();

        // load all the teams
        ArrayList<MyTeam> AllTeams = db.loadTeams();
        ArrayList<MyTeam> temp = AllTeams;
        int i, j;


        for (i = 1; i < temp.size(); i++) {
            MyTeam key = new MyTeam();
            key.TWins = temp.get(i).getTWins();
            key.TeamID = temp.get(i).getTeamID();
            key.TName = temp.get(i).getTName();
            key.TRating = temp.get(i).getTRating();
            key.TLosses = temp.get(i).getTLosses();
            j = i;
            while ((j > 0) && (temp.get(j - 1).getTWins() < key.TWins)) {
                temp.set(j, temp.get(j - 1));
                j--;
            }
            temp.set(j, key);
        }

        return temp;
    }



}


//Methods

//getters and setters

//views (such as display)
