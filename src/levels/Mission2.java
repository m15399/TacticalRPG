package levels;

import level_intros.Mission3Intro;
import model.GameObject;
import model.Level;

public class Mission2 extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 122195458674410630L;

	public Mission2(){
		super("maps/mission2");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Mission3Intro();
		else
			return new Mission2();
	}
	
}
