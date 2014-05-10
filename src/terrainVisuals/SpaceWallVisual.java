package terrainVisuals;

import terrains.SpaceWallTerrain;
import view.Sprite;

public class SpaceWallVisual extends TerrainVisual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2240364667891486589L;
	private Sprite sprite;
	//b,h,l,r,t,v
	public SpaceWallVisual(SpaceWallTerrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage(terrain.getFileName());
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
