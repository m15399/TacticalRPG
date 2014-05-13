package levels;

import java.awt.Point;

import specific_ships_items.Mothership;
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
		
		addShipToMap(new Mothership(new Point(15,3)));
		
		startTurn(0);

	}
	
	public void update(){
		super.update();
		
		if(!getIsOver() && getTurnNumber() == 10){
			onTeamWin(0, "You defended against the alien attack.");
		}
	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Mission3Intro();
		else
			return new Mission2();
	}
	
}
