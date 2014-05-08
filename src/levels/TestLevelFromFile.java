package levels;

import model.Game;
import model.GameObject;
import model.Level;
import model.TitleMenu;

public class TestLevelFromFile extends Level {

	public TestLevelFromFile(Game game){
		super(game, "maps/testTileMap");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new TestLevel(getGame());
		else
			return new TestLevelFromFile(getGame());
	}
	
}
