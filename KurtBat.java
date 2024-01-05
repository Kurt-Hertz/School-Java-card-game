//import java.util for scanner and arraylist
import java.util.*;
/**
 * KurtBat is the Brains of the game. It will hold the deck, an array of players and whos turn it is.
 *
 * @author (Kurt Hertz)
 * @version (11/18/2021)
 */
public class KurtBat
{
    //declaring constant variable
    static int basePlayers = 3; //and hand size

    //declaring instance variables
    KurtDeck deck = new KurtDeck();
    ArrayList<KurtPlayer> players;
    KurtPlayer currentPlayer;
    int total;  // score

    //declaring scanner to get user input
    Scanner in = new Scanner(System.in);

    //defaut constructor
    public KurtBat(){
        this.deck = deck.shuffel();
        this.players = new ArrayList<KurtPlayer>();
        for (int  i=0; i < basePlayers; i++){
            this.players.add(new KurtPlayer());
        }
        this.currentPlayer = this.players.get(0);
        this.total = 0;
    }

    //takes in the people playing the game constructor
    public KurtBat(KurtPlayer[] peoples){
        this.deck = deck.shuffel();
        this.players = new ArrayList<KurtPlayer>();
        for (int  i=0; i < peoples.length; i++){
            this.players.add(peoples[i]);
        }
        this.currentPlayer = this.players.get(0);
        this.total = 0;
    }

    //out puts the player and whos turn it is
    public String toString(){
        String str = "";
        str = deck +"\n\n";
        for (int  i=0; i < players.size(); i++){
            str +=  players.get(i)+ "\n";
        }
        str += "\n\nIt is currently " + currentPlayer.name + " turn to play.";
        return str;
    }

    //deals a fresh hand to every one
    public void hands(){
        //makes a shuffled base deck
        this.deck = new KurtDeck();
        this.deck = deck.shuffel();
        //deals 3 cards to every one
        for(int i = 0; i < players.size(); i++){
            players.set(i,players.get(i)).hand = this.deck.deal(basePlayers);
        }
    }

    //aces method if someone plays an ace, 1 or 11
    public byte aces(){
        byte num = 0;
        //checks if input is valid
        while(num != 1 && num != 11){
            System.out.println("What would you like your ace to be 1 or 11?");
            num = in.nextByte();
        }
        return num;
    }

    //nines method if someone plays a nine, reverse order
    public byte nines(){
        //varable to loop all of the players
        byte times = (byte)(players.size()/2);
        byte i = 0;
        //loop all of the players
        while(times != i){
            //swapes the players working inward
            KurtPlayer temp = players.get(players.size()-1-i);
            players.set(players.size()-1 -i, players.get(i));
            players.set(i, temp);
            i++;
        }

        //does not change the total
        return 0;
    }

    //swiches whos turn it is
    public KurtPlayer nextPlayer(){
        //see who it the current player
        byte i = (byte)players.indexOf(currentPlayer);

        //checks if it is the last player in the array then goes back to first else goes next
        if(players.get(players.size()-1) == this.currentPlayer){
            return players.get(0);
        }
        else{
            return players.get(i+1);
        }
    }

    //the order of the game
    public void playCard(KurtCard aCard){

        //see what cand they played and what it does
        this.total += checkMove(aCard);

        //see who it the current player
        byte i = (byte)players.indexOf(currentPlayer);

        //swap the card the player played with the first card from their hand 
        for(int j = 0; j<3;j++){
            if (players.get(i).hand.cards[j] == aCard){
                players.get(i).hand.swapCards(j, 0);
                break;
            }
        }

        //deals and new first card
        pickUp();

        //set roundOver() to somthing to see if the game is over
        boolean end = roundOver();

        //check if the game is over
        if(end != true){
            //if not next players turn and cardPlayed() (ask player what card they want to play)
            this.currentPlayer = nextPlayer();
            //outputs the new players turn
            System.out.println("\n\nIt is currently " + currentPlayer.name + "'s turn.");
            cardPlayed();
        }
        else{
            System.out.println("Game Over");
        }

    }

    //see what card they played and what it does
    public int checkMove(KurtCard aCard){
        int q = 0;
        if(aCard.rank == 1){
            //aces method if someone plays a ace
            return aces();
        }
        else if(aCard.rank == 4){
            //skips turn == adds 0 to total
            return 0;
        }
        else if(aCard.rank == 9){
            //nines method if someone plays a nine
            return nines();
        }
        else if(aCard.rank == 11){
            //jack removes ten from total
            return -10;
        }
        else if(aCard.rank == 12){
            //quenns make total to 99 
            //find what numer makes total 99
            System.out.println(this.total); // FIXME
            q = 99 - this.total;
            //return that
            return q;
        }
        else if(aCard.rank == 13){
            //king adds 20 to total
            return 20;
        }
        else if(aCard.rank == 3){
            kickNext();
            return 0;
        }
        else {
            //any other cand is not speical add its value
            return aCard.rank;
        }

    }

