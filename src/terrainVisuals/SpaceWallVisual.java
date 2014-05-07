package terrainVisuals;

import resources.ImageLibrary;
import terrains.SpaceWallTerrain;
import view.Sprite;

public class SpaceWallVisual extends TerrainVisual{

	private Sprite sprite;
	//b,h,l,r,t,v
	public SpaceWallVisual(SpaceWallTerrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage(ImageLibrary.getInstance().getImage(terrain.getFileName()));
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
}
