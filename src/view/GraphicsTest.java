package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import actions.*;
import model.*;



public class GraphicsTest extends GameObject implements Observer {
	
	Sprite testSprite, testSprite2, testSprite3;
	
	public GraphicsTest(){
		for(int i = 0; i < 50; i++){
			Sprite s = new Sprite("test.png", (int)(Math.random() * Game.WIDTH),
					(int)(Math.random() * Game.HEIGHT));
			s.getPosition().setMirrored(true);
			s.getPosition().setScale(.5, .5);
			addChild(s);
			
		}
		
		testSprite = new Sprite("test.png", 40,300);
		testSprite2 = new Sprite("test.png", 400,200);
		testSprite3 = new Sprite("test.png", 70,20);
		addChild(testSprite);
		addChild(testSprite2);
		addChild(testSprite3);
		
		testSprite2.getPosition().setScale(1,1);
		testSprite2.getPosition().mirror();
		
		testSprite3.getPosition().setParent(testSprite2.getPosition());
		
		Action a = new TimerAction(90, this);
		addChild(a);
		a.start();
		
		ActionQueue aq = new ActionQueue(null);
		for(int i = 0; i < 10; i++){
			aq.addAction(new MoveEntityToAction(testSprite2, 
					new Position((int)(Math.random() * 100)+400,
					(int)(Math.random() * 100)+200), 40, null));
			aq.addAction(new MirrorEntityAction(testSprite2, null));
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
	
	public void drawSelfAndChildren(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform saved = g2.getTransform();
		
//		g2.scale(3,3);
		
		super.drawSelfAndChildren(g);
		
		g2.setTransform(saved);		
	}
	
	public void draw(Graphics g){
		
	}

	public void notified(Observable o){
		System.out.println("Timer working");
	}
	
}
