package levels;

import model.GameObject;
import model.Level;
import model.TitleMenu;

public class Mission3 extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 961227008648367354L;

	public Mission3(){
		super("maps/mission3");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new TitleMenu();
		else
			return new Mission3();
	}
	
}
