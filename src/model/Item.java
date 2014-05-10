package model;

public abstract class Item extends Castable{
	
	public Item(String name, String description, Castable.TargetType targetType, int castRange){
		super(name, description, targetType, castRange);
		
	}
	
	public abstract String getFilename();
	
}
