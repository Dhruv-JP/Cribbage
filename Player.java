public class Player{
	private int playerNum, score, cardPlayed;
	private int[] hand;
	private boolean dealer;

	Player(int playNum, int startScore, int cardsInHand){
		dealer = false;
		playerNum = playNum;
		score = startScore;
		hand = new int[cardsInHand];
	}
	public void setCards(int[] cards){
		hand = cards;
	}
	public void showHand(Deck deck){
		System.out.println("\nshowing hand for: "+playerNum);
		for(int i = 0; i < hand.length; i++){
			if(hand[i] != -1)
				System.out.println("Card: "+deck.getSuit(hand[i])+" "+deck.getFace(hand[i]));
		}
	}
	public int getPlayerNum(){
		return playerNum;
	}
	public void setDealer(){
		dealer = !dealer;
	}
	public boolean amIDealing(){
		return dealer;
	}
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score += score;
	}
	public int[] sendToCrib(){
		int[] cribCards = {hand[0], hand[1]};
		hand[0] = -1;
		hand[1] = -1;
		sortCards();
		return cribCards;
	}
	public void sortCards(){
		for(int j = 0; j < hand.length; j++){
			for(int i = 0; i < hand.length-1; i++){
				if(hand[i] == -1){
					int temp = hand[i];
					hand[i] = hand[i+1];
					hand[i+1] = temp;
				}
			}
		if(hand[j] != -1)
			break;
		}
	}
	public int playCard(int lastAttempt){
		if(lastAttempt == -1)
			return hand[0];
		//search hand for card send card after
		for(int i = 0; i < hand.length; i++){
			if(hand[i] == lastAttempt)
				return hand[i++];
		}
		return -1;
	}
	public void goodPlay(int cardPlayed){
		for(int i = 0; i < hand.length; i++){
			if(hand[i] == cardPlayed)
				hand[i] = -1;
		}
		sortCards();
	}
	public int[] getHand(){
		int[] trueHand = new int[4];
		trueHand[0] = -1;
		int count = 0;
		for(int i = 0; i < hand.length; i++){
			if(hand[i] != -1)
				trueHand[count++] = hand[i];
		}
		return trueHand;
	}
}
