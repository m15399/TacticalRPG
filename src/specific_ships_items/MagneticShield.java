package specific_ships_items;

import actions.TimerAction;
import utils.Observer;
import model.Castable;
import model.Item;
import model.Popup;

public class MagneticShield extends Item implements Popup{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2335142587348028030L;
	private double shieldingBoost ;
	
	public MagneticShield() {
		super("Magnetizer", "Gives the ship a \nshield boost of 15%", Castable.TargetType.NONE, 0);
		shieldingBoost = 1.15;
	}

	public void useWithoutTarget(Observer notifyWhenDone) {
		getOwner().updateShielding(getOwner().getShielding()*shieldingBoost);
		
		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}

	public String getFilename(){
		return "MagneticShield.png";
	}

}
