package shipVisuals;

import view.GunSprite;
import view.JetSprite;
import view.GunSprite.GunSize;
import view.GunSprite.GunType;
import view.JetSprite.JetSize;
import model.Ship;

public class BattleCruiserVisual extends ShipVisual {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3368080337604897682L;
	
	private JetSprite jet, jet2;
	private GunSprite gun1, gun2;
	
	public BattleCruiserVisual(Ship ship) {
		super(ship);

		jet = new JetSprite(-38, 6, this, JetSize.H16);
		jet.setVisible(false);
		addChildBelowSprite(jet);
		
		jet2 = new JetSprite(-35, -4, this, JetSize.H16);
		jet2.setVisible(false);
		addChildBelowSprite(jet2);
		double s = 2;
		jet2.getPosition().setScale(s,s);
		
		gun1 = new GunSprite(16,-3,this,GunSize.H8, GunType.CANNON);
		addChild(gun1);
		
		gun2 = new GunSprite(28,9,this,GunSize.H8, GunType.CANNON);
		addChild(gun2);
		
		

	}

	public void playMoveAnimation() {
		jet.setVisible(true);
		jet2.setVisible(true);
	}

	public void playAttackAnimation(int time) {
		gun1.playGunAnimation(6, time);
		gun2.playGunAnimation(12, time);

	}

	public void playIdleAnimation() {
		jet.setVisible(false);
		jet2.setVisible(false);
	}
	
}
