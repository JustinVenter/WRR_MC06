/**
 * Created by Michael on 09/08/2016.
 */
public class Calculate {
    //Attributes found in Database

    //Methods
    /**
            * This method takes a players' attack and defence rating to determine the average rating.
            * Depending on the type of player i.e Forward( Wing, CenterForward), Midfielder( Left Mid, center mid, right mid)
            * defence( Center back, right back, left back), different calculations are used.
            * @param P- A single player
    * @return- The average rating of that player based on their position.
            */
    public double CalcPlayerAvg(Player P){

        double playerAvgRating=0;
        // For players that are in a forward position
        if((P.PPos.contains("W")||(P.PPos.contains("CF"))))
        {
            playerAvgRating=(P.getPAttRating()*0.9)+(P.getPDefRating()*0.1);
        }
        //For players that are in Midfield position
        if((P.PPos.contains("M"))){

            playerAvgRating=(P.getPAttRating()*0.5)+(P.getPDefRating()*0.5);
        }
        //For players playing in a defencive position
        if(P.PPos.contains("B")){

            playerAvgRating= (P.getPAttRating()*0.1)+(P.getPDefRating()*0.9);
        }
        // for the Goalkeeper
        if(P.PPos.contains("GK")){

            playerAvgRating=(P.getPDefRating());
        }

        return  playerAvgRating;
    }

    //getters and setters

    //views (such as display)
}
