package actions;

import utils.Observer;
import view.Camera;

public class WaitForCameraAction extends Action {

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
