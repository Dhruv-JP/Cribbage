import java.util.*;
import java.math.*;
public class Game{
	public static void main (String args[]){
		while(true){
			Cribbage game = new Cribbage();
			int inProgress = 0;
			while(inProgress == 0){
				inProgress = game.play();
		/*	try{
				Thread.currentThread().sleep(3000);
			}catch(InterruptedException e){
			}*/
			}
		}
	}
}
