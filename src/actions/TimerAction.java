package actions;

import utils.Observer;

/*
 * Timer that counts down in frames
 */
public class TimerAction extends Action {

	private int timeLeft;

	public TimerAction(int time, Observer observer){
		super(observer);
		
		timeLeft = time;
		
	}
	
	public void update(){
		if(!started)
			return;
		
		timeLeft--;
		if(timeLeft <= 0){
			finish();
		}
	}
	
	public int getTimeLeft(){
		return timeLeft;
	}
	
}
