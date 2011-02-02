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
		return cribCards;
	}
	public int playCard(){
		//choose card. make temp. set to -1 return temp
		for(int i = 2; i < 6; i++){
			if(hand[i] != -1){
				cardPlayed = i;
				return hand[i];
			}
		}
		return -1;
	}
	public void goodMove(){
		hand[cardPlayed] = -1;
	}
	public int[] getHand(){
		int[] trueHand = new int[4];
		int count = 0;
		for(int i = 0; i < hand.length; i++){
			if(hand[i] != -1)
				trueHand[count++] = hand[i];
		}
		return trueHand;
	}
}
