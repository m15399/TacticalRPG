package specific_ships_items;


import utils.Observer;
import actions.TimerAction;
import model.Ability;

public class SniperChargeAbility extends Ability {

	public SniperChargeAbility(){
		super("Charge", "Charges up its next \nattack for bonus \ndamage.", TargetType.NONE, 0, 3);
	}
	
	public void useWithoutTarget(Observer notifyWhenDone){
		Sniper sniper = (Sniper) getOwner();
		sniper.setCharged(true);

		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
	}
	
}
