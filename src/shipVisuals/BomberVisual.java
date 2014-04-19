package shipVisuals;

import model.Ship;
import view.Sprite;

public class BomberVisual extends ShipVisual {
	
	Sprite sprite;
	
	public BomberVisual(Ship ship) {
		super(ship);
		
		sprite = new Sprite("test2.png");
		addChild(sprite);

		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
		
		sprite.getPosition().setScale(.3, .3);
	}

}
