package FLMfiles;

import java.util.Random;

/**
 * Created by Michael on 06/09/2016.
 */
public class PlayerNode {
    private PlayerNode Parent;
    private PlayerNode Left;
    private PlayerNode Right;
    private PlayerNode Middle;
    private Player value;//would be player
    private Player defendervalue;

    public void setDefendervalue(Player defendervalue) {
        this.defendervalue = defendervalue;
    }



    public Player getValue() {
        return value;
    }

    public Player getDefendervalue()
    {
        return defendervalue;
    }

    public void setValue(Player value) {
        this.value = value;
    }



    public PlayerNode(Player Value){
        this.value = Value;
        this.Left = null;
        this.Middle = null;
        this.Right = null;
    }

    public PlayerNode(Player Value, Player defendervalue){
        this.value = Value;
        this.Left = null;
        this.Middle = null;
        this.Right = null;
        this.defendervalue = defendervalue;
    }

    public PlayerNode(PlayerNode L, PlayerNode M, PlayerNode R, Player Value){
        this.value = Value;
        this.Left = L;
        this.Middle = M;
        this.Right = R;
    }

    public PlayerNode(PlayerNode P, PlayerNode L, PlayerNode M, PlayerNode R, Player Value){
        this.value = Value;
        this.Left = L;
        this.Middle = M;
        this.Right = R;
        this.Parent = P;
    }

    public void setChildren(PlayerNode L, PlayerNode M, PlayerNode R)
    {
        this.Left = L;
        this.Middle = M;
        this.Right = R;
    }

    public void setParent(PlayerNode P) {
        this.Parent = P;
    }

    public PlayerNode getLeft() {
        if(this.Left!= null)
            return Left;
        return null;//return to root
    }

    public void setLeft(PlayerNode left) {
        this.Left = left;
    }

    public PlayerNode getRight() {
        if(this.Right!= null)
            return Right;
        return null;//return to root
    }

    public void setRight(PlayerNode right) {
        this.Right= right;
    }

    public PlayerNode getMiddle() {
        if(this.Middle!= null)
            return Middle;
        return null;//return to root
    }

    public void setMiddle(PlayerNode middle) {
        Middle = middle;
    }

    public boolean isLeaf() {
        if(this.getMiddle() == null)
            return true;
        else
            return false;
    }

    public boolean hasnext()
    {
        if(this.getMiddle()==null)
           return false;
        else return true;
    }


    public PlayerNode bestPass(){
        Player L = (Player)this.getLeft().getValue();
        Player M = (Player)this.getMiddle().getValue();
        Player R = (Player)this.getRight().getValue();
        double max = Math.max(L.getPAvgRating(), M.getPAvgRating());
        max = Math.max(max, R.getPAvgRating());

        PlayerNode[] children = {this.getLeft(), this.getMiddle(), this.getRight()};

        if(L.getPAvgRating() == max)
            return this.getLeft();
        if(M.getPAvgRating() == max)
            return this.getMiddle();
        if(R.getPAvgRating() == max)
            return this.getRight();

        return this.RandomPass();
    }

    public boolean shoot(Player opposition){
        Player curPlayer = this.getValue();
        Random probability = new Random();
        int Prob = probability.nextInt(100);
        if(Prob < (curPlayer.getPAttRating() - opposition.getPDefRating()))
            return true;
        else
            return false;
    }

    public PlayerNode RandomPass()
    {
        PlayerNode[] children = {this.getLeft(), this.getMiddle(), this.getRight()};

        //if doesn't return anything
        Random random = new Random();
        return children[random.nextInt(2)];
    }

}
