package terrainVisuals;

import resources.ImageLibrary;
import terrains.AsteroidTerrain;
import view.Sprite;

public class AsteroidVisual extends TerrainVisual{

	private Sprite sprite;
	
	public AsteroidVisual(AsteroidTerrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage(ImageLibrary.getInstance().getRandomImageFromSheet("asteroids.png"));
		addChild(sprite);
		
		// rotate to a random degree
//		sprite.getPosition().setRotation(Math.random()*10);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
