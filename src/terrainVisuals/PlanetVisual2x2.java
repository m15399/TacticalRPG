package terrainVisuals;

import resources.ImageLibrary;
import terrains.Terrain;
import view.Sprite;

public class PlanetVisual2x2 extends TerrainVisual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1777455285874731277L;
	private Sprite sprite;
	
	public PlanetVisual2x2(Terrain terrain) {
		super(terrain);
		sprite = new Sprite();
		sprite.setImage(ImageLibrary.getInstance().getImage("planet2x2.png"));
		sprite.getPosition().setLocation(35, 33);
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
	
}
