package FLMfiles;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Michael on 10/08/2016.
 */
public class Market implements Serializable {
    private ArrayList<Player> PendingAcceptedOffers; //If added to accepted players. Salary and bonus will be set to offer. Therefore adjust database.
    private ArrayList<Player> PendingRejectedOffers;
    private ArrayList<Player> PendingSales; //All Players sold after few weeks. Bonus is set to 0.

    Database db = new Database();

    MyTeam myTeam = new MyTeam();



    public Market(){
        PendingAcceptedOffers = new ArrayList<>();
        PendingRejectedOffers = new ArrayList<>();
        PendingSales = new ArrayList<>();
    }

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
    public void AddSale(Player P){
        PendingSales.add(P);
    }
    public void RemoveSale(Player P){
        PendingSales.remove(P);
    }

    public void DetermineAccepted() throws IOException, ClassNotFoundException {
        if(PendingAcceptedOffers.size()>0) {
            System.out.println("Determining Accepted");
            ArrayList<Player> temp = new ArrayList<>();
            for (int x = 0; x < PendingAcceptedOffers.size(); x++) {
                //for(Player P: PendingAcceptedOffers){
                Player P = PendingAcceptedOffers.get(x);
                if (P.getWeeksPending() == 0) {
                    System.out.println("Should be placed in myTeam");
                    //PendingAcceptedOffers.remove(P);
                    temp.add(P);
                    db.connectToDB();
                    db.UpdatePlayerTeam2(P, 1);

                    User user = new User();
                    user = user.readUser();

                    MyAccount account = new MyAccount();
                    account = account.readAccount();

                    MyNewsFeed myNewsFeed = new MyNewsFeed();
                    myNewsFeed = myNewsFeed.readNews();

                    Transaction T = new Transaction(P.getPValue(), "Purchased player", false, user.getWeek());
                    account.UpdateBank(T);

                    NewsFeedElement N = new NewsFeedElement(user.getWeek(), "Player accepted offer: " + P.GetPlayerFullName(), false);
                    myNewsFeed.AddNews(N);

                    //Show confirmation
                }
            }
            for(int x = 0; x< temp.size(); x++) {
                Player P = temp.get(x);
                PendingAcceptedOffers.remove(P);
            }
        }
    }
    public void DetermineRejected() throws IOException, ClassNotFoundException {
        if(PendingRejectedOffers.size()>0) {
            System.out.println("Determining Rejected");
            ArrayList<Player> temp = new ArrayList<>();
            for (int x = 0; x < PendingRejectedOffers.size(); x++) {
                Player P = PendingRejectedOffers.get(x);
                if (P.getPendingWeeks() == 0) {
                    //PendingRejectedOffers.remove(P);
                    temp.add(P);
                    db.connectToDB();
                    db.UpdatePlayerTeam2(P, 0);

                    User user = new User();
                    user = user.readUser();

                    MyNewsFeed myNewsFeed = new MyNewsFeed();
                    myNewsFeed = myNewsFeed.readNews();

                    NewsFeedElement N = new NewsFeedElement(user.getWeek(), "Player rejected offer: " + P.GetPlayerFullName(), false);
                    myNewsFeed.AddNews(N);
                    //Adjust Bank
                    //Show confirmation
                }
            }
            for(int x = 0; x< temp.size(); x++) {
                Player P = temp.get(x);
                PendingRejectedOffers.remove(P);
            }
        }
    }
    public void DetermineSold() throws IOException, ClassNotFoundException, SQLException {
        if(PendingSales.size()>0) {
            System.out.println("Determining Sales");
            ArrayList<Player> temp = new ArrayList<>();
            //temp = PendingSales;
            for (int x = 0; x < PendingSales.size(); x++) {
                Player P = PendingSales.get(x);
                if (P.getWeeksPending() == 0) {

                    myTeam = db.loadMyTeam();
                    myTeam.loadSquad();
                   Player [] players = new Player[11];
                   players =  myTeam.getStartingLineUp();

                    boolean found = false;

                    for(int i = 0; i<players.length; i++) {
                        Player player = players[i];
                        if (player.getPlayerID() == P.getPlayerID()) {
                            found = true;
                            break;
                        }
                    }

                    if(found){ //Player is in starting lineup. Therefore must put another player into the lineup.
                        db.connectToDB();
                        db.UpdateStartingLineUp0(P);

                        ArrayList<Player> curPlayers = myTeam.getPosPlayers(P.getPPos());
                        for(Player CurPlayer: curPlayers){
                            if(CurPlayer.getPlayerID() != P.getPlayerID()){
                                db.connectToDB();
                                db.UpdateStartingLineUp1(CurPlayer);
                                break;
                            }
                        }
                    }

                    db.connectToDB();
                    db.UpdatePlayerBonus2(P, 0);
                    //PendingSales.remove(P);
                    temp.add(P);
                    db.connectToDB();
                    db.UpdatePlayerTeam2(P, 0);
                    db.connectToDB();
                    db.UpdateLineup(P, 0);
                    db.connectToDB();
                    db.UpdateOnSale2(P);

                    User user = new User();
                    user = user.readUser();

                    MyAccount account = new MyAccount();
                    account = account.readAccount();

                    MyNewsFeed myNewsFeed = new MyNewsFeed();
                    myNewsFeed = myNewsFeed.readNews();

                    Transaction T = new Transaction(P.getPValue(), "Player sold", true, user.getWeek());
                    account.UpdateBank(T);

                    NewsFeedElement N = new NewsFeedElement(user.getWeek(), "Player sold: " + P.GetPlayerFullName(), false);
                    myNewsFeed.AddNews(N);

                    SquadController.Remove(P.getPlayerID());

                    //SquadController.get().remove(SquadController.CurPlayer);
                    //Adjust Bank
                    //Show confirmation
                }
            }
            for(int x = 0; x< temp.size(); x++){
                Player P = temp.get(x);
                PendingSales.remove(P);
            }
        }
    }
    public void AddWeek() {
        if (PendingAcceptedOffers.size() > 0) {
            for (int x = 0; x < PendingAcceptedOffers.size(); x++) {
                Player P = PendingAcceptedOffers.get(x);
                P.setWeeksPending(P.getWeeksPending() - 1);
            }
        }
        if (PendingRejectedOffers.size() > 0) {
            for (int x = 0; x < PendingRejectedOffers.size(); x++) {
                Player P = PendingRejectedOffers.get(x);
                P.setWeeksPending(P.getWeeksPending() - 1);
            }
        }
        if (PendingSales.size() > 0) {
            for (int x = 0; x < PendingSales.size(); x++) {
                Player P = PendingSales.get(x);
                P.setWeeksPending(P.getWeeksPending() - 1);
            }
        }
    }

    public void saveMarketDetails() throws FileNotFoundException {

        try{
            File file = new File("marketDetails.obj");
            file.delete();

            FileOutputStream fout = new FileOutputStream("marketDetails.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            fout.close();
            System.out.println("Saved");

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    public Market readMarket() throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream("marketDetails.obj");
        ObjectInputStream ois = new ObjectInputStream(fin);
        Market myMarket = (Market) ois.readObject();
        fin.close();
        ois.close();
        return myMarket;
    }

}
