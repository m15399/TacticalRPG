package specific_ships_items;

import java.util.List;

import utils.Observer;
import actions.TimerAction;
import model.Ability;
import model.Castable;
import model.Ship;

public class MineAbility extends Ability {

	private static int DAMAGE = 50;
	private static int RANGE = 3;

	public MineAbility() {
		super("Explode", "Mine explodes, dealing\n" + DAMAGE
				+ " damage to all enemies \nwithin " + RANGE + " tiles.",
				Castable.TargetType.NONE, 0, 1);

	}
	
	public void useWithoutTarget(Observer notifyWhenDone){
		int team = 0;
		if(getOwner().getTeam() == 0)
			team = 1;
		
		List<Ship> enemies = getOwner().getLevel().getShipsWithinCircularArea(getOwner(), RANGE, team);
		for(Ship s : enemies){
			s.updateHull(-DAMAGE);
			s.getVisual().updateDisplayHealth();
		}
		getOwner().setHull(0);

		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}

}
