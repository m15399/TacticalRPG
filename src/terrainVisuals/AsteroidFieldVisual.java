package terrainVisuals;

import resources.ImageLibrary;
import terrains.Terrain;
import view.Sprite;

public class AsteroidFieldVisual extends TerrainVisual{

	private Sprite sprite;
	
	public AsteroidFieldVisual(Terrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage(ImageLibrary.getInstance().getRandomImageFromSheet("asteroidfield.png"));
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
