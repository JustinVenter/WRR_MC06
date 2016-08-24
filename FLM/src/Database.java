import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.sound.sampled.Line;
import javax.xml.crypto.Data;

/**
 * Created by Michael on 09/08/2016.
 */


public class Database {

    // fields needed to access database
    // actual connection to db
    static private Connection con = null;
    // object used to issue SQL commands
    static private Statement stmt = null;

    public Database(){
        connectToDB();
        //updatePlayerAvg();

    }

    //Methods
    public void connectToDB() {
        System.out.println("Establishing connection to database...");

        System.out.println("   Loading JDBC driver for MS SQL Server database...");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            System.out.printf("   Unable to load JDBC driver... '%s'\n", e.getMessage());
            return;
        }
        try {
            // another way of creating a connection to a database
            System.out.println("   Locate database to open (using server data source)...");

            // specify details of connection to be made
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser("MC06User");
            ds.setPassword("sOSnCmUp");
            ds.setServerName("openbox.nmmu.ac.za\\wrr");
            ds.setInstanceName("WRR");
            ds.setDatabaseName("MC06");

            // create the connection to the DB
            con = ds.getConnection();

            // create statement object for manipulating DB
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            System.out.println("connection successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList loadTeam()
    {

        System.out.println("Using database...");

        ArrayList<Player> Lineup = new ArrayList<>();
        try {
            // perform query on database and retrieve results
            String sql = "SELECT * FROM Player WHERE TeamID = 1";
                System.out.println("  Loading starting lineup = " + sql);
            ResultSet result = stmt.executeQuery(sql);

            System.out.println();
            System.out.println("   Displaying Query Result");
            System.out.println("----------------------------------------------------------------------");

            // while there are tuples in the result set, display them
            while (result.next() ) {
                // get values from current tuple
                int PlayerID = result.getInt("PlayerID");
                int TeamID = result.getInt("TeamID");
                String surname = result.getString("PSurname");
                String name = result.getString("PName");
                boolean StartLineUp = result.getBoolean("StartLineUp");
                double PAvgRating = result.getDouble("PAvgRating");
                int PAttRating = result.getInt("PAttackRating");
                int PDefRating = result.getInt("PDefenceRating");;
                String PPos = result.getString("PPos");
                String PSkill = result.getString("PSkill");;  //actual name of the skill [Normal(default)/Striker(attack)/Playmaker(midfield)/PowerDefender(defense)]
                int PFatigue = result.getInt("PFatigue"); //his current fatigue level, with 100 being max and, 0 meaning injury.
                double PSalary = result.getDouble("PSalary");
                double PValue = result.getDouble("PValue"); //the player's buy and sell value
                int PContract = result.getInt("PContract"); //time remaining in weeks
                boolean PInjury = result.getBoolean("PInjury");
                int PFatigueLvl = result.getInt("PFatigueLevel"); //1 being wost and 4 being max*/

                Player newOne = new Player(PlayerID, TeamID, StartLineUp, name, surname, PAvgRating, PAttRating, PDefRating, PPos, PSkill, PFatigue, PSalary, PValue, PContract, PInjury, PFatigueLvl);
                Lineup.add(newOne);
            }
            System.out.println("Load successful");
            for (int i = 0; i < Lineup.size(); i++)
            {
                System.out.println(Lineup.get(i).toString());
            }


            return Lineup;
        } catch (Exception e) {
            System.out.println("   Was not able to query database...");
        }
        return null;
    }

    public void disconnectDB() {
        System.out.println("Disconnecting from database...");

        try {
            //Important to close connection (same as with files)
            con.close();
            System.out.println("Connection ended");
        } catch (Exception ex) {
            System.out.println("   Unable to disconnect from database");
        }
    }

    //add team, remove team

    //update player team

    //update contract

    //update team ratings

    //update rankings


    //getters and setters

    //views (such as display)
}
