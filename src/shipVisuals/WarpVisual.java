package shipVisuals;

import model.Ship;
import view.Sprite;

public class WarpVisual extends ShipVisual{

	private Sprite sprite;
	
	public WarpVisual(Ship ship) {
		super(ship);
		
		if(ship.getTeam() == 0)
			sprite = new Sprite("wormholeBlue.png", ship.getLocation().x, ship.getLocation().y);
		else if(ship.getTeam() == 1)
			sprite = new Sprite("wormholeRed.png", ship.getLocation().x, ship.getLocation().y);
		else if(ship.getTeam() == 2)
			sprite = new Sprite("wormholeGreen.png", ship.getLocation().x, ship.getLocation().y);
		else
			System.out.println("Something went wrong loading a warp hole.");
		
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}

}
