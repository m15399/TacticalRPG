package specific_ships_items;

import actions.TimerAction;
import shipVisuals.RepairShipVisual;
import utils.Observer;
import utils.Position;
import view.HealBeam;
import model.Ability;
import model.Castable;
import model.Ship;

public class RepairAbility extends Ability {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6139154918871946441L;
	private static final int HEAL_AMOUNT = 25;

	public RepairAbility() {
		super("Repair", "Repairs a ship's hull for \n" + HEAL_AMOUNT + " points",
				Castable.TargetType.ALLY, 2, 2);

	}
	
	private void startHealBeam(Position target){
		RepairShipVisual visual = (RepairShipVisual) getOwner().getVisual();
		double ox = -16;
		double oy  = 4;
		
		double x = visual.getPosition().getX() + (visual.getPosition().getMirrored() ? -ox : ox);
		double y = visual.getPosition().getY() + oy;
		double ex = target.getX();
		double ey = target.getY();
		
		double angle = utils.Utilities.angleBetween(x, y, ex, ey);
		
		
		visual.addChild(new HealBeam(ox, oy, angle, visual));
	}
	
	public void useOnShip(Ship ship, Observer notifyWhenDone) {		
		ship.updateHull(HEAL_AMOUNT);
		ship.getVisual().updateDisplayHealth();


		// placeholder animation
		TimerAction timer = new TimerAction(30, notifyWhenDone);
		addChild(timer);
		timer.start();
		
		startHealBeam(ship.getVisual().getPosition());
		
	}

}
