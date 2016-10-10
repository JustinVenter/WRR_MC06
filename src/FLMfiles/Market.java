package FLMfiles;

import java.util.ArrayList;

/**
 * Created by Michael on 10/08/2016.
 */
public class Market {
    private ArrayList<Player> PendingAcceptedOffers;
    private ArrayList<Player> PendingRejectedOffers;
    private ArrayList<Player> PendingSales;

    public static Player CurPlayer;

    public void AddAcceptedPlayer(Player P){
        PendingAcceptedOffers.add(P);
    }
    public void AddRejectedPlayer(Player P){
        PendingRejectedOffers.add(P);
    }
    public void RemoveAccepted(Player P){
        PendingAcceptedOffers.remove(P);
    }
    public void RemoveRejected(Player P){
        PendingRejectedOffers.remove(P);
    }

}
