package view;

import utils.Observable;
import utils.Observer;
import actions.TimerAction;
import model.Entity;

public class HealBeam extends Entity {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9179881042058380473L;
	

	public HealBeam(double x, double y, double angle, Entity spatialParent){
		
		Sprite sprite = new Sprite("healbeam.png");
		addChild(sprite);
		
		sprite.getPosition().setParent(spatialParent.getPosition());
		
		sprite.getPosition().setLocation(x, y);		
		if(spatialParent.getPosition().getMirrored()){
			sprite.getPosition().setMirrored(false);
			angle += Math.PI;
		}
		
		sprite.getPosition().setRotation(angle);
		
		TimerAction timer = new TimerAction(30, new Observer(){
			public void notified(Observable sender){
				destroy();
			}
		});
		addChild(timer);
		timer.start();
		
	}
	
	
	
}
