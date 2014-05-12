package levels;

import level_intros.Mission2Intro;
import model.GameObject;
import model.Level;
import model.VictoryArea;



public class Mission1 extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8708225401421898966L;

	private VictoryArea victoryArea;

	
	public Mission1(){
		super("maps/mission1");
		
		//Set bounds for Victory Area
		victoryArea = new VictoryArea(7, 12, getMap().getWidth(), getMap().getHeight());
		getMap().addChild(victoryArea); // put it under the terrain
		
		startTurn(0);

	}
	
	public void onMoveFinished(){
		if(victoryArea != null && victoryArea.checkIfAllShipsAreInVictoryArea(getShips(0))){
			onTeamWin(0, "Your ships reached the objective.");
		}
	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Mission2Intro();
		else
			return new Mission1();
	}
	
}
