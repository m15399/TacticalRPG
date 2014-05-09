package levels;

import model.Game;
import model.GameObject;
import model.Level;
import model.TitleMenu;

public class MultiplayerLevel extends Level{

	private static final String[] MAPS = {"multiplayer_devin", "multiplayer_ethan", 
		"multiplayer_luis", "multiplayer_mark"};
	
	public MultiplayerLevel(Game game, int mapNumber){
		super(game, "maps/" + MAPS[mapNumber]);
		
		setToMultiplayer();
		
		startTurn(0);
	}
	
	public GameObject getNextRoot(){
		return new TitleMenu(getGame());
	}
	
}
