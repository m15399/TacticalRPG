package specific_ships_items;

import java.awt.Point;

import model.Ability;
import model.Castable;
import model.Level;
import model.Tile;
import utils.Observer;
import actions.TimerAction;

public class PlaceFighterAbility extends Ability {
	
	public PlaceFighterAbility() {
		super("Deploy", "Spawns a Fighter at\n the target location.", Castable.TargetType.TILE, 1, 8);
		
	}
	
	public void useOnTile(Tile tile, Observer notifyWhenDone){
		Level level = getOwner().getLevel();
		if(getOwner().getTeam() == 0){
			level.addShipToMap(new Fighter(new Point(tile.getLocation())));
		} else {
			level.addEnemyShipToMap(new Fighter(new Point(tile.getLocation())));
		}

		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}
}
