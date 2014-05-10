package actions;

import utils.Observer;
import view.Camera;

public class WaitForCameraAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539429463719245030L;
	Camera camera;
	
	public WaitForCameraAction(Camera c, Observer o){
		super(o);
		camera = c;
	}
	
	public void update(){
		if(started){
			if(!camera.getIsMoving()){
				finish();
			}
		}
	}
	
}
