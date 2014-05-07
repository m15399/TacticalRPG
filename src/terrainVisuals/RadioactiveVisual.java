package terrainVisuals;

import resources.ImageLibrary;
import terrains.Terrain;
import view.Sprite;

public class RadioactiveVisual extends TerrainVisual{

	private Sprite sprite;
	
	public RadioactiveVisual(Terrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage(ImageLibrary.getInstance().getImage("radioactive.png"));
		sprite.getPosition().setLocation(0, 64);
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}

}
