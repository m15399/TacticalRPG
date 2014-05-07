package levels;

import model.Game;
import model.Level;
import model.TitleMenu;

public class TestLevelFromFile extends Level {

	public TestLevelFromFile(Game game){
		super(game, "maps/testTileMap");
		
		startTurn(0);

	}
	
	public void update(){
		super.update();
		
		if(!getIsOver()){
			// check objective
			if(getShips(0).size() == 0){
				System.out.println("\n\n\nYou lose!!");
				exitLevel(new TitleMenu(getGame()));
			}
			if(getShips(1).size() == 0){
				System.out.println("\n\n\nYou win!!");
				exitLevel(new TitleMenu(getGame()));
			}
		}
	}
	
}
