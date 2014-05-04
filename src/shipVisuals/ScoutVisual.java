package shipVisuals;

import view.GunSprite;
import view.JetSprite;
import view.Sprite;
import view.GunSprite.GunSize;
import view.GunSprite.GunType;
import view.JetSprite.JetSize;
import model.Ship;

/*
 * Controls visuals for the Scout
 */
public class ScoutVisual extends ShipVisual {

	private Sprite sprite;
	private JetSprite jet;
	private GunSprite gun;
	

	public ScoutVisual(Ship ship) {
		super(ship);

		jet = new JetSprite(-38, -3, this, JetSize.H16);
		jet.setVisible(false);
		addChild(jet);

		sprite = new Sprite("scout.png");
		addChild(sprite);

		gun = new GunSprite(24, 8, this, GunSize.H16, GunType.CANNON);
		gun.getPosition().setScale(.8, .8);
		addChild(gun);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());

	}

	public void playMoveAnimation() {
		jet.setVisible(true);
//		System.out.println("moving");
	}
	
	public void playAttackAnimation(int time){
		gun.playGunAnimation(6, time);
	}

	public void playIdleAnimation() {
		jet.setVisible(false);
		gun.setVisible(false);
//		System.out.println("idling");
	}

}
