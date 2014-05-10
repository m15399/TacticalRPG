package terrainVisuals;

import terrains.Terrain;
import view.Sprite;

public class SpaceWreckageVisual extends TerrainVisual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1401194756559638650L;
	private Sprite sprite;
	
	public SpaceWreckageVisual(Terrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage("spacewreckage.png");
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
