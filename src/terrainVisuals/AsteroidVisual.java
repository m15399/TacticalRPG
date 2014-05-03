package terrainVisuals;

import resources.ImageLibrary;
import terrains.AsteroidTerrain;
import view.Sprite;

public class AsteroidVisual extends TerrainVisual{

	private Sprite sprite;
	
	public AsteroidVisual(AsteroidTerrain terrain) {
		super(terrain);
		sprite = new Sprite(terrain.getLocation().x, terrain.getLocation().y);
		sprite.setImage(ImageLibrary.getInstance().getRandomImageFromSheet("asteroids.png"));
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
