import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * Created by Michael on 25/08/2016.
 */
public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        database.UpdatePAverage();
        ArrayList<Player> myteam = database.loadMyTeam();

        for(int i = 0; i < myteam.size(); i++)
        {
            Player curPlayer = myteam.get(i);

            System.out.println(curPlayer.toString());
        }

        database.disconnectDB();
    }
}
