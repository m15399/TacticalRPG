package src.tests;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.util.ArrayList;

import src.model.GameObject;

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
	
	private class TestObject extends GameObject{
		int updates;
		int draws;
		boolean destroyDuringUpdate;
		boolean destroyedSuccessfully;
		
		public TestObject(){
			updates = 0;
			draws = 0;
			destroyDuringUpdate = false;
			destroyedSuccessfully = false;
		}
		
		public void update(){
			updates++;
			if(destroyDuringUpdate)
				destroy();
		}
		
		public void draw(Graphics g){
			draws++;
		}
		
		public void onDestroy(){
			destroyedSuccessfully = true;
		}
	}
	
	@Test
	public void testUpdatingAndDrawing(){
		GameObject root = new GameObject();
		
		TestObject object1 = new TestObject();
		root.addChild(object1);
		
		TestObject object2 = new TestObject();
		object1.addChild(object2);
		
		assertEquals(object1.updates, 0);
		assertEquals(object2.updates, 0);
		root.updateSelfAndChildren();
		assertEquals(object1.updates, 1);
		assertEquals(object2.updates, 1);
		
		assertEquals(object1.draws, 0);
		assertEquals(object2.draws, 0);
		root.drawSelfAndChildren(null);
		assertEquals(object1.draws, 1);
		assertEquals(object2.draws, 1);

	}
	
	@Test
	public void testDestroyingObjectDuringUpdate(){
		GameObject root = new GameObject();
		
		ArrayList<TestObject> objects = new ArrayList<TestObject>();
		
		for(int i = 0; i < 10; i++){
			TestObject o = new TestObject();
			objects.add(o);
			root.addChild(o);
		}
		
		objects.get(0).destroyDuringUpdate = true;
		objects.get(1).destroyDuringUpdate = true;
		objects.get(2).destroyDuringUpdate = true;
		
		root.updateSelfAndChildren();
		root.updateSelfAndChildren(); 
		
		assertEquals(root.getChildren().size(), 7);
		
		for(int i = 0; i < 10; i++){
			TestObject o = objects.get(i);
						
			if(o.destroyDuringUpdate){
				assertEquals(o.updates, 1);
				assertTrue(o.destroyedSuccessfully);
			} else 
				assertEquals(o.updates, 2); 
		}
		

	}
}
