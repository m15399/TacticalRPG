package model;

public abstract class Item extends Castable{
	
	public Item(String name, String description, boolean isTargetted, int castRange){
		super(name, description, isTargetted, castRange);
		
	}	
	
}
