package view;

import java.awt.Graphics;

import actions.*;
import model.*;



public class GraphicsTest extends GameObject implements Observer {
	
	Sprite testSprite, testSprite2;
	
	public GraphicsTest(){
		for(int i = 0; i < 50; i++){
			Sprite s = new Sprite("test.png", (int)(Math.random() * Game.WIDTH),
					(int)(Math.random() * Game.HEIGHT));
			s.getPosition().setMirrored(true);
			addChild(s);
			
		}
		
		testSprite = new Sprite("test.png", 40,300);
		testSprite2 = new Sprite("test.png", 100,100);
		addChild(testSprite);
		addChild(testSprite2);
		
//		testSprite2.getPosition().setScale(4,4);
//		testSprite.getPosition().mirror();
		
		Action a = new TimerAction(90, this);
		addChild(a);
		a.start();
		
		ActionQueue aq = new ActionQueue(null);
		for(int i = 0; i < 10; i++){
			aq.addAction(new MoveEntityToAction(testSprite2, 
					new Position((int)(Math.random() * 100)+100,
					(int)(Math.random() * 100)+100), 10, null));
		}
		addChild(aq);
		aq.start();
	}
	
	public void update(){
		testSprite.getPosition().moveBy(1.4,0);
		testSprite.getPosition().rotateBy(0.007);
		testSprite.getPosition().scaleBy(1.002, 1.002);
//		testSprite.getPosition().mirror();

	}
	
	public void draw(Graphics g){
		
	}

	public void notified(Observable o){
		System.out.println("Timer working");
	}
	
}
