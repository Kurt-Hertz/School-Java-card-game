import java.util.*;
import java.lang.Math;   
/**
 * KurtDeck holds and array of 52 card and can (most used metods are) shuffle and deal
 *
 * @author (Kurt Hertz)
 * @version (11/18/2021)
 */
public class KurtDeck
{
    //declaring instance variables
    KurtCard[] cards;

    //constructor if you only know the decks size
    public KurtDeck(int n){
        this.cards = new KurtCard[n];
    }

    //default constructor for a defaut deck
    public KurtDeck(){
        this.cards = new KurtCard[52];

        int index = 0;
        for(int suit = 0;suit<4;suit++){
            for(int rank = 1; rank <= 13; rank++){ 
                this.cards[index] = new KurtCard(rank, suit); 
                index++;
            }
        }
    }

    //toString outputs the deck by looping through it
    public String toString(){
        String str = "(";
        for  (int i = 0; i < this.cards.length; i++){
            if (i == 0){
                str += this.cards[i];
            }
            else{
                str += ", " + this.cards[i];
            }
        }
        return str+")";
    }

    //random number generator
    public int rand(int max){
        int ran = (int)(Math.random()*(max));
        return ran;
    }

    //swapes two given cards
    public void swapCards(int i, int j){ 
        KurtCard temp = this.cards[j];
        this.cards[j] = this.cards[i];
        this.cards[i] = temp;
    } 

    //Shuffels deck by makeing a deck same size then getting random index of origonl deck and swaping it with the i index 
    //then move it in to the deck and return (so it returns a deck not Card[])
    public KurtDeck shuffel(){
        int ran = 0;
        //want to return deck so declaring a deck
        KurtDeck sufD = new KurtDeck(this.cards.length);
        for  (int  i=0;  i<this.cards.length;  i++){
            //scrambles the cards
            ran = rand(this.cards.length);
            swapCards(i,ran);
        }
        sufD.cards = this.cards;
        
        //want to return a deck
        return sufD;
    }

    //removes the first number(n) of card in the deck by setting it to null
    public void removeFront(int n){
        for (int  i=0; i<n; i++){
            this.cards[i] = null;
        }
    }

    //moves cards from a deck into a sub deck
    public KurtDeck subKurtDeck(KurtDeck KurtDeck, int low, int high){
        KurtDeck sub = new KurtDeck(high-low+1);
        for (int i = 0; i<(high-low+1); i++){
            sub.cards[i] = KurtDeck.cards[low+i]; 
        } 
        return sub; 
    } 

    //deals the first cards of a deck
    public KurtDeck deal(int n){
        KurtDeck hand = new KurtDeck(n);
        int i = 0, j = 0;
        //loops the amount of card being delt
        while(i < this.cards.length && n > 0) {
            //don't deal null cards
            if(this.cards[i] != null) {
                //put cards in hand then set deck cards to null
                hand.cards[j] = this.cards[i];
                this.cards[i] = null;
                n--;
                j++;
            }
            i++;
        }
        //returns deck
        return hand;

    }

    public static void main(String[] args){
        KurtDeck d = new KurtDeck();
        System.out.println(d);
        System.out.println("shuffel: ");
        KurtDeck suf = d.shuffel();
        System.out.println(suf);
        KurtDeck hand = suf.deal(5);
        System.out.println(hand);
        System.out.println(suf);

    }
}
