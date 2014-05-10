package levels;

import model.GameObject;
import model.Level;

public class TestLevelFromFile extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 164431663622928217L;

	public TestLevelFromFile(){
		super("maps/testTileMap");
		
		startTurn(0);

	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new TestLevel();
		else
			return new TestLevelFromFile();
	}
	
}
