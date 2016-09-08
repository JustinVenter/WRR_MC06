import java.util.Random;

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

    public Fixture LMAlogrithm(MyTeam home, MyTeam away){


        return MyGameHome(home, away);

    }

    public Fixture MyGameHome(MyTeam AttackTeam, MyTeam DefendTeam){

        int MyScore = 0;
        int OppositionScore = 0;

        TeamTree teamTree = new TeamTree(AttackTeam, DefendTeam);

        int MaxTime = 90;//Game done when 90
        int Time = 0;//current time


        while(Time < MaxTime)
        {
            PlayerNode curNode = teamTree.getRoot();


            while((curNode.hasnext() != false)&&(Time < MaxTime))
            {
                Random Decisionpass = new Random();
                Player curPlayer = curNode.getValue();
                double prob = (double) Decisionpass.nextInt(100);
                if(curNode.getMiddle().getValue()==null)//shoot
                {
                    boolean goal = curNode.shoot(curNode.getDefendervalue());

                    if(goal)
                        MyScore = MyScore + 1;
                    curNode = teamTree.getRoot();
                }
                else
                if( prob < (curPlayer.getPAvgRating() - (curNode.getDefendervalue().getPDefRating()/3)))
                {
                    if(Decisionpass.nextInt(100) < (curPlayer.getPAvgRating()/2))
                        curNode = curNode.bestPass();
                    else
                        curNode.RandomPass();
                }
                Time = Time + 1;
            }
        }

        //teamTree.

        return new Fixture(AttackTeam, DefendTeam, String.valueOf(MyScore));
    }

    public Fixture MyGameAway(Team opposition, MyTeam myTeam){
        return new Fixture(opposition, myTeam, "Winner");
    }

    public Fixture BotGame(Team home, Team away){
        return new Fixture(home, away, "Winner");
    }

    //getters and setters

    //views (such as display)
}
