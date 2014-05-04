package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import specific_ships_items.Scout;
import specific_ships_items.Sniper;

public class AttackTest {

	Scout ship = new Scout(new Point(1,1));
	Scout ship2 = new Scout(new Point(1,2));
	Sniper sniper = new Sniper(new Point(1, 3));
	
	@Test
	public void testLocation() {
		assertTrue(ship.getLocation().equals(new Point(1,1)));
		assertFalse(ship.getLocation().equals(new Point(0,1)));
		
		assertTrue(ship2.getLocation().equals(new Point(1,2)));
		assertFalse(ship2.getLocation().equals(new Point(2,1)));
	}

	@Test
	public void testAttack() {
		assertEquals(50, ship.getHull(), 1e-12);
		assertEquals(50, ship2.getHull(), 1e-12);
		ship.attack(ship2);
		assertFalse(ship2.getHull() == 50);
		System.out.println(ship2.getHull());
		ship.attack(ship2);
		System.out.println(ship2.getHull());
		ship.attack(ship2);	
		System.out.println(ship2.getHull());
		ship.attack(ship2);
		System.out.println(ship2.getHull());
		// ship2 health = 0 when damage does more than health
	}
	
	@Test
	public void testSpecial() {
		assertFalse(sniper.getCharged());
		sniper.useAbilityWithoutTarget(null);
		assertTrue(sniper.getCharged());
	}
}
