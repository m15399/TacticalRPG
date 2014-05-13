package specific_ships_items;

import java.util.List;
import java.util.Random;

import utils.Observer;
import actions.TimerAction;
import model.Ability;
import model.Castable;
import model.Ship;
import model.Tile;

public class SpawnEnemyShipAbility extends Ability {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3874097256449753393L;

	public SpawnEnemyShipAbility() {
		super("Spawn", "How are you even reading this?", Castable.TargetType.NONE, 1, 2);
		
	}
	
	public void useWithoutTarget(Observer notifyWhenDone){
		List<Tile> tiles = getOwner().getLevel().getEmptyTilesWithinCircularArea(getOwner(), 1.5);
		Random random = new Random();
		
		if(tiles.size() > 0){
			Tile t = tiles.get(random.nextInt(tiles.size()));
			
			Ship s = null;
			
			int rand = random.nextInt(3);
			switch(rand){
			case 0:
				s = new Scout(t.getLocation());
				break;
			case 1:
				s = new Fighter(t.getLocation());
				break;
			case 2:
				s = new Bomber(t.getLocation());
				break;
			}
			
			
			getOwner().getLevel().addEnemyShipToMap(s);
		}
		
		
		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}
	
}
