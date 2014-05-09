package levels;

import model.Game;
import model.GameObject;
import model.Level;
import model.TitleMenu;

public class Mission3 extends Level {

	public Mission3(Game game){
		super(game, "maps/mission3");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new TitleMenu(getGame());
		else
			return new Mission3(getGame());
	}
	
}
