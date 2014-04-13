package view;


import java.awt.Point;

import actions.*;
import model.*;



public class GraphicsTest extends GameObject implements Observer {
	
	Sprite testSprite, testSprite2, testSprite3;
	
	public GraphicsTest(){
		// add random sprites
		for(int i = 0; i < 50; i++){
			Sprite s = new Sprite("test.png", (int)(Math.random() * Game.WIDTH),
					(int)(Math.random() * Game.HEIGHT));
			s.getPosition().setMirrored(true);
			s.getPosition().setScale(.5, .5);
			addChild(s);
			
		}
		
		// test sprites
		testSprite = new Sprite("test.png", 40,300);
		testSprite2 = new Sprite("test.png", 400,200);
		testSprite3 = new Sprite("test.png", 70,20);
		addChild(testSprite);
		addChild(testSprite2);
		addChild(testSprite3);
		testSprite2.getPosition().setScale(1,1);
		testSprite2.getPosition().mirror();
		
		testSprite3.getPosition().setParent(testSprite2.getPosition());
		
		// test timer
		Action a = new TimerAction(90, this);
		addChild(a);
		a.start();
		
		// test actionqueue
		ActionQueue aq = new ActionQueue(null);
		for(int i = 0; i < 10; i++){
			aq.addAction(new MoveEntityToAction(testSprite2, 
					new Position((int)(Math.random() * 100)+400,
					(int)(Math.random() * 100)+200), 40, null));
			aq.addAction(new MirrorEntityAction(testSprite2, null));
		}
		addChild(aq);
		aq.start();
		
		// test animated sprites
		AnimatedSprite as = new AnimatedSprite("smurf_sprite.png", 960, 680);
		as.getPosition().mirror();
		as.setDelay(2);
		as.setFrameSize(128, 128);
//		int[] indicies = {1, 2, 3, 4, 4, 4, 4, 4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
//		as.setFrameIndicies(indicies);
		as.setNumberFrames(16);
		addChild(as);
	}
	
	public void update(){
		testSprite.getPosition().moveBy(1.4,0);
		testSprite.getPosition().rotateBy(0.007);
		testSprite.getPosition().scaleBy(1.002, 1.002);
//		testSprite.getPosition().mirror();

	}

	public void notified(Observable o){
		System.out.println("Timer working");
	}
	
}
