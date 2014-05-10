package levels;

import view.GraphicsTest;
import model.GameObject;

public class GraphicsTestLevel extends GameObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7714880008423457448L;

	public GraphicsTestLevel(){
		addChild(new GraphicsTest());
	}
	
}
