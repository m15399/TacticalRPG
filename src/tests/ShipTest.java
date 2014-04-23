package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import specific_ships_items.Bomber;
import specific_ships_items.MagneticShield;
import specific_ships_items.ScrapMetal;
import specific_ships_items.SpaceMine;
import model.Ship;

public class ShipTest {
	
	Ship ship = new Ship(new Point(1,1));
	
	@Test
	public void testLocation(){
		assertTrue(ship.getLocation().equals(new Point(1,1)));
		assertFalse(ship.getLocation().equals(new Point(0,1)));
		ship.setLocation(new Point(2,2));
		assertTrue(ship.getLocation().equals(new Point(2,2)));
		assertFalse(ship.getLocation().equals(new Point(1,1)));
	}
	
	@Test
	public void testHealth(){
		assertEquals(1, ship.getHull(), 1e-12);
		ship.setHull(4);
		assertEquals(4, ship.getHull(), 1e-12);
		ship.updateHull(-2);
		assertEquals(2, ship.getHull(), 1e-12);
	}
	
	@Test
	public void testArmor(){
		assertEquals(1, ship.getShielding(), 1e-12);
		ship.setShielding(4);
		assertEquals(4, ship.getShielding(), 1e-12);
		ship.updateShielding(-2);
		assertEquals(2, ship.getShielding(), 1e-12);
	}
	
	@Test
	public void testMoves(){
		assertEquals(1, ship.getMoves());
		ship.setMoves(4);
		assertEquals(4, ship.getMoves());
	}
	
	@Test
	public void testItems(){
		//TODO new a class that extends Item to implement
		//TODO must test getItems, addToItems, removeFromItems
	}
	
	@Test
	public void testToString(){
		Ship bomber = new Bomber(new Point(1,1));
	
		bomber.addToItems(new ScrapMetal());
		bomber.addToItems(new SpaceMine());
		bomber.addToItems(new MagneticShield());
		System.out.print(bomber.shipStatus());
	}
	
}
