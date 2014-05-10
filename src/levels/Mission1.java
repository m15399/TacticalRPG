package levels;

import model.GameObject;
import model.Level;

public class Mission1 extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708225401421898966L;

	public Mission1(){
		super("maps/mission1");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Mission2();
		else
			return new Mission1();
	}
	
}
