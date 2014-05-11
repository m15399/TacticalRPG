package terrainVisuals;

import terrains.Terrain;
import view.Sprite;

public class SpaceWreckageVisual1x3 extends TerrainVisual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1510256138918552080L;
	private Sprite sprite;
	
	public SpaceWreckageVisual1x3(Terrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage("spacewreckage1x3.png");
		sprite.getPosition().setLocation(64, 0);
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}

}
