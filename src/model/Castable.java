package model;

public abstract class Castable extends GameObject {

	private String description;
	private String name;
	private boolean isTargetted;
	private int castRange;
	
	private Ship owner;
	
	public Castable(String name, String description, boolean isTargetted, int castRange){
		this.name = name;
		this.description = description;
		this.isTargetted = isTargetted;
		this.castRange = castRange;
		owner = null;
	}
	
	public abstract void useWithoutTarget();
	public abstract void useOnShip(Ship ship);
	public abstract void useOnTile(Tile tile);
	
	/*
	 * Setters and Getters.  Please add other methods above these so they are easier to find.
	 */
	
	public Ship getOwner(){
		return owner;
	}
	
	public void setOwner(Ship ship){
		owner = ship;
	}
	
	public int getCastRange(){
		return castRange;
	}
	
	public boolean getIsTargetted(){
		return isTargetted;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getName(){
		return name;
	}
	
	public void setDescription(String newDescription){
		description = newDescription;
	}
	
}
