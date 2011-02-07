import java.util.*;
import java.math.*;
public class Game{
	public static void main (String args[]){
	int p1Win, p2Win;
	p1Win = p2Win = 0;
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
			if(inProgress == 1)
				p1Win++;
			else
				p2Win++;
			if(p2Win == 100 && p1Win < 100){
				System.out.println("Player 1: "+p1Win);
				System.out.println("Player 2: "+p2Win);
				p1Win = p2Win = 0;
			}
		}
	}
}
