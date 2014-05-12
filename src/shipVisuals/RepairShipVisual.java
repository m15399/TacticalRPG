package shipVisuals;

import model.Ship;
import view.GunSprite;
import view.JetSprite;
import view.GunSprite.GunSize;
import view.GunSprite.GunType;
import view.JetSprite.JetSize;

public class RepairShipVisual extends ShipVisual {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2722779174503159517L;
	private JetSprite jet;
	private GunSprite gun;
	
	
	public RepairShipVisual(Ship ship) {
		super(ship);

		jet = new JetSprite(-40, 1, this, JetSize.H16);
		jet.setVisible(false);
		addChildBelowSprite(jet);


		gun = new GunSprite(32, 8, this, GunSize.H16, GunType.CANNON);
		gun.getPosition().setScale(.8, .8);
		addChildBelowSprite(gun);

		

	}


	public void playMoveAnimation() {
		jet.setVisible(true);
	}

	public void playAttackAnimation(int time) {
		gun.playGunAnimation(6, time);
	}

	public void playIdleAnimation() {
		jet.setVisible(false);
		gun.setVisible(false);
	}
	
}
