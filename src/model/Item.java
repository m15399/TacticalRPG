package model;

public abstract class Item extends Castable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2619087685975325705L;

	public Item(String name, String description, Castable.TargetType targetType, int castRange){
		super(name, description, targetType, castRange);
		
	}
	
	public abstract String getFilename();
	
}
