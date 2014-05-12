package shipVisuals;

import view.GunSprite;
import view.GunSprite.GunType;
import view.JetSprite;
import view.GunSprite.GunSize;
import view.JetSprite.JetSize;
import model.Ship;

public class BomberVisual extends ShipVisual {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5482847056354702080L;
	
	private JetSprite jet;
	private GunSprite gun1, gun2;
	
	public BomberVisual(Ship ship) {
		super(ship);
		
		jet = new JetSprite(-37, -3, this, JetSize.H16);
		addChildBelowSprite(jet);
		jet.setVisible(false);
		
		int x = 37;
		gun1 = new GunSprite(x-1, -1, this, GunSize.H16, GunType.CANNON);
		gun2 = new GunSprite(x+1, 4, this, GunSize.H16, GunType.CANNON);

		gun1.setVisible(false);
		gun2.setVisible(false);
		
		addChild(gun1);
		addChild(gun2);
		
//		getSprite().getPosition().setScale(.3, .3);
	}

	public void playMoveAnimation() {
		jet.setVisible(true);
	}

	public void playAttackAnimation(int time) {
		int t = 8;
		gun1.playGunAnimation(t, time);
		gun2.playGunAnimation(t, time);
	}

	public void playIdleAnimation() {
		jet.setVisible(false);
		gun1.setVisible(false);
		gun2.setVisible(false);
	}
	
}
