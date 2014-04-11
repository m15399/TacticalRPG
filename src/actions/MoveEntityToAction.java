package actions;

import model.Entity;
import model.Observer;
import model.Position;

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
		
		if(timeLeft == 0){
			entity.getPosition().setLocation(endX, endY);
			finish();
			
		} else {
			
			double currX = entity.getPosition().getX();
			double currY = entity.getPosition().getY();
			
			double dx = (endX - currX) / timeLeft;
			double dy = (endY - currY) / timeLeft;
						
			entity.getPosition().moveBy(dx, dy);
		}
		
	}
	
}
