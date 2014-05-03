package view;

import model.Entity;

public class GunSprite extends AnimatedSprite {

	public enum GunSize {
		H8, H16;
	}
	
	public GunSprite(int x, int y, Entity spatialParent, GunSize size){
		
		getPosition().setLocation(x, y);
		
		// set up sprite
		String fn = "";
		int w = 0, h = 0;
		
		switch(size){
		case H16:
			fn = "guns_36x17.png";
			w = 36;
			h = 17;
			break;
		case H8:
			fn = "guns_19x7.png";
			w = 19;
			h = 7;
		}
		
		setImage(fn);
		setFrameSize(w, h);
		
		// set animation properties
		setDelay(3);
		int frames[] = {0, 1, 2, 3};
		setFrameIndicies(frames);
		
		// parent to the entity passed in
		if(spatialParent != null)
			setSpatialParent(spatialParent);
	}
	
}
