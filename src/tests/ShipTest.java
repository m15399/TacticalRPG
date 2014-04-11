package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

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
		assertEquals(1, ship.getHealth());
		ship.setHealth(4);
		assertEquals(4, ship.getHealth());
		ship.updateHealth(-2);
		assertEquals(2, ship.getHealth());
	}
	
	@Test
	public void testArmor(){
		assertEquals(1, ship.getArmor(), 0);
		ship.setArmor(4);
		assertEquals(4, ship.getArmor(), 0);
		ship.updateArmor(-2);
		assertEquals(2, ship.getArmor(), 0);
	}
	
	@Test
	public void testMoves(){
		assertEquals(1, ship.getMoves());
		ship.setMoves(4);
		assertEquals(4, ship.getMoves());
		ship.updateMoves(-2);
		assertEquals(2, ship.getMoves());
	}
	
	@Test
	public void testItems(){
		//TODO new a class that extends Item to implement
		//TODO must test getItems, addToItems, removeFromItems
	}
}
