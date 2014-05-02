package specific_ships_items;

import actions.TimerAction;
import utils.Observer;
import model.Castable;
import model.Item;

public class MagneticShield extends Item{
	private double shieldingBoost ;
	
	public MagneticShield() {
		super("Magnetic Shield", "Gives the ship a shield boost of 15%", Castable.TargetType.ALLY, 0);
		shieldingBoost = 1.15;
	}

	public void useWithoutTarget(Observer notifyWhenDone) {
		getOwner().updateShielding(getOwner().getShielding()*shieldingBoost);
		
		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}


}
