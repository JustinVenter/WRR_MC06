package FLMfiles;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Michael on 10/08/2016.
 */
public class Market implements Serializable {
    private ArrayList<Player> PendingAcceptedOffers; //If added to accepted players. Salary and bonus will be set to offer. Therefore adjust database.
    private ArrayList<Player> PendingRejectedOffers;
    private ArrayList<Player> PendingSales; //All Players sold after few weeks. Bonus is set to 0.

    Database db = new Database();



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

    public void DetermineAccepted() throws IOException, ClassNotFoundException {
        if(PendingAcceptedOffers.size()>0) {
            System.out.println("Determining Accepted");
            for (int x = 0; x < PendingAcceptedOffers.size(); x++) {
                //for(Player P: PendingAcceptedOffers){
                Player P = PendingAcceptedOffers.get(x);
                if (P.getWeeksPending() == 0) {
                    System.out.println("Should be placed in myTeam");
                    PendingAcceptedOffers.remove(P);
                    db.connectToDB();
                    db.UpdatePlayerTeam2(P, 1);

                    User user = new User();
                    user = user.readUser();

                    MyAccount account = new MyAccount();
                    account = account.readAccount();

                    Transaction T = new Transaction(P.getPValue(), "Purchased player", false, user.getWeek());
                    account.UpdateBank(T);

                    //Adjust Bank
                    //Show confirmation
                }
            }
        }
    }
    public void DetermineRejected(){
        if(PendingRejectedOffers.size()>0) {
            System.out.println("Determining Rejected");
            for (int x = 0; x < PendingRejectedOffers.size(); x++) {
                Player P = PendingRejectedOffers.get(x);
                if (P.getPendingWeeks() == 0) {
                    PendingRejectedOffers.remove(P);
                    db.connectToDB();
                    db.UpdatePlayerTeam2(P, 0);
                    //Adjust Bank
                    //Show confirmation
                }
            }
        }
    }
    public void DetermineSold(){
        if(PendingSales.size()>0) {
            System.out.println("Determining Sales");
            for (int x = 0; x < PendingSales.size(); x++) {
                Player P = PendingSales.get(x);
                if (P.getWeeksPending() == 0) {
                    PendingSales.remove(P);
                    db.connectToDB();
                    db.UpdatePlayerTeam2(P, 0);
                    //Adjust Bank
                    //Show confirmation
                }
            }
        }
    }
    public void AddWeek() {
        if (PendingAcceptedOffers.size() > 0) {
            for (Player P : PendingAcceptedOffers) {
                P.setWeeksPending(P.getWeeksPending() - 1);
            }
        }
        if (PendingSales.size() > 0) {
            for (Player P : PendingRejectedOffers) {
                P.setPendingWeeks(P.getPendingWeeks() - 1);
            }
        }
        if (PendingSales.size() > 0) {
            for (Player P : PendingSales) {
                P.setPendingWeeks(P.getPendingWeeks() - 1);
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
