package levels;

import model.Game;
import model.GameObject;
import model.Level;

public class Mission1 extends Level {

	public Mission1(Game game){
		super(game, "maps/mission1");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Mission2(getGame());
		else
			return new Mission1(getGame());
	}
	
}
