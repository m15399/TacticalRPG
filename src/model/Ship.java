package model;

import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import shipVisuals.ShipVisual;
import utils.Direction;
import utils.Observer;
import view.Camera;

public class Ship extends GameObject {
	private Point location;
	private int moves, movesLeft, range, team;
	private double hull, shielding, maxHull, maxShielding, accuracy, minDamage,
			maxDamage, critChance;
	private List<Item> items;
	private Ability ability;
	private String description, name, filename;
	private boolean canAttack, canMove, canUseAbility, canUseItem, isWaiting, isTargetable;
	private Level level;
	private ShipVisual visual;
	private Random random = new Random();

	public Ship(Point newLocation) {
		location = new Point(newLocation);
		constructorAid("DefaultShip", 1, 1, 1, 1, 1, 1,
				"No description available", 1, 1, 1, 1, true, 0);
		items = new ArrayList<Item>();
		visual = null;
		ability = null;
	}
	
	public boolean isShipDead() {
		if (hull > 0)
			return false;
		return true;
	}
	
	/**
	 * Helps remind whoever is making a new class of all the values that should
	 * be declared to avoid them being default
	 * 
	 * @param name
	 * @param moves - meant as max movement range
	 * @param hull
	 * @param shielding
	 * @param maxHull
	 * @param maxShielding
	 * @param accuracy
	 * @param description
	 * @param minDamage
	 * @param maxDamage
	 * @param critChance
	 * @param range - meant as ship's attack range
	 * @param isTargetable
	 * @param team
	 */

	public void constructorAid(String name, int moves, double hull,
			double shielding, double maxHull, double maxShielding,
			double accuracy, String description,
			double minDamage, double maxDamage, double critChance, int range, boolean isTargetable, int team) {
		setName(name);
		setFileName(name.toLowerCase() + ".png");//doubles off of setName
		setMoves(moves);
		setMaxHull(maxHull);
		setHull(hull);
		setShielding(shielding);
		setMaxShielding(maxShielding);
		setAccuracy(accuracy);
		setDescription(description);
		setMinDamage(minDamage);
		setMaxDamage(maxDamage);
		setCritChance(critChance);
		setRange(range);
		setIsTargetable(isTargetable);
		setTeam(team);
	}
	
	public void startTurn(){
		setMovesLeft(moves);
		setCanAttack(true);
		setCanUseItem(true);
		setCanUseAbility(true);
		setIsWaiting(false);
		if(ability != null)
			ability.startTurn();
	}
	
	/*
	 * Plays the move animation 
	 */
	public void moveWithDirections(Observer notifyWhenDone, int x, int y, List<Direction> directions, Camera camera){
		Point originalPoint = new Point(getLocation());
		
		setLocation(new Point(x, y));
		ShipVisual visual = getVisual();
		if(visual != null){
			visual.moveWithDirections(originalPoint, notifyWhenDone, directions, camera);
		}
		updateMovesLeft(-directions.size());
	}
	
	/*
	 * Attack
	 */
	public void attack(Ship target) {
		
	}
	
	public void setIsWaiting(boolean b){
		isWaiting = b;
	}
	
	public boolean getIsWaiting(){
		return isWaiting;
	}
	
	/*
	 * Items
	 */
	
	public void itemUsed(Item item){
		removeFromItems(item);
		setCanUseItem(false);
	}
	
	/*
	 * Ability
	 */
	
	private void abilityUsed(){
		setCanUseAbility(false);
		ability.resetCooldown();
	}
	
	public void useAbilityOnShip(Ship ship, Observer notifyWhenDone){
		abilityUsed();
		ability.useOnShip(ship, notifyWhenDone);
	}
	
	public void useAbilityOnTile(Tile tile, Observer notifyWhenDone){
		abilityUsed();
		ability.useOnTile(tile, notifyWhenDone);
	}
	
	public void useAbilityWithoutTarget(Observer notifyWhenDone){
		abilityUsed();
		ability.useWithoutTarget(notifyWhenDone);
	}
		
	/*
	 * Returns a random damage amount between the min and max damage values.
	 * Remember nextInt returns a value between 0 inclusive and some value exclusive.
	 */
	
	public double getDamage(){
		double dmgModifier = Math.random();
		return (maxDamage-minDamage)*dmgModifier + minDamage;
	}
	
	/*
	 * Returns final damage after basic shielding calculations
	 */
	
	public double getFinalDamage(double initialDamage){
		if (initialDamage * ((100 - shielding) / 100) <= 0)
			return 0;
		else
			return (initialDamage * ((100 - shielding) / 100));
	}

	/*
	 * Setters and Getters for private instance variables. Please add other
	 * methods above these so they are easier to find.
	 */
	
	public void setLevel(Level level){
		this.level = level;
	}
	
	public Level getLevel(){
		return level;
	}
	
	public void setAbility(Ability ability){
		this.ability = ability;
		ability.setOwner(this);
		addChild(ability);
	}
	
	public Ability getAbility(){
		return ability;
	}
	
	public void setTeam(int newTeam){
		team = newTeam;
	}
	
	public int getTeam(){
		return team;
	}

	public void setVisual(ShipVisual newVisual){
		if(visual != null)
			visual.destroy();
		addChild(newVisual);
		visual = newVisual;
	}
	
