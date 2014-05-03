package view;

import model.Entity;

/*
 * Animation of the jet/exhaust from ships. Has a few sizes to choose from to suit multiple ships
 */
public class JetSprite extends AnimatedSprite {
	
	public enum JetSize {
		H8, H16;
	}
	
	public JetSprite(int x, int y, Entity spatialParent, JetSize size){
		
		getPosition().setLocation(x, y);
		
		// set up sprite
		String fn = "";
		int w = 0, h = 0;
		
		switch(size){
		case H16:
			fn = "jet_37x16.png";
			w = 37;
			h = 16;
			break;
		case H8:
			fn = "jet_19x9.png";
			w = 19;
			h = 9;
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
