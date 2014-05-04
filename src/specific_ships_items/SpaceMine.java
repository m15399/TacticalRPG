package specific_ships_items;

import actions.TimerAction;
import utils.Observer;
import model.Castable;
import model.Item;
import model.Tile;

public class SpaceMine extends Item {

	public SpaceMine() {
		super(
				"Space Mine",
				"The ship drops a space mine in a specific location that will be detonated by any enemy ship that passes through.",
				Castable.TargetType.TILE, 1);
	}

	public void useOnTile(Tile tile, Observer notifyWhenDone) {
		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}

}
