package view;

import utils.Observable;
import utils.Observer;
import actions.TimerAction;
import model.Entity;

public class SniperBeam extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3323223563332717601L;

	public SniperBeam(double x, double y, double angle, double length,  Entity spatialParent){
		Sprite sprite = new Sprite("sniperbeam.png");
		addChild(sprite);
		
		sprite.getPosition().setParent(spatialParent.getPosition());
		
		sprite.getPosition().setLocation(x, y);		
		if(spatialParent.getPosition().getMirrored()){
			sprite.getPosition().setMirrored(false);
			angle += Math.PI;
		}
		
		sprite.getPosition().setRotation(angle);
		sprite.getPosition().setScale(length, 1);
		
		TimerAction timer = new TimerAction(200, new Observer(){
			public void notified(Observable sender){
				destroy();
			}
		});
		addChild(timer);
		timer.start();
	}
	
}
