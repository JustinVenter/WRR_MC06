package FLMfiles;

/**
 * Created by Michael on 06/09/2016.
 */
public class TeamTree {

    PlayerNode Goalie;

    PlayerNode FB;
    PlayerNode CB;
    PlayerNode LB;
    PlayerNode RB;

    PlayerNode LM;
    PlayerNode CM;
    PlayerNode RM;

    PlayerNode LF;
    PlayerNode CF;
    PlayerNode RF;

    PlayerNode Shoot;

    public TeamTree(MyTeam myTeam, MyTeam defTeam)
    {
        Player[] StartingLineup = myTeam.StartingLineUp;
        Player[] DefStartingLineup = defTeam.StartingLineUp;

        Goalie = new PlayerNode(StartingLineup[10], new Player(0, 0, 0));

        FB = new PlayerNode(StartingLineup[9], DefStartingLineup[8]);
        CB = new PlayerNode(StartingLineup[8], DefStartingLineup[1]);
        LB = new PlayerNode(StartingLineup[7], DefStartingLineup[0]);
        RB = new PlayerNode(StartingLineup[6], DefStartingLineup[2]);

        LM = new PlayerNode(StartingLineup[5], DefStartingLineup[3]);
        CM = new PlayerNode(StartingLineup[4], DefStartingLineup[4]);
        RM = new PlayerNode(StartingLineup[3], DefStartingLineup[5]);

        LF = new PlayerNode(StartingLineup[2], DefStartingLineup[6]);
        CF = new PlayerNode(StartingLineup[1], DefStartingLineup[8]);
        RF = new PlayerNode(StartingLineup[0], DefStartingLineup[7]);

        Shoot = new PlayerNode(null);

        Goalie.setChildren(LB, FB, RB);

        FB.setChildren(LB, CB, RB);
        CB.setChildren(LM, CM, RM);
        LB.setChildren(LM, CM, CB);
        RB.setChildren(CB, CM, RM);

        CM.setChildren(LF, CF, RF);
        LM.setChildren(LF, CF, CM);
        RM.setChildren(CM, CF, RF);

        CF.setChildren(null, Shoot, null);
        LF.setChildren(null, Shoot, CF);
        RF.setChildren(CF, Shoot, null);

        Shoot.setChildren(null, null, null);
    }

    public PlayerNode getRoot(){
        return Goalie;
    }

}
