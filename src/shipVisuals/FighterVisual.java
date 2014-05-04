package shipVisuals;

import view.GunSprite;
import view.JetSprite;
import view.Sprite;
import view.GunSprite.GunSize;
import view.JetSprite.JetSize;
import model.GameObject;
import model.Ship;

/*
 * Controls visuals for the Scout
 */
public class FighterVisual extends ShipVisual {

	private static final int FIRE_RATE = 2;
	
	private Sprite sprite;
	private JetSprite[] jets;
	private GunSprite gun;
	
	private RapidFirer rapidFirer;
	
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

		gun = new GunSprite(13, 7, this, GunSize.H8);
		addChild(gun);
		gun.setVisible(false);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
		
		rapidFirer = new RapidFirer();
		addChild(rapidFirer);
	}

	public void playMoveAnimation() {
		for(JetSprite j : jets){
			j.setVisible(true);
		}
	}
	
	public void playAttackAnimation(){
		gun.setVisible(true);
		
		rapidFirer.start();
	}
	
	public void playIdleAnimation() {
		for(JetSprite j : jets){
			j.setVisible(false);
		}	
		gun.setVisible(false);
		
		rapidFirer.stop();
	}

	
	private class RapidFirer extends GameObject {
				
		int count;
		boolean going;
		
		public RapidFirer(){
			count = 0;
			going = false;
		}
		
		public void start(){
			going = true;
			count = 1;
			gun.setVisible(true);
		}
		
		public void stop(){
			going = false;
			gun.setVisible(false);
		}
		
		public void update(){
			if(going){
				count++;
				if(count % FIRE_RATE == 0){
					gun.setVisible(!gun.getVisible());
				}
			}
			
		}
		
	}
	
	
}
