package shipVisuals;

import model.Ship;

public class WarpVisual extends ShipVisual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8338933074734805742L;
	private double rotateSpeed;
	
	public WarpVisual(Ship ship) {
		super(ship);
		
		rotateSpeed = .006;
		
		// rotate to a random degree
		getSprite().getPosition().setRotation(Math.random() * 10);
		
	}
	
	public void update(){
		getSprite().getPosition().rotateBy(rotateSpeed);
	}

}
