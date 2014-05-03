package specific_ships_items;

import actions.TimerAction;
import utils.Observer;
import model.Ability;
import model.Castable;
import model.Ship;

public class RepairAbility extends Ability {

	private static final int HEAL_AMOUNT = 25;

	public RepairAbility() {
		super("Repair", "Repairs a ship's hull for " + HEAL_AMOUNT + " points",
				Castable.TargetType.ALLY, 2, 2);

	}

	public void useOnShip(Ship ship, Observer notifyWhenDone) {		
		ship.updateHull(HEAL_AMOUNT);
		ship.getVisual().updateDisplayHealth();


		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}

}
