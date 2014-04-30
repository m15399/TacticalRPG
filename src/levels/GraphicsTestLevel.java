package levels;

import view.GraphicsTest;
import model.GameObject;

public class GraphicsTestLevel extends GameObject{

	public GraphicsTestLevel(){
		addChild(new GraphicsTest());
	}
	
}
