package actions;

import java.util.LinkedList;
import java.util.Queue;

import utils.Observable;
import utils.Observer;


/*
 * Queue of Actions, plays the first action, 
 * when that's done plays the second action, etc
 */
public class ActionQueue extends Action implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4315545058583576547L;
	Queue<Action> queue; 
	
	public ActionQueue(Observer o){
		super(o);
		queue = new LinkedList<Action>();
	}
	
	public void addAction(Action a){
		addChild(a);
		queue.add(a);
		a.addObserver(this);
	}
	
	public void start(){
		startNextAction();
	}
	
	private void startNextAction(){
//		System.out.println("starting action");
		if(queue.isEmpty())
			finish();
		else{
			Action a = queue.poll();
			a.start();
		}
	}
	
	public void notified(Observable o){
		startNextAction();
	}

}
