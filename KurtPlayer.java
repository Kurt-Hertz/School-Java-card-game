/**
 * KurtPlayer will hold people diffed by a name, hand and status (how close they are to out). People will be uesd to play are "BAT" game. 
 *
 * @author (Kurt Hertz)
 * @version (11/18/2021)
 */
public class KurtPlayer
{
    //declaring constant of hand size
    static int numHand = 3;
    
    //declaring instance variables
    KurtDeck hand;
    String name;
    String status;

    //defaut constructor
    public KurtPlayer(){
        KurtDeck hand = new KurtDeck(numHand);
        this.name = "TBD";
        this.status = "";
    }

    //constructor for player with no hand (can be used with hands())
    public KurtPlayer(String name, String status){
        KurtDeck hand = new KurtDeck(numHand);
        this.name = name;
        this.status = status;
    }

    //all constructor
    public KurtPlayer(String name, String status, KurtDeck hand){
        this.name = name;
        this.status = status;
        this.hand = new KurtDeck(numHand);
        this.hand = hand;
    }

    //to string outputs players name and status if it has letters
    public String toString(){
        String str = "";
        if (status.equals(""))
            str = this.name + " with a hand of "+ this.hand + " and no letters.";
        else
            str = this.name + " with a hand of "+ this.hand + " and letters " + this.status + ".";

        return str;
    }

}