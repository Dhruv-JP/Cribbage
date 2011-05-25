public class Player{
	private int playerNum, score, cardPlayed;
	private int[] hand, playHand;
	private boolean dealer;

	Player(int playNum, int startScore, int cardsInHand){
		dealer = false;
		playerNum = playNum;
		score = startScore;
		hand = new int[cardsInHand];
		playHand = new int[cardsInHand];
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
		sortCards(hand);
		copyCards();
		return cribCards;
	}
	public void copyCards(){
		for(int i = 0; i < hand.length; i++)
			playHand[i] = hand[i];
	}
	public void sortCards(int[] shand){
		for(int j = 0; j < shand.length; j++){
			for(int i = 0; i < shand.length-1; i++){
				if(shand[i] == -1){
					int temp = shand[i];
					shand[i] = shand[i+1];
					shand[i+1] = temp;
				}
			}
		if(shand[j] != -1)
			break;
		}
	}
	public int playCard(int lastAttempt){
		if(lastAttempt == -1)
			return playHand[0];
		//search hand for card send card after
		for(int i = 0; i < playHand.length; i++){
			if(playHand[i] == lastAttempt)
				return playHand[i++];
		}
		return -1;
	}
	public void goodPlay(int cardPlayed){
		for(int i = 0; i < playHand.length; i++){
			if(playHand[i] == cardPlayed)
				playHand[i] = -1;
		}
		sortCards(playHand);
	}
	public int[] getHand(){
		return hand;
	}
}
