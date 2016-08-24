import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * Created by Michael on 09/08/2016.
 */


public class Database {

    // fields needed to access database
    // actual connection to db
    private Connection con = null;
    // object used to issue SQL commands
    private Statement stmt = null;


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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectDB() {
        System.out.println("Disconnecting from database...");

        try {
            //Important to close connection (same as with files)
            con.close();
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
