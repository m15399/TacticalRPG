package levels;

import java.awt.Point;

import specific_ships_items.AISpawner;
import specific_ships_items.Bomber;
import specific_ships_items.Fighter;
import specific_ships_items.MagneticShield;
import specific_ships_items.Scout;
import specific_ships_items.ScrapMetal;
import specific_ships_items.WarpGateShip;
import model.GameObject;
import model.Level;

public class TestLevel extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5206578761391762646L;

	public TestLevel() {
		super(16, 12);
		
		// Ship Testing
		
		/*
		 * Player Ships
		 */
		
		//Scout
		Scout scout = new Scout(new Point(4, 4));
		addShipToMap(scout);
		
		//Fighter
		Fighter fighter = new Fighter(new Point(1, 3));
		fighter.addToItems(new ScrapMetal());
		addShipToMap(fighter);
		
		//Fighter
		Fighter fighter2 = new Fighter(new Point(3, 1));
		fighter2.addToItems(new ScrapMetal());
		addShipToMap(fighter2);
		
		//Bomber
		Bomber bomber = new Bomber(new Point(1, 1));
		bomber.addToItems(new MagneticShield());
		addShipToMap(bomber);
		
		//Warpgate
		WarpGateShip warpgate = new WarpGateShip(new Point(0,0));
		addShipToMap(warpgate);
		
		/*
		 * Enemy Ships
		 */
		
		//Scout
		Scout escout = new Scout(new Point(10, 6)); 
		addEnemyShipToMap(escout);
		
		//Fighter
		Fighter efighter = new Fighter(new Point(13, 7));//was 15,9
		efighter.addToItems(new ScrapMetal());
		addEnemyShipToMap(efighter);
		
		//Fighter
		Fighter efighter2 = new Fighter(new Point(11, 9));//was 13,11
		efighter2.addToItems(new ScrapMetal());
		addEnemyShipToMap(efighter2);
		
		//Bomber
		Bomber ebomber = new Bomber(new Point(13, 9)); //was 15,11
		ebomber.addToItems(new MagneticShield());
		addEnemyShipToMap(ebomber);
		
		AISpawner spawner = new AISpawner(new Point(13, 0));
		addEnemyShipToMap(spawner);
						
		startTurn(0);
	}
	
	public GameObject getNextRoot(){
		return new TestLevelFromFile();
	}

}
