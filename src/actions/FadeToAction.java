package actions;

import model.Fadable;
import utils.Observer;

public class FadeToAction extends Action {

	private double targetFade;
	private int timeLeft;
	private Fadable fadable;
	
	public FadeToAction(Fadable fadable, double fade, int time, Observer observer) {
		super(observer);
		targetFade = fade;
		timeLeft = time;
		this.fadable = fadable;
	}
	
	public void update(){
		if(!started)
			return;
		
		timeLeft--;
		if(timeLeft == 0){
			fadable.setFade(targetFade);
			finish();
			return;
		}
		
		double oldFade = fadable.getFade();
		double dFade = targetFade - oldFade;
		
		fadable.setFade(oldFade + dFade/timeLeft);
	}

	
	
}
