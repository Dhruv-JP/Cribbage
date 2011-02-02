public class Card{
	private int suit, value, face;
	public boolean inUse;
	Card(int s, int v, int f){
		suit = s;
		value = v;
		face = f;
		inUse = false;
	}
	public boolean getUse(){	
		return inUse;
	}
	public void setUse(){
		inUse = !inUse;
	}
	public String getSuit(){
		switch(suit){
			case 0:
				return "C";
			case 1:
				return "D";
			case 2:
				return "H";
			case 3:
				return "S";
			default:
				return "ERROR";
		}
	}
	public int getValue(){
		return value;
	}
	public int getNumValue(){
		return face;
	}
	public String getFace(){
		switch(face){
			case 1:
				return "A";
			case 11:
				return "J";
			case 12:
				return "Q";
			case 13:
				return "K";
			default:
				return ""+face;
			}
	}
}
