package actions;

import utils.Observable;
import utils.Observer;

import model.GameObject;

/*
 * Action class that runs some code over time and 
 * then notifies the listeners when it's done (useful for animation)
 */
public class Action extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717528151348192013L;
	Observable observable;
	boolean started;
	
	public Action(Observer observer) {
		observable = new Observable();
		addObserver(observer);
		started = false;
	}
	
	public void addObserver(Observer o){
		observable.addObserver(o);
	}

	public void start() {
		started = true;
	}
	
	public boolean getStarted(){
		return started;
	}
	
	/*
	 * default Action just finishes
	 */
	public void update(){
		if(started){
			finish();
		}
	}

	protected void finish() {
		observable.notifyObservers();
		destroy();
	}

}
