package actions;

import java.util.LinkedList;
import java.util.Queue;

import model.Observable;
import model.Observer;

public class ActionQueue extends Action implements Observer {
	
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