    //needs new canrd
    public void pickUp(){
        //see who it the current player
        byte i = (byte)players.indexOf(currentPlayer);
        //deals and new first card
        players.get(i).hand.cards[0] = deck.deal(1).cards[0];
    }

    //tacks in the players position in the array
    public void isOut(int p){
        //output who is out
        System.out.println(players.get(p).name + " is out of the game.\n********************************\n");
        //then remove them 
        players.remove(p);
    }

    // check if round is over and return winner if game is over
    public boolean roundOver(){
        boolean over = false; // round over
        //see who it the current player
        byte i = (byte)players.indexOf(currentPlayer);
        String bat = "";

        //see if round is over if not output total if true
        if(total <= 99){
            System.out.println("Your total is up to " + total);
            return false;
        }

        over = true;
        hands(); // deal fresh hands for next round

        //see what status that player is at and add a letter
        if(currentPlayer.status.equals("BA")){
            bat = "BAT";
            this.players.get(i).status = bat;
            //remove player
            isOut(i);
            //resaet total
            this.total = 0;
            //see if their is a winner/game over
            return isWinner();
        }
        else if (currentPlayer.status.equals("B")){
            bat = "BA";
        }
        else if (currentPlayer.status.equals("")){
            bat = "B";
        }
        else{
            System.out.println("check Over method");
        }

        //change players status
        this.players.get(i).status = bat;
        //output the new status
        System.out.println("Round over " + players.get(i).name + " is now at " +  players.get(i).status + ".\n/////////////////////////////////////////////////////////////////\n\n");
        //reset total
        this.total = 0;

        //return false if game is not over
        return false;
    }

    public boolean isWinner(){
        //see if thier is only one player left
        if(players.size() == 1){
            //true output winner and return true/game over
            System.out.println("The winner of the national \"BAT\" compotions is " + players.get(0).name);
            return true;
        }
        else {
            //game not over keep playing
            return false;
        }

    }

    //see what card the player plays
    public void cardPlayed(){
        int card = 0;
        String play = "";

        //output current player
        System.out.println(currentPlayer);
        System.out.println("What card do you want to play (1,2,3)\n");
        //get what card they want to play
        card = in.nextInt();

        //see if it is valid card
        if (card > basePlayers || card < 1){
            System.out.println("\nYou did not pick a card, try again\n");
            card = in.nextInt();
        }

        // update what card was played
        playCard(currentPlayer.hand.cards[card - 1]);

    }

    //sets up how many people will be in the game
    public void setUpNew(){
        int numP = 0;
        String name = "";
        KurtDeck d = new KurtDeck();
        KurtPlayer[] people;
        

        //see how many people will be in the game
        System.out.println("How many players will you have in the game (1 - 10)");
        numP = in.nextInt();

        KurtDeck suf = d.shuffel();
        people = new KurtPlayer[numP];

        //scanner bug
        in.nextLine();
        for(int i = 0; i<numP;i++){
            //see players names and set status to""
            System.out.println("What is player " + (i+1) + "'s name? ");
            name = in.nextLine();
            people[i] = new KurtPlayer(name,"",suf.deal(basePlayers));
        }
        //new people in game
        KurtBat c = new KurtBat(people);
        
        //see if only one player
        if(c.isWinner() == true){
            System.out.println("Game Over");
        }
        else{
            //start the game
            System.out.println(c);
            c.cardPlayed();
        }
    }

    //if 3 was played
    public void kickNext(){
        //see next player (might get kicked)
        KurtPlayer NextPlayer = nextPlayer();
        System.out.println(NextPlayer);
        String ans = "";
        int count = 0;

        in.nextLine(); 
        // check if majority kick player 
        for(int i = 0; i < players.size(); i++){
            System.out.println("\n" + players.get(i).name + " would you like to kick " + NextPlayer.name + ". type yes or no");
            ans = in.nextLine();
            if(ans.equals("yes")){
                count++;
            }
        }

        //see if majority picked
        if(count>(int)players.size()/2){
            isOut(players.indexOf(NextPlayer));
        }
        else{
            System.out.println(NextPlayer.name + " was not kicked.");
        }
    }

    public static void main(String[] args){
        KurtDeck d = new KurtDeck();
        KurtBat b = new KurtBat();
        System.out.println(b);
        KurtDeck suf = d.shuffel();

        // KurtPlayer[] k = new KurtPlayer[4];
        // k[0] = new KurtPlayer("Rich","",suf.deal(3));
        // k[1] = new KurtPlayer("Kurt","",suf.deal(3));
        // k[2] = new KurtPlayer("Hertz","",suf.deal(3));
        // k[3] = new KurtPlayer("Hertz","",suf.deal(3));
        // KurtBat c = new KurtBat(k);
        // System.out.println(c);
        // c.cardPlayed();
        b.setUpNew();

    }
}