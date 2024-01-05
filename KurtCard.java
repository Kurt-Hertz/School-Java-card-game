/**
 * The KurtCard class is a class that holds a card object with instances of int rank and suit.
 *
 * @author (Kurt Hertz)
 * @version (11/18/2021)
 */
public class KurtCard
{
    // instance variables
    int rank;
    int suit;

    //defalut constructor
    public KurtCard(){
        this.rank = 1;
        this.suit = 1;
    }

    //all constructor;
    public KurtCard(int rank,int suit)  
    { 
        this.rank  =  rank;
        this.suit  =  suit;
    } 

    //to string that swiches the int rank and suit to cards string suit and rank
    public String toString(){
        String[] ranks = {null,"Ace", "2", "3","4","5","6","7","8","9","10","Jack","Queen","King"};
        String[] suits = {"Clubs","Diamonds","Hearts","Spades"};
        String s = ranks[this.rank] + " of " + suits[this.suit];
        return s;
    }

    //returns true if two cards = each other
    public boolean equals(KurtCard that){
        return this.rank == that.rank && this.suit == that.suit;
    }

    //returns a number to see if the card is higher or lower then the given card (that card)
    public int compareTo(KurtCard that){
        if (this.suit < that.suit){
            return -1;
        }
        if (this.suit > that.suit){
            return 1;
        }
        if(this.rank < that.rank) {
            return -1;
        }
        if(this.rank > that.rank){
            return 1;
        }
        return 0;
    }

    //searches thourgh every card to see it any are ==
    public static int search(KurtCard[] KurtCards, KurtCard target){
        for (int i = 0; i< KurtCards.length; i++){
            if (KurtCards[i].equals(target)){
                return i;
            }
        }
        return -1;
    }

    //better search because to works from the middel and finds what side the card is on.
    public static int binarySearch(KurtCard[] KurtCards, KurtCard target){
        int low = 0;
        int high = KurtCards.length -1;

        while (low <= high) {
            int mid = (low + high)/2;
            int comp = KurtCards[mid].compareTo(target);

            if (comp == 0){
                return mid;
            }
            else if (comp < 0){
                low = mid+1;
            }
            else{
                high = mid -1;
            }
        }
        return -1;
    }

    //an overloaded search above
    public static int binarySearch(KurtCard[] KurtCards, KurtCard target,int low, int high){
        if(high < low){
            return -1;
        }
        int mid = (low + high)/2;
        int comp = KurtCards[mid].compareTo(target);

        if (comp == 0){
            return mid;
        }
        else if (comp < 0 ){
            return binarySearch(KurtCards, target, mid+1, high);
        }
        else{
            return binarySearch(KurtCards, target, low,mid-1);
        }

    }

    //makes a base deck with no jokers
    public KurtCard[] makeDeck(){
        KurtCard[] KurtCards = new KurtCard[52];
        int index = 0;

        for(int suit = 0; suit <4; suit++){
            for (int rank = 1; rank < 14; rank++){
                KurtCards[index] = new KurtCard(rank, suit);
                index++;
            }
        }
        return KurtCards;
    }

    //returns  a  histogram  of  the  suits  in  the  hand. 
    public int[] makeHist(KurtCard[] KurtCards){
        int[] suitNum = {0,0,0,0};

        for (int i=0;i<KurtCards.length;i++){
            if (KurtCards[i].equals(0)){
                suitNum[0] += suitNum[0];
            }
            else if (KurtCards[i].equals(1)){
                suitNum[1] += suitNum[1];
            }
            else if (KurtCards[i].equals(2)){
                suitNum[2] += suitNum[2];
            }
            else{
                suitNum[3] += suitNum[3];
            }
        }
        return suitNum;
    }

    //see if player has fluch
    public boolean hasFlush(KurtCard[] KurtCards){
        int[] numSuit = makeHist(KurtCards);

        for(int i =0;i<4;i++){
            if (numSuit[i] >= 5)
                return true;
        }
        return false;

    }

    //test if works
    public static void main(String args[]){
        KurtCard[]  KurtCards  =  new  KurtCard[52];

        if  (KurtCards[0]  ==  null)  { 
            System.out.println("No  KurtCard  yet!"); 
        } 

        System.out.println(KurtCards[0].rank);  

    }
}