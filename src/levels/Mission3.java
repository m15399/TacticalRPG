package levels;

import songPlayer.SongPlayer;
import specific_ships_items.Mothership;
import level_intros.Credits;
import model.Game.ObjectWaitingForSongToEnd;
import model.GameObject;
import model.Level;
import model.Ship;

public class Mission3 extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 961227008648367354L;
	
	public Mission3(){
		super("maps/mission3");
		
		SongPlayer.stopFile();
		ObjectWaitingForSongToEnd waiter = new ObjectWaitingForSongToEnd();
		SongPlayer.playFile(waiter, System.getProperty("user.dir")
			      + System.getProperty("file.separator") + "songfiles"
			      + System.getProperty("file.separator") + "space_clips.mp3");
		
		startTurn(0);

	}
	
	
	public void onUnitDestroyed(Ship s){
		if(s instanceof Mothership){
			onTeamWin(0, "You destroyed the Mothership!");

		}
	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Credits();
		else
			return new Mission3();
	}
	
}
