package actions;

import utils.Observer;
import utils.Position;
import model.Entity;

/*
 * Moves an Entity to a position over a period of time
 */
public class MoveEntityToAction extends Action {

	Entity entity;
	
	double endX, endY;
	
	int timeLeft;
	
	public MoveEntityToAction(Entity entity, Position endPosition, int time, Observer o){
		super(o);
		
		this.entity = entity;
		
		endX = endPosition.getX();
		endY = endPosition.getY();
		
		timeLeft = time;
		
	}
	
	public void update(){
		if(!started)
			return;
				
		timeLeft--;
		
		if(timeLeft <= 0){
			// if time is over, set to the final location and call finish()
			entity.getPosition().setLocation(endX, endY);
			finish();
			
		} else {
			// move towards the end point by distance/timeLeft
			
			double currX = entity.getPosition().getX();
			double currY = entity.getPosition().getY();
			
			double dx = (endX - currX) / timeLeft;
			double dy = (endY - currY) / timeLeft;
			
			if(dx < 0){
				entity.getPosition().setMirrored(true);
			} else if (dx > 0){
				entity.getPosition().setMirrored(false);
			}
						
			entity.getPosition().moveBy(dx, dy);
		}
		
	}
	
}
