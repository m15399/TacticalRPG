package shipVisuals;

import view.Sprite;
import model.Ship;

/*
 * Controls visuals for the Scout
 */
public class ScoutVisual extends ShipVisual {

	Sprite sprite;
	
	public ScoutVisual(Ship ship) {
		super(ship);
		
		sprite = new Sprite("test.png");
		addChild(sprite);

		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
		
		sprite.getPosition().setScale(.6, .6);
	}



	
	
}
