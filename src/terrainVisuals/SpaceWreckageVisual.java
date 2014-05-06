package terrainVisuals;

import resources.ImageLibrary;
import terrains.Terrain;
import view.Sprite;

public class SpaceWreckageVisual extends TerrainVisual{

	private Sprite sprite;
	
	public SpaceWreckageVisual(Terrain terrain) {
		super(terrain);
		sprite = new Sprite();
		//sprite.setImage(ImageLibrary.getInstance().getImage("somepicture.png"));
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
//TODO Implement
}
