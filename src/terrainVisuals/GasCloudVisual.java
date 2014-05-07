package terrainVisuals;

import resources.ImageLibrary;
import terrains.Terrain;
import view.Sprite;

public class GasCloudVisual extends TerrainVisual{

	private Sprite sprite;
	
	public GasCloudVisual(Terrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage(ImageLibrary.getInstance().getImage("gascloud.png"));
		sprite.getPosition().setLocation(64, 0);
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}

}
