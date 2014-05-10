package levels;

import model.GameObject;
import model.Level;
import model.TitleMenu;

public class MultiplayerLevel extends Level{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6771900907769457364L;
	private static final String[] MAPS = {"multiplayer_devin", "multiplayer_ethan", 
		"multiplayer_luis", "multiplayer_mark"};
	
	public MultiplayerLevel(int mapNumber){
		super("maps/" + MAPS[mapNumber]);
		
		setToMultiplayer();
		
		startTurn(0);
	}
	
	public GameObject getNextRoot(){
		return new TitleMenu();
	}
	
}
