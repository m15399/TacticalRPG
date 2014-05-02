package specific_ships_items;

import actions.TimerAction;
import utils.Observer;
import model.Castable;
import model.Item;
import model.Ship;

public class ScrapMetal extends Item {

	private double hullBoost;

	public ScrapMetal() {
		super("Scrap Metal", "Heals the ship's hull for 100 points",
				Castable.TargetType.ALLY, 2);
		hullBoost = 100;
	}

	public void useOnShip(Ship ship, Observer notifyWhenDone) {
		ship.updateHull(hullBoost);

		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}

}
