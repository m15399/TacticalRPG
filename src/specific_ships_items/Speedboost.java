package specific_ships_items;

import actions.TimerAction;
import utils.Observer;
import model.Castable;
import model.Item;
import model.Ship;

public class Speedboost extends Item {

	private int speedBoost;

	public Speedboost() {
		super("Speed Boost", "Increases the ship's move range for one turn.",
				Castable.TargetType.ALLY, 2);
		speedBoost = 4;
	}

	public void useOnShip(Ship ship, Observer notifyWhenDone) {
		ship.setMovesLeft(ship.getMovesLeft() + speedBoost);

		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}
	
	public String getFilename(){
		return "speedboost.png";
	}

}
