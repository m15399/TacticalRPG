package src.model;

public abstract class Item extends GameObject{
	private String description;
	
	public Item(){
		//TODO create the item
	}
	
	public abstract void useOn(Ship ship);
	
	/*
	 * Setters and Getters.  Please add other methods above these so they are easier to find.
	 */
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String newDescription){
		description = newDescription;
	}
}
