package actions;

import model.Observer;


public class TimerAction extends Action {

	int timeLeft;
	
	public TimerAction(Observer observer, int time){
		super(observer);
		
		timeLeft = time;
	}
	
	public void update(){
		timeLeft--;
		if(timeLeft == 0){
			finish();
		}
	}
	
}
