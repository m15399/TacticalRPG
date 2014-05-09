package levels;

import model.Game;
import model.GameObject;
import model.Level;

public class Mission2 extends Level {

	public Mission2(Game game){
		super(game, "maps/mission2");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Mission3(getGame());
		else
			return new Mission2(getGame());
	}
	
}
