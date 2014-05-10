package model;

import utils.Observer;

public abstract class Castable extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5311342057545579843L;

	public enum TargetType{
		NONE, ALLY, ENEMY, TILE;
	}
	
	private String description;
	private String name;
	private TargetType targetType;
	private int castRange;
	
	private Ship owner;
	
	public Castable(String name, String description, TargetType targetType, int castRange){
		this.name = name;
		this.description = description;
		this.targetType = targetType;
		this.castRange = castRange;
		owner = null;
	}
	
	public void useWithoutTarget(Observer notifyWhenDone){}
	public void useOnShip(Ship ship, Observer notifyWhenDone){}
	public void useOnTile(Tile tile, Observer notifyWhenDone){}
	
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
	
	public TargetType getTargetType(){
		return targetType;
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
