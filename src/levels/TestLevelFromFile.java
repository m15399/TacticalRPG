package levels;

import model.Level;

public class TestLevelFromFile extends Level {

	public TestLevelFromFile(){
		super("testTileMap");
		
		startTurn(0);

	}
	
	public void update(){
		super.update();
		
		// check objective
		if(getShips(0).size() == 0){
			System.out.println("\n\n\nYou lose!!");
			System.exit(0);
		}
		if(getShips(1).size() == 0){
			System.out.println("\n\n\nYou win!!");
			System.exit(0);
		}
	}
	
}
