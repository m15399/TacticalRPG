package specific_ships_items;

import java.awt.Point;

import utils.Observer;
import actions.TimerAction;
import model.Ability;
import model.Castable;
import model.Level;
import model.Tile;

public class PlaceMineAbility extends Ability {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965263051491507798L;

	public PlaceMineAbility() {
		super("Mine", "Places a mine at\nthe target location.", Castable.TargetType.TILE, 2, 3);
		
	}
	
	public void useOnTile(Tile tile, Observer notifyWhenDone){
		Level level = getOwner().getLevel();
		if(getOwner().getTeam() == 0){
			level.addShipToMap(new MineShip(new Point(tile.getLocation())));
		} else {
			level.addEnemyShipToMap(new MineShip(new Point(tile.getLocation())));
		}

		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}
	
}
