package shipVisuals;

import model.Ship;
import view.Sprite;

public class WarpVisual extends ShipVisual{

	private Sprite sprite;
	private double rotateSpeed;
	
	public WarpVisual(Ship ship) {
		super(ship);
		
		rotateSpeed = .006;
		
		if(ship.getTeam() == 0)
			sprite = new Sprite("wormholeBlue.png");
		else if(ship.getTeam() == 1)
			sprite = new Sprite("wormholeRed.png");
		else if(ship.getTeam() == 2)
			sprite = new Sprite("wormholeGreen.png");
		else
			System.out.println("Something went wrong loading a warp hole.");
		
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
