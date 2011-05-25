import java.util.*;
import java.math.*;
public class Cribbage{
	private Deck cribbageDeck;
	private Player playerOne, playerTwo, crib;
	private int[] boardCards = new int[8];
	private int boardScore, cutCard;
	Cribbage(){
		cribbageDeck = new Deck();
		playerOne = new Player(1, 0, 6);
		playerOne.setDealer();
		playerTwo = new Player(2, 0, 6);
		crib = new Player(3, 0, 4);
		cribbageDeck.createPlayingCards();
	}
	public int play(){
		deal();
		askForCribCards();
		cut();
		boardScore = 0;
		playCards();
		scoreHands();
		playerOne.setDealer();
		playerTwo.setDealer();
		if(playerOne.getScore() >= 121)
			return 1;
		else if(playerTwo.getScore() >= 121)
			return 2;
		else
			return 0;
	}
	public void deal(){
		cribbageDeck.shuffle();
		int[] firstHand = {0,2,4,6,8,10};
		int[] secondHand = {1,3,5,7,9,11};
		sort(firstHand);
		sort(secondHand);
		if(playerOne.amIDealing()){
			playerOne.setCards(firstHand);
			playerTwo.setCards(secondHand);
		}else{
			playerOne.setCards(firstHand);
			playerTwo.setCards(secondHand);
		}
	}		
	public void askForCribCards(){
		int[] pOneCards = playerOne.sendToCrib();
		int[] pTwoCards = playerTwo.sendToCrib();
		int[] cribCards = Arrays.copyOf(pOneCards, 4);
		System.arraycopy(pTwoCards, 0, cribCards, 2, 2);
		sort(cribCards);
		crib.setCards(cribCards);
	}
	public void cut(){
		cutCard = cribbageDeck.getCut();
	}
	public void emptyBoard(){
		for(int i = 0; i < boardCards.length; i++)
			boardCards[i] = 0;
	}
	public void playCards(){
		emptyBoard();
		// true is pOne false is pTwo
		boolean playersTurn = playerOne.amIDealing();
		printASDF(playerOne.getHand());
		boardScore = 0;
		int nextBoardSpot = 0;
		int cardPlayed = -1;
		if(playersTurn){
			//add the first points to the board
			boardScore = cribbageDeck.getValue(playerOne.playCard(cardPlayed));
			playerOne.goodPlay(cardPlayed);
		}else{
			boardScore = cribbageDeck.getValue(playerTwo.playCard(cardPlayed));
			playerTwo.goodPlay(cardPlayed);
		}
		playersTurn = !playersTurn;
		boardCards[nextBoardSpot++] = cardPlayed;
		System.out.println("boardScore = "+boardScore);
		while(playerOne.getHand()[0] != -1 && playerTwo.getHand()[0] != -1){
			cardPlayed = -1;
			boolean pOneHasMove = checkPlayable(playerOne.getHand());
			boolean pTwoHasMove = checkPlayable(playerTwo.getHand());
			if(pOneHasMove && pTwoHasMove){
				if(playersTurn){
					cardPlayed = playerOne.playCard(cardPlayed);
					while(cribbageDeck.getValue(cardPlayed) + boardScore > 31)
						cardPlayed = playerOne.playCard(cardPlayed);
					playerOne.goodPlay(cardPlayed);
				}else{
					cardPlayed = playerTwo.playCard(cardPlayed);
					while(cribbageDeck.getValue(cardPlayed) + boardScore > 31)
						cardPlayed = playerTwo.playCard(cardPlayed);
					playerTwo.goodPlay(cardPlayed);
				}
				//check for scores.
				boardScore += cribbageDeck.getValue(cardPlayed);
				playersTurn = !playersTurn;
			}else if(pOneHasMove){
				//play the move
				cardPlayed = playerOne.playCard(cardPlayed);
				while(cribbageDeck.getValue(cardPlayed) + boardScore > 31)
					cardPlayed = playerOne.playCard(cardPlayed);
				playerOne.goodPlay(cardPlayed);
				boardScore += cribbageDeck.getValue(cardPlayed);
				//check for scores.
			}else if(pTwoHasMove){
				//play the move
				cardPlayed = playerTwo.playCard(cardPlayed);
				while(cribbageDeck.getValue(cardPlayed) + boardScore > 31)
					cardPlayed = playerTwo.playCard(cardPlayed);
				playerTwo.goodPlay(cardPlayed);
				boardScore += cribbageDeck.getValue(cardPlayed);
				//check for scores.
			}else{
				emptyBoard();
				nextBoardSpot = 0;
				boardScore = 0;
			}
			if(cardPlayed != -1){
				boardCards[nextBoardSpot++] = cardPlayed;
				System.out.println("boardScore = "+boardScore);
			}else
				System.out.println("***Cleared***");

		}
	}
	public boolean checkPlayable(int[] Hand){
		//get the board score
		for(int i = 0; i < Hand.length; i++){
			if(Hand[i] == -1)
				return false;
			if(boardScore + cribbageDeck.getValue(Hand[i]) <= 31)
				return true;
		}
		return false;
	}
	public void printASDF(int[] a){
		for(int i = 0; i < a.length-2; i++){
			System.out.println(cribbageDeck.getValue(a[i]));
		}
	}
	public void scoreHands(){
		printASDF(playerOne.getHand());
		int[] addCutCardPOne = Arrays.copyOf(playerOne.getHand(), 5);
		addCutCardPOne[4] = cutCard;
		int[] addCutCardPTwo = Arrays.copyOf(playerTwo.getHand(), 5);
		addCutCardPTwo[4] = cutCard;
		int[] addCutCardCrib = Arrays.copyOf(crib.getHand(), 5);
		addCutCardCrib[4] = cutCard;
		sort(addCutCardPOne);
		sort(addCutCardPTwo);
		sort(addCutCardCrib);
		int argh;
		if(playerOne.amIDealing()){
			playerTwo.setScore(fifteens(addCutCardPTwo));
			if(playerTwo.getScore() >= 121)
				return;
			playerOne.setScore(fifteens(addCutCardPOne));
			playerOne.setScore(fifteens(addCutCardCrib));
		}else{
			playerOne.setScore(fifteens(addCutCardPOne));
			if(playerOne.getScore() >= 121)
				return;
			playerTwo.setScore(fifteens(addCutCardPTwo));
			playerTwo.setScore(fifteens(addCutCardCrib));
		}
	}
	public int fifteens(int[] hand){
		//count the fifteens then return the next check
		int fifteenScore = 0;
		for(int oneCard = 0; oneCard < hand.length; oneCard++){
			for(int twoCard = oneCard+1; twoCard < hand.length; twoCard++){
				if(cribbageDeck.getValue(hand[oneCard])+cribbageDeck.getValue(hand[twoCard]) == 15)
					fifteenScore+=2;	
				for(int threeCard = twoCard+1; threeCard < hand.length; threeCard++){
					if(cribbageDeck.getValue(hand[oneCard])+cribbageDeck.getValue(hand[twoCard])+cribbageDeck.getValue(hand[threeCard]) == 15)
						fifteenScore+=2;	
					for(int fourCard = threeCard+1; fourCard < hand.length; fourCard++){
						if(cribbageDeck.getValue(hand[oneCard])+cribbageDeck.getValue(hand[twoCard])+cribbageDeck.getValue(hand[threeCard])+cribbageDeck.getValue(hand[fourCard]) == 15)
							fifteenScore+=2;
					}
				}
			}
		}
		if(cribbageDeck.getValue(hand[0])+cribbageDeck.getValue(hand[1])+cribbageDeck.getValue(hand[2])+cribbageDeck.getValue(hand[3])+cribbageDeck.getValue(hand[4]) == 15)
			fifteenScore+=2;
		return fifteenScore + runs(hand);
	}
	public int runs(int[] hand){
		int runScore = 0;
		int multiplier = 1;
		int currentMultiplierCard = 0;
		for (int currentCard = 0; currentCard < hand.length-1; currentCard++){
			int thisCard = cribbageDeck.getNumValue(hand[currentCard]);
			int nextCard = cribbageDeck.getNumValue(hand[currentCard+1]);
			if(thisCard == nextCard-1){
				runScore++;
			}else if(thisCard == nextCard){
				if(currentMultiplierCard == thisCard || currentMultiplierCard == 0){
					multiplier++;
					currentMultiplierCard = thisCard;
				}else
					multiplier *=2;
			}else if(runScore < 2){
				runScore = 0;
				multiplier = 1;
			}else
				break;
		}
		if(runScore > 1){
			runScore = (runScore+1)*multiplier;
			return runScore + flush(hand);
		}
		return 0+ flush(hand);
	}
	public int flush(int[] hand){
		int flushScore = 0;
		for (int i = 0; i < hand.length-1; i++){
			if(cribbageDeck.getSuit(hand[i]).compareTo(cribbageDeck.getSuit(hand[i+1])) != 0)
				return pairs(hand);
			else
				flushScore++;
		}
		return flushScore + pairs(hand);
	}
	public int pairs(int[] hand){
		int pairScore = 0;
		for (int i = 0; i < hand.length-1; i++){
			for(int j = i+1; j < hand.length; j++){
				if(cribbageDeck.getFace(hand[i]).compareTo(cribbageDeck.getFace(hand[j])) == 0)
					pairScore +=2;
			}
		}
		return pairScore + knobs(hand);
	}
	public int knobs(int[] hand){
		int knobScore = 0;
		for (int i = 0; i < hand.length-1; i++)
			if(cribbageDeck.getFace(hand[i]).compareTo("J") == 0 && cribbageDeck.getSuit(hand[i]).compareTo(cribbageDeck.getSuit(cutCard)) == 0){
				knobScore = 1;
				break;
			}
		return knobScore;
	}
	public void sort(int[] hand){
		for(int i = 0; i < hand.length; i++){
			for(int j = 0; j < hand.length-1; j++){
				if(cribbageDeck.getNumValue(hand[j]) > cribbageDeck.getNumValue(hand[j+1])){
					int temp = hand[j];
					hand[j] = hand[j+1];
					hand[j+1] = temp;
				}
			}
		}
	}
}
