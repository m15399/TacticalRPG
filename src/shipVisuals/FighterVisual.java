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
public class FighterVisual extends ShipVisual {

	
	private Sprite sprite;
	private JetSprite[] jets;
	private GunSprite gun;
	
	
	public FighterVisual(Ship ship) {
		super(ship);
		
		jets = new JetSprite[3];
		
		int ox = -22;
		
		JetSprite jet = new JetSprite(ox, 1, this, JetSize.H8);
		jet.setVisible(false);
		addChild(jet);
		jets[0] = jet;
		
		jet = new JetSprite(ox-1, -3, this, JetSize.H8);
		jet.setVisible(false);
		addChild(jet);
		jets[1] = jet;
		
		jet = new JetSprite(ox, 0, this, JetSize.H8);
		jet.setVisible(false);
		addChild(jet);
		jets[2] = jet;
		
				
		
		sprite = new Sprite("fighter.png");
		addChild(sprite);

		gun = new GunSprite(13, 7, this, GunSize.H8, GunType.MACHINE_GUN);
		addChild(gun);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
		
		
	}

	public void playMoveAnimation() {
		for(JetSprite j : jets){
			j.setVisible(true);
		}
	}
	
	public void playAttackAnimation(int time){
		gun.playGunAnimation(0, time);
	}
	
	public void playIdleAnimation() {
		for(JetSprite j : jets){
			j.setVisible(false);
		}	
		
	}

	
}
