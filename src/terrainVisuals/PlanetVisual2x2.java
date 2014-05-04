package terrainVisuals;

import resources.ImageLibrary;
import terrains.Terrain;
import view.Sprite;

public class PlanetVisual2x2 extends TerrainVisual{

	private Sprite sprite;
	
	public PlanetVisual2x2(Terrain terrain) {
		super(terrain);
		sprite = new Sprite(terrain.getLocation().x, terrain.getLocation().y);
		sprite.setImage(ImageLibrary.getInstance().getImage("asteroids.png"));
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
	
}
