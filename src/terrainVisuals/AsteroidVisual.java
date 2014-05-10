package terrainVisuals;

import terrains.AsteroidTerrain;
import view.AnimatedSprite;

public class AsteroidVisual extends TerrainVisual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3129575860014974425L;
	private AnimatedSprite sprite;
	
	public AsteroidVisual(AsteroidTerrain terrain) {
		super(terrain);
		sprite = new AnimatedSprite("asteroids.png");
		sprite.setFrameSize(64, 64);
		sprite.setNumberFrames(3);
		sprite.setRandomFrame();
//		sprite.setImage(ImageLibrary.getInstance().getRandomImageFromSheet("asteroids.png"));
		addChild(sprite);
		
		// rotate to a random degree
//		sprite.getPosition().setRotation(Math.random()*10);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
