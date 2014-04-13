package utils;

import java.util.ArrayList;

public class Observable {
	
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
