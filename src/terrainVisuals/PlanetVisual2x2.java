package terrainVisuals;

import java.awt.image.BufferedImage;

import resources.ImageLibrary;
import terrains.Terrain;
import view.Sprite;

public class PlanetVisual2x2 extends TerrainVisual{

	private Sprite sprite;
	
	public PlanetVisual2x2(Terrain terrain) {
		super(terrain);
		sprite = new Sprite(terrain.getLocation().x, terrain.getLocation().y);
		BufferedImage image = ImageLibrary.getInstance().getImage("planet2x2.png");
		image = new BufferedImage(128, 128, image.getType());
		sprite.setImage(image);
		addChild(sprite);
		
		// parent the sprite to the location of the visual
		sprite.getPosition().setParent(getPosition());
	}
	
}