	public ShipVisual getVisual(){
		return visual;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point newLocation) {
		location = newLocation;
	}

	public double getHull() {
		DecimalFormat df = new DecimalFormat("#.#");      
		hull = Double.valueOf(df.format(hull));
		return hull;
	}

	public void setHull(double newHullTotal) {
		hull = newHullTotal;
		if(hull < 0)
			hull = 0;
		if(hull > maxHull)
			hull = maxHull;
	}

	public void updateHull(double valueToAdjustHullBy) {
		setHull(hull + valueToAdjustHullBy);
	}

	public double getShielding() {
		return shielding;
	}

	public void setShielding(double newShieldingValue) {
		shielding = newShieldingValue;
	}

	public void updateShielding(double valueToAdjustShieldingBy) {
		shielding += valueToAdjustShieldingBy;
	}

	public double getMaxHull() {
		return maxHull;
	}

	public void setMaxHull(double newMaxHullTotal) {
		maxHull = newMaxHullTotal;
	}

	public void updateMaxHull(double valueToAdjustMaxHullBy) {
		maxHull += valueToAdjustMaxHullBy;
	}

	public double getMaxShielding() {
		return maxShielding;
	}

	public void setMaxShielding(double newMaxShieldingValue) {
		maxShielding = newMaxShieldingValue;
	}

	public void updateMaxShielding(double valueToAdjustMaxShieldingBy) {
		maxShielding += valueToAdjustMaxShieldingBy;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int newMoveTotal) {
		moves = newMoveTotal;
	}
	
	public int getMovesLeft(){
		return movesLeft;
	}
	
	public void setMovesLeft(int newMovesLeft){
		movesLeft = newMovesLeft;
		if(movesLeft > 0){
			setCanMove(true);
		} else {
			setCanMove(false);
		}
	}
	
	public void updateMovesLeft(int valueToAdjustBy){
		setMovesLeft(getMovesLeft() + valueToAdjustBy);
	}

	public List<Item> getItems() {
		return items;
	}

	public void addToItems(Item newItem) {
		items.add(newItem);
		newItem.setOwner(this);
		addChild(newItem);
	}

	public void removeFromItems(Item itemUsed) {
		items.remove(itemUsed);
		itemUsed.setOwner(null);
		removeChild(itemUsed);
	}

	public void setAccuracy(double newAccuracy) {
		accuracy = newAccuracy;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void updateAccuracy(double valueToAdjustBy) {
		accuracy += valueToAdjustBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public void setMinDamage(double newMinDamage) {
		minDamage = newMinDamage;
	}

	public double getMinDamage() {
		return minDamage;
	}

	public void updateMinDamage(double valueToAdjustBy) {
		minDamage += valueToAdjustBy;
	}

	public void setMaxDamage(double newMaxDamage) {
		maxDamage = newMaxDamage;
	}

	public double getMaxDamage() {
		return maxDamage;
	}

	public void updateMaxDamage(double valueToAdjustBy) {
		maxDamage += valueToAdjustBy;
	}

	public void setCritChance(double newCritValue) {
		critChance = newCritValue;
	}

	public double getCritChance() {
		return critChance;
	}

	public void updateCritChance(double valueToAdjustBy) {
		critChance += valueToAdjustBy;
	}
	
	public int getRange(){
		return range;
	}
	
	public void setRange(int newRange){
		range = newRange;
	}
	
	public void updateRange(int valueToAdjustBy){
		range += valueToAdjustBy;
	}
	
	public boolean getCanAttack() {
		if(range == 0)
			return false;
		return canAttack;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

	public boolean getCanMove() {
		if(movesLeft == 0)
			return false;
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public Boolean getCanUseAbility() {
		return canUseAbility;
	}

	public void setCanUseAbility(boolean canUseAbility) {
		this.canUseAbility = canUseAbility;
	}

	public boolean isCanUseItem() {
		return canUseItem;
	}

	public void setCanUseItem(boolean canUseItem) {
		this.canUseItem = canUseItem;
	}
	
	public String getFileName(){
		return filename;
	}
	
	public void setFileName(String newFileName){
		filename = newFileName;
	}
	
	public boolean getIsTargetable(){
		return isTargetable;
	}
	
	public void setIsTargetable(boolean isTargetable){
		this.isTargetable = isTargetable;
	}
	
	public boolean isHit() {
		if (random.nextInt(100) < getAccuracy()) {
			return true;
		}
		else return false;
	}
	
	public String itemsToString(){
		String itemsString = "";
		for(int i = 0; i < items.size(); i++){
			if(i == items.size()-1){
				itemsString += items.get(i).getName();
			}
			else{
			itemsString += items.get(i).getName() + ", ";
			}
		}
		return itemsString;
	}
	
	public String shipStatus(){
		String shipStatus = "";
		shipStatus += this.getName();
		shipStatus += "\n";
		shipStatus += "Hull: " + this.getHull();
		shipStatus += "\n";
		shipStatus += "Shielding: " + this.getMaxShielding();
		shipStatus += "\n";
		shipStatus += "Damage: " + this.getDamage();
		shipStatus += "\n";
		shipStatus += "Items: " + this.itemsToString();
		shipStatus += "\n";
		return shipStatus;
	}
	
	public String toString(){
		return shipStatus();
	}
}
