package actions;

import model.Observer;

/*
 * Timer that counts down in frames
 */
public class TimerAction extends Action {

	int timeLeft;

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
	
}
