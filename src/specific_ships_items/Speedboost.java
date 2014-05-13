package specific_ships_items;

import actions.TimerAction;
import utils.Observer;
import model.Castable;
import model.Item;
import model.Popup;

public class Speedboost extends Item implements Popup{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8799209495814661932L;
	private int speedBoost;

	public Speedboost() {
		super("Afterburner", "Increases the ship's \nmove range for one turn.",
				Castable.TargetType.NONE, 0);
		speedBoost = 4;
	}

	public void useWithoutTarget(Observer notifyWhenDone) {
		getOwner().setMovesLeft(getOwner().getMovesLeft() + speedBoost);

		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}
	
	public String getFilename(){
		return "speedboost.png";
	}

}
