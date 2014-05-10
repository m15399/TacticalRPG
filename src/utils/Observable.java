package utils;

import java.io.Serializable;
import java.util.ArrayList;

public class Observable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1123129087048329727L;
	ArrayList<Observer> observers;
	
	public Observable(){
		observers = new ArrayList<Observer>();
	}
	
	public void addObserver(Observer observer){
		if(observer != null)
			observers.add(observer);
	}
	
	public void removeObserver(Observer observer){
		observers.remove(observer);
	}
	
	public void notifyObservers(){
		for(Observer o : observers){
			o.notified(this);
		}
	}
	
}
