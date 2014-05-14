package specific_ships_items;

import java.awt.Point;

import model.Level;
import model.Map;
import model.Ship;
import model.Tile;
import shipVisuals.MothershipVisual;

public class Mothership extends Ship{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2810332910910250251L;

	/**
	 * Reminder on what things constructorAid sets to help in balancing
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
	
	
	
	public Mothership(Point newLocation) {
		super(newLocation);
		String description = "Grand mothership,\nstationary and capable\nof unit production.";
		this.constructorAid("Mothership", 0, 350, 50, 350, 50, 100, description, 30, 35, 10, 5, true, 0);
		
		setVisual(new MothershipVisual(this));
		
	}
	
	public void onAddedToLevel(){
		
		Level level = getLevel();

		int x = getLocation().x;
		int y = getLocation().y;
		
		Turret t1 = new Turret(new Point(x - 3, y));
		Turret t2 = new Turret(new Point(x + 3, y));
		
		Map map = level.getMap();
		Point p = new Point(x-2, y-1);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x-1, y-1);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x, y-1);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x+1, y-1);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x+2, y-1);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x-1, y-2);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x, y-2);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x+1, y-2);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x-2, y);
		map.setTile(p, new Tile(false, p.x, p.y));
		p = new Point(x+2, y);
		map.setTile(p, new Tile(false, p.x, p.y));
		
		if(getTeam() == 1){
			level.addEnemyShipToMap(t1);
			level.addEnemyShipToMap(t2);
			setAbility(new SpawnEnemyShipAbility());

		} else {
			level.addShipToMap(t1);
			level.addShipToMap(t2);
			setAbility(new PlaceFighterAbility());
		}
		
	}
	
	/*
	 * Ship basic attack.
	 */
	@Override
	public void attack(Ship target) {
		double damage = this.getDamage();
		if (target.getHull() - target.getFinalDamage(damage) > 0)
			target.setHull(target.getHull() - target.getFinalDamage(damage));
		else
			target.setHull(0);
		this.setCanAttack(false);
		setDidMiss(false);
	}



}
