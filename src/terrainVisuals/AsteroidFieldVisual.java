package terrainVisuals;

import terrains.AsteroidFieldTerrain;
import view.AnimatedSprite;

public class AsteroidFieldVisual extends TerrainVisual{

	private AnimatedSprite sprite;
	
	public AsteroidFieldVisual(AsteroidFieldTerrain terrain) {
		super(terrain);
		sprite = new AnimatedSprite("asteroidfield.png");
		sprite.setFrameSize(64, 64);
		sprite.setNumberFrames(1);
		sprite.setRandomFrame();
//		sprite.setImage(ImageLibrary.getInstance().getRandomImageFromSheet("asteroids.png"));
		addChild(sprite);
		
		// rotate to a random degree
//		sprite.getPosition().setRotation(Math.random()*10);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
