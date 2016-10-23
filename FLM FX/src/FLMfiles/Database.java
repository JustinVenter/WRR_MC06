package FLMfiles;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 09/08/2016.
 */


public class Database implements Serializable{
    User user = new User();

    Calculate calculate = new Calculate();
    // fields needed to access database
    // actual connection to db
    static private Connection con = null;
    // object used to issue SQL commands
    static private Statement stmt = null;

    ArrayList<Player> MyStartingLineUp = new ArrayList<>();

    public Database(){
        //connectToDB();
        //UpdatePAverage();
        //updatePlayerAvg();
    }

    //Methods
    public void connectToDB() {
        System.out.println("Establishing connection to database...");

        System.out.println("   Loading JDBC driver for MS SQL Server database...");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Class.forName(\"com.microsoft.sqlserver.jdbc.SQLServerDriver\")");
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
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Close(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    Load your team's squad
    */
    public MyTeam loadMyTeam() throws IOException, ClassNotFoundException {
        this.connectToDB();
        System.out.println("Using database...");
        MyTeam myTeam = null;
        //user = user.readUser();

        try {
            String sql = "SELECT * FROM Team WHERE TeamID = 1";
            ResultSet result = stmt.executeQuery(sql);
            if (result.next() ) {
                // perform query on database and retrieve results

                String TeamName = result.getString("TName");
                int TeamID = result.getInt("TeamID");
                String TCity = result.getString("TCity");
                double TRating = result.getDouble("TRating");
                int TAttRating = result.getInt("TAttRating");
                int TDefRating = result.getInt("TDefRating");

                myTeam = new MyTeam(TeamID, TeamName, TRating, TAttRating, TDefRating, TCity, 0, 0, 0);

                myTeam.loadSquad();

                myTeam.loadSquad();
                //Delete when squads are working!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                Player[] strt = new Player[11];

                for(Player p : myTeam.getMySquad())
                    if(p.StartLineUp == true)
                        MyStartingLineUp.add(p);

                strt = loadStartingLineUp(MyStartingLineUp);


                myTeam.setStartingLineUp(strt);
                myTeam.CalculateAvgDefence();
                myTeam.CalculateAvgAttack();
                myTeam.CalculateAvgRating();

                //update avg stuff
                try {
                    // perform query on database and retrieve results
                    String sql2 = "UPDATE Team SET TAttRating = " + myTeam.getTAttRating() + ", TDefRating = " + myTeam.getTDefRating() + " WHERE TeamID = 1";
                    stmt.execute(sql2);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

         disconnectDB();
        return myTeam;
    }

    public Player[] loadStartingLineUp(ArrayList<Player> myStartingLineUp) {
        Player[] startlineup = new Player[11];

        for(int i = 0; i < myStartingLineUp.size(); i++)
        {
            Player cur = myStartingLineUp.get(i);

            switch(cur.getPPos())
            {
                case "LF": startlineup[0] = cur;
                    break;
                case "CF": startlineup[1] = cur;
                    break;
                case "RF": startlineup[2] = cur;
                    break;

                case "LM": startlineup[3] = cur;
                    break;
                case "CM": startlineup[4] = cur;
                    break;
                case "RM": startlineup[5] = cur;
                    break;

                case "LB": startlineup[6] = cur;
                    break;
                case "LMB": startlineup[7] = cur;
                    break;
                case "RMB": startlineup[8] = cur;
                    break;
                case "RB": startlineup[9] = cur;
                    break;

                case "GK": startlineup[10] = cur;
                    break;

            }
        }
        return startlineup;
    }

    /**
     Load your team's squad
     */
    public ArrayList loadMyTeamSquad()
    {
        ArrayList<Player> AllPlayers = LoadAllPlayers();
        ArrayList<Player> MyTeam = new ArrayList<>();

        for(int i = 0; i < AllPlayers.size(); i++)
        {
            if(AllPlayers.get(i).getTeamID()==1)
            {
                MyTeam.add(AllPlayers.get(i));
            }
        }
        return MyTeam;
    }

    public ArrayList<Player> LoadAllPlayers()
    {

        System.out.println("Using database...");

        ArrayList<Player> AllPlayers = new ArrayList<>();
        try {
            // perform query on database and retrieve results
            String sql = "SELECT * FROM Player";
            System.out.println("  Loading all players = " + sql);
            ResultSet result = stmt.executeQuery(sql);

            System.out.println();
            System.out.println("   LoadAllPlayers (Comment)");
            System.out.println("----------------------------------------------------------------------");

            Player newOne = null;

            // while there are tuples in the result set, display them
            while (result.next() ) {
                System.out.println("   LoadAllPlayers (Comment 2)");
                // get values from current tuple
                int PlayerID = result.getInt("PlayerID");
                int TeamID = result.getInt("TeamID");
                String surname = result.getString("PSurname");
                String name = result.getString("PName");
                int Age = result.getInt("PAge");
                double PerformanceBonus = result.getDouble("PperformanceBonus");
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
                int PInjuryPenalty = result.getInt("PInjuryPenalty");

                boolean PonSale = result.getBoolean("PonSale");

                System.out.println("   LoadAllPlayers (Comment 3)");

                newOne = new Player(PlayerID, TeamID, StartLineUp, name, surname,Age, PAvgRating, PAttRating, PDefRating, PPos, PSkill, PFatigue, PSalary, PValue, PContract, PInjury, PFatigueLvl, PerformanceBonus, PInjuryPenalty, PonSale);

                /*double AvgRating = calculate.CalcPlayerAvg(newOne);
                newOne.setPAvgRating(AvgRating);
                */

                AllPlayers.add(newOne);

            }


            //loadStartingLineUp(MyStartingLineUp);
            System.out.println("Load successful");
            System.out.println("   LoadAllPlayers (Comment 4)");

            //if(newOne.getPAvgRating() == 0){
               // UpdatePAverage(AllPlayers);
            //}
            return AllPlayers;
        } catch (Exception e) {
            System.out.println("   Was not able to query database...");
        }
        System.out.println("   LoadAllPlayers (Comment 5 error)");
        return null;

    }

    public void UpdatePAverage()
    {
        ArrayList<Player> AllPlayers = LoadAllPlayers();
        try {
            // perform query on database and retrieve results
            for (int i = 0; i < AllPlayers.size(); i++) {
                Player cur = AllPlayers.get(i);
                double PAverage = Math.floor(calculate.CalcPlayerAvg(AllPlayers.get(i)));
                String sql = "UPDATE Player SET PAvgRating = " + PAverage + " WHERE PlayerID =" + cur.getPlayerID();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList LoadPlayerMarket()
    {
            ArrayList<Player> AllPlayers = LoadAllPlayers();
            ArrayList<Player> Market = new ArrayList<>();

            for(int i = 0; i < AllPlayers.size(); i++)
            {
                if(AllPlayers.get(i).getTeamID()== 0)
                    Market.add(AllPlayers.get(i));
            }
            return Market;
    }

    public ArrayList<MyTeam> loadTeams()
    {
        System.out.println("Using database...");

        ArrayList<MyTeam> Teams = new ArrayList<>();
        try {
            // perform query on database and retrieve results
            String sql = "SELECT * FROM Team";
            System.out.println("  Loading teams = " + sql);
            ResultSet result = stmt.executeQuery(sql);

            // while there are tuples in the result set, display them
            while (result.next() ) {
                // get values from current tuple
                int TeamID = result.getInt("TeamID");
                String TName = result.getString("TName");
                double TRating = result.getDouble("TRating");
                int TAttRating = result.getInt("TAttRating");
                int TDefRating = result.getInt("TDefRating");;
                String TCity = result.getString("TCity");
                int TRank = result.getInt("TRank");
                int TWins = result.getInt("TWins");
                int TLosses = result.getInt("TLosses");
                int TStaffLevel = result.getInt("TLosses");
                int TConfidence = result.getInt("TConfidence");

                MyTeam newOne = new MyTeam(TeamID,TName,TRating,TAttRating,TDefRating,TCity,TRank,TWins,TLosses);
                Teams.add(newOne);
            }
            System.out.println("Load successful");

            return Teams;
        } catch (Exception e) {
            System.out.println("   Was not able to query database...");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void disconnectDB()
    {
        System.out.println("Disconnecting from database...");

        try {
            //Important to close connection (same as with files)
            con.close();
            System.out.println("Connection ended");
        } catch (Exception ex) {
            System.out.println("   Unable to disconnect from database");
        }
    }

    public void UpdateStaff(int i)
    {
        try {
            // perform query on database and retrieve results
                String sql = "UPDATE Team SET TStaffLevel = " + i + " WHERE TeamID = " + 1;
                stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMatchWinnerLoser(int win, int loser)
    {

        String sql = "UPDATE Team SET TWins = TWins + 1 WHERE TeamID = " + win;
        String sql2 = "UPDATE Team SET TLosses = TLosses + 1  WHERE TeamID = " + loser;
        try {
            stmt.execute(sql);
            stmt.execute(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void UpdateStartingLineUp(ArrayList<Player> Squad, Player[] players) throws SQLException {
        //set all other players to false
        connectToDB();
        String sql = "UPDATE Player SET StartLineUp = " + 0 + " WHERE TeamID = " + 1;
        stmt.execute(sql);


        for(Player p : players)
        {
            String sql2 = "UPDATE Player SET StartLineUp = " + 1 + " WHERE TeamID = " + 1 + " AND PlayerID = " + p.getPlayerID();
            stmt.execute(sql2);
        }
    }

    public void UpdateStartingLineUp0(Player P) throws SQLException {
        //set all other players to false
        connectToDB();
        String sql = "UPDATE Player SET StartLineUp = " + 0 + " WHERE PlayerID = " + P.getPlayerID();
        stmt.execute(sql);
    }

    public void UpdateStartingLineUp1(Player P) throws SQLException {
        //set all other players to false
        connectToDB();
        String sql = "UPDATE Player SET StartLineUp = " + 1 + " WHERE PlayerID = " + P.getPlayerID();
        stmt.execute(sql);
    }


    /**
     *
     * @param player whose team has changed
     * @param i:  if i = 1 then player will be added to your team.  If i = 0 then player will be removed from your team
     *
     *         This method will be applied when player is bought, sold or when contract is terminated.
     */
    public void UpdatePlayerTeam(Player player, int i)
    {

        try {
            // perform query on database and retrieve results
            String sql = "UPDATE Player SET TeamID = " + i + " WHERE PlayerID = " + player.getPlayerNum();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdatePlayerTeam2(Player player, int i)
    {

        try {
            // perform query on database and retrieve results
            String sql = "UPDATE Player SET TeamID = " + i + " WHERE PlayerID = " + player.getPlayerID();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void CreateTeam(String TeamName, String TeamCity)
    {
        try {
            String sql = "UPDATE Team SET Team.TName =  '" + TeamName + "' WHERE Team.TeamID = " + 1;
            stmt.execute(sql);
            } catch (SQLException e1) {
            e1.printStackTrace();

            //insert manager name code
        }
    }


    public void UpdateContract(Player player, int Contract)//contract in weeks
    {
        int playerID = player.getPlayerID();

        try {
            String sql = "UPDATE Player SET PContract = " + Contract + " WHERE PlayerID = " + playerID;
            stmt.execute(sql);
        } catch (SQLException e1) {
            e1.printStackTrace();
            //insert manager name code
        }
    }

    public void UpdateFatigue(Player player) {
            try {
                String sql = "UPDATE Player SET PFatigue =  " + player.getPFatigue() + ", PInjuryPenalty = " + player.PInjuryPenalty + " WHERE PlayerID = " + player.getPlayerID();
                stmt.execute(sql);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
    }

    public void UpdatePInjury(Player curPlayer) {
        int bValue;
        if(curPlayer.PInjury==true)
            bValue = 1;
        else
            bValue = 0;

        try {
            String sql = "UPDATE Player SET PInjury =  " + bValue + " WHERE PlayerID = " + curPlayer.getPlayerID();
            stmt.execute(sql);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    public void UpdatePlayerSalary(Player player, double Salary){
        try {
            String sql = "UPDATE Player SET PSalary =  " + Salary  + " WHERE PlayerID = " + player.getPlayerNum();
            stmt.execute(sql);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void UpdatePlayerBonus(Player player, double Bonus){
        try {
            String sql = "UPDATE Player SET PperformanceBonus =  " + Bonus  + " WHERE PlayerID = " + player.getPlayerNum();
            stmt.execute(sql);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void UpdatePlayerBonus2(Player player, double Bonus)
    {

        try {
            // perform query on database and retrieve results
            String sql = "UPDATE Player SET PperformanceBonus =  " + Bonus  + " WHERE PlayerID = " + player.getPlayerID();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateLineup(Player player, int LineUp)
    {

        try {
            // perform query on database and retrieve results
            String sql = "UPDATE Player SET StartLineUp =  " + LineUp  + " WHERE PlayerID = " + player.getPlayerID();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdateOnSale1(Player player) //CurPlayer is on Sale
    {

        try {
            // perform query on database and retrieve results
            String sql = "UPDATE Player SET PonSale = 1  WHERE PlayerID = " + player.getPlayerID();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateOnSale2(Player player) //CurPlayer is no longer on sale
    {

        try {
            // perform query on database and retrieve results
            String sql = "UPDATE Player SET PonSale = 0  WHERE PlayerID = " + player.getPlayerID();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    //update contract

    //update rankings


    //getters and setters

    //views (such as display)
}