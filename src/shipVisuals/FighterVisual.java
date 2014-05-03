package shipVisuals;

import view.JetSprite;
import view.Sprite;
import view.JetSprite.JetSize;
import model.Ship;

/*
 * Controls visuals for the Scout
 */
public class FighterVisual extends ShipVisual {

	private Sprite sprite;
	private JetSprite[] jets;

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

		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
		

	}

	public void playMoveAnimation() {
		for(JetSprite j : jets){
			j.setVisible(true);
		}
	}

	public void playIdleAnimation() {
		for(JetSprite j : jets){
			j.setVisible(false);
		}	}

	
	
}
