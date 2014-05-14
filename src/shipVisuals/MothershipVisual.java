package shipVisuals;

import java.awt.Graphics;

import view.GunSprite;
import view.GunSprite.GunSize;
import view.GunSprite.GunType;
import model.Map;
import model.Ship;

public class MothershipVisual extends ShipVisual {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5562077940544244427L;

	
	private GunSprite guns[];
	
	public MothershipVisual(Ship ship) {
		super(ship);

		getSprite().getPosition().moveBy(0, -Map.TILESIZE);
		
		guns = new GunSprite[4];
		
		int ox1 = 20;
		int ox2 = ox1 - 5;
		int oy1 = -22;
		int oy2 = oy1-4;
		
		int or = 2;
		
		guns[0] = new GunSprite(-ox1, oy1, this, GunSize.H8, GunType.CANNON);
		guns[1] = new GunSprite(-ox2, oy2, this, GunSize.H8, GunType.CANNON);
		guns[2] = new GunSprite(ox1 + or, oy1, this, GunSize.H8, GunType.CANNON);
		guns[3] = new GunSprite(ox2 + or, oy2, this, GunSize.H8, GunType.CANNON);
		
		for(GunSprite g : guns){
			addChild(g);
			g.getPosition().setRotation(Math.PI/2);
			
		}
	}

	public void playAttackAnimation(int time) {
		int t1 = 6;
		int t2 = 10;
		
		guns[0].playGunAnimation(t1, time);
		guns[1].playGunAnimation(t2, time);
		guns[2].playGunAnimation(t1, time);
		guns[3].playGunAnimation(t2, time);

	}
	
	public void draw(Graphics g){
		super.draw(g);
		
		if(getPosition().getMirrored())
			getPosition().mirror();
	}
	
}
