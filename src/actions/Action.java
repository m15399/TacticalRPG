package actions;

import model.Observable;
import model.Observer;

import model.GameObject;

/*
 * Action class that runs some code over time and 
 * then notifies the listeners when it's done (useful for animation)
 */
public class Action extends GameObject {

	Observable observable;

	public Action(Observer observer) {
		observable = new Observable();
		addObserver(observer);
	}
	
	public void addObserver(Observer o){
		observable.addObserver(o);
	}

	public void start() {
		finish();
	}

	protected void finish() {
		observable.notifyObservers();
		destroy();
	}

}
