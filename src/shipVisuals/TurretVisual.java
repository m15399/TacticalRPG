package shipVisuals;

import java.awt.Graphics;

import view.GunSprite;
import view.GunSprite.GunSize;
import view.GunSprite.GunType;
import model.Ship;

public class TurretVisual extends ShipVisual {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8138382397813646073L;
	private GunSprite gun1, gun2;

	
	public TurretVisual(Ship ship) {
		super(ship);
		
		int ox = 4;
		int oy = 30;
		
		gun1 = new GunSprite(-ox, oy, this, GunSize.H16, GunType.MACHINE_GUN);
		gun1.getPosition().setRotation(Math.PI/2);
		addChild(gun1);	
		
		gun2 = new GunSprite(ox, oy, this, GunSize.H16, GunType.MACHINE_GUN);
		gun2.getPosition().setRotation(Math.PI/2);
		addChild(gun2);	
	}
	
	public void playAttackAnimation(int time) {
		gun1.playGunAnimation(0, time);
		gun2.playGunAnimation(0, time);
	}

	public void draw(Graphics g){
		super.draw(g);
		
		if(getPosition().getMirrored())
			getPosition().mirror();
	}
	
}
