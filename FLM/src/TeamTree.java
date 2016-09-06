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

    public TeamTree(MyTeam myTeam)
    {
        Player[] StartingLineup = myTeam.StartingLineUp;

        Goalie = new PlayerNode(StartingLineup[10]);

        FB = new PlayerNode(StartingLineup[9]);
        CB = new PlayerNode(StartingLineup[8]);
        LB = new PlayerNode(StartingLineup[7]);
        RB = new PlayerNode(StartingLineup[6]);

        LM = new PlayerNode(StartingLineup[5]);
        CM = new PlayerNode(StartingLineup[4]);
        RM = new PlayerNode(StartingLineup[3]);

        LF = new PlayerNode(StartingLineup[2]);
        CF = new PlayerNode(StartingLineup[1]);
        RF = new PlayerNode(StartingLineup[0]);

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

}
