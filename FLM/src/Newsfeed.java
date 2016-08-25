import java.util.ArrayList;

/**
 * Created by Michael on 25/08/2016.
 */
public class Newsfeed {

    public static ArrayList notifications= new ArrayList();

    // Player injury notification

    public static void notifyInjury(Player P){

        String injuryMessage="";

        if(P.isPInjury()==true){

            injuryMessage= "Player Injury: "+P.getPName()+" "+P.getPSurname()+" "+ "has been injured, and will not be able to play until recovered. Hint: Don't let fatigue level reach 0.";
            notifications.add(injuryMessage);
        }


    }

    // Player contract notification.

    public static void notifyPlayerContract(Player P){

        String contractMessage="";
        if(P.getPContract()==0){

            contractMessage= "Contract expired: "+ P.getPName()+" "+P.getPSurname()+" has been return to the player market.";
            notifications.add(contractMessage);
        }
    }


    // Teams result notification ( WIN/ LOSE)
}
