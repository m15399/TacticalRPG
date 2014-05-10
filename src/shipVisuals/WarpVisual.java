package shipVisuals;

import model.Ship;
import view.Sprite;

public class WarpVisual extends ShipVisual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8338933074734805742L;
	private Sprite sprite;
	private double rotateSpeed;
	
	public WarpVisual(Ship ship) {
		super(ship);
		
		rotateSpeed = .006;
		
		sprite = new Sprite(ship.getFileName());
		
		addChild(sprite);
		// rotate to a random degree
		sprite.getPosition().setRotation(Math.random() * 10);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
	
	public void update(){
		sprite.getPosition().rotateBy(rotateSpeed);
	}

}
