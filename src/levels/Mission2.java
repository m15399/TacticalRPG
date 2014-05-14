package levels;

import java.awt.Point;

import songPlayer.EndOfSongEvent;
import songPlayer.EndOfSongListener;
import songPlayer.SongPlayer;
import specific_ships_items.Mothership;
import level_intros.Mission3Intro;
import model.GameObject;
import model.Level;

public class Mission2 extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = 122195458674410630L;

	public Mission2(){
		super("maps/mission2");
		
		SongPlayer.stopFile();
		ObjectWaitingForSongToEnd2 waiter = new ObjectWaitingForSongToEnd2();
		SongPlayer.playFile(waiter, System.getProperty("user.dir")
			      + System.getProperty("file.separator") + "songfiles"
			      + System.getProperty("file.separator") + "mission2.mp3");
		
		addShipToMap(new Mothership(new Point(16,7)));
		
		startTurn(0);

	}
	
	private static class ObjectWaitingForSongToEnd2 implements EndOfSongListener {
		public void songFinishedPlaying(EndOfSongEvent eosEvent) {
			System.out.print("Finished " + eosEvent.fileName());
			ObjectWaitingForSongToEnd2 waiter = new ObjectWaitingForSongToEnd2();
			SongPlayer.playFile(waiter, System.getProperty("user.dir")
				      + System.getProperty("file.separator") + "songfiles"
				      + System.getProperty("file.separator") + "mission2.mp3");
	    }
	  }
	
	public void update(){
		super.update();
		
		if(!getIsOver() && getTurnNumber() == 10){
			onTeamWin(0, "You defended against the alien attack.");
		}
			
	}
	
	public GameObject getNextRoot(){
		if(getWinner() == 0)
			return new Mission3Intro();
		else
			return new Mission2();
	}
	
}
