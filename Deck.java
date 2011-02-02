import java.util.*;
import java.math.*;

public class Deck{
	private int cardCount;
	private Card[] myCards;
	Deck(){
	}
	public void createPlayingCards(){
		cardCount = 52;
		myCards = new Card[cardCount];
		int numberOfSuits = 4;
		int numberOfCards = 13;
		int count = 0;
		for(int i = 0; i < numberOfSuits; i++){
			for(int j = 0; j < numberOfCards; j++){
				myCards[count++] = new Card(i, j<10 ?j+1: 10, j+1);
			}
		}
	}
	public void printDeck(){
		for(int i = 0; i < cardCount; i++)
			printCard(i);
	}
	public void printCard(int i){
		System.out.println("Card: "+myCards[i].getSuit()+" "+myCards[i].getValue()+" "+myCards[i].getFace());
	}
	public void shuffle(){
		Collections.shuffle(Arrays.asList(myCards));
	}
	public int getCut(){
		Random deckCutter = new Random();
		int cutCard;
		while(true){
			cutCard = deckCutter.nextInt(52);
			if(!myCards[cutCard].getUse())
				break;
		}
		return cutCard;
	}
	public boolean getUse(int i){
		return myCards[i].getUse();
	}
	public String getSuit(int i){
		return myCards[i].getSuit();
	}
	public String getFace(int i){
		return myCards[i].getFace();
	}
	public int getValue(int playedCard){
		return myCards[playedCard].getValue();
	}
	public int getNumValue(int card){
		return myCards[card].getNumValue();
	}
}
