package levels;

import songPlayer.EndOfSongEvent;
import songPlayer.EndOfSongListener;
import songPlayer.SongPlayer;
import model.Game.ObjectWaitingForSongToEnd;
import specific_ships_items.Mothership;
import level_intros.Credits;
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
		ObjectWaitingForSongToEnd3 waiter = new ObjectWaitingForSongToEnd3();
		SongPlayer.playFile(waiter, System.getProperty("user.dir")
			      + System.getProperty("file.separator") + "songfiles"
			      + System.getProperty("file.separator") + "mission3.mp3");
		
		startTurn(0);

	}
	
	private static class ObjectWaitingForSongToEnd3 implements EndOfSongListener {
		public void songFinishedPlaying(EndOfSongEvent eosEvent) {
			System.out.print("Finished " + eosEvent.fileName());
			ObjectWaitingForSongToEnd3 waiter = new ObjectWaitingForSongToEnd3();
			SongPlayer.playFile(waiter, System.getProperty("user.dir")
				      + System.getProperty("file.separator") + "songfiles"
				      + System.getProperty("file.separator") + "mission3.mp3");
	    }
	  }
	
	public void onUnitDestroyed(Ship s){
		if(s instanceof Mothership){
			onTeamWin(0, "You destroyed the Mothership!");

		}
	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0) {
			SongPlayer.stopFile();
			ObjectWaitingForSongToEnd waiter2 = new ObjectWaitingForSongToEnd();
			SongPlayer.playFile(waiter2, System.getProperty("user.dir")
				      + System.getProperty("file.separator") + "songfiles"
				      + System.getProperty("file.separator") + "space_clips.mp3");			
			return new Credits();
		}
		else
			return new Mission3();
	}
	
}
