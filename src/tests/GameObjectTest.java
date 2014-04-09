package tests;

import static org.junit.Assert.*;
import model.GameObject;

import org.junit.Test;

public class GameObjectTest {
	
	/*
	 * tests the constructor, setChildren, getChildren, addChild, and removeChild
	 */
	
	@Test
	public void testSetAndGetChildren(){
		GameObject game = new GameObject();
		GameObject one = new GameObject();
		GameObject two = new GameObject();
		GameObject three = new GameObject();
		game.addChild(one);
		game.addChild(two);
		game.addChild(three);
		assertTrue(game.getChildren().size() == 3);
		game.removeChild(two);
		assertFalse(game.getChildren().size() == 3);
		assertTrue(game.getChildren().size() == 2);
	}
}
