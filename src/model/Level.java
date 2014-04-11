package model;

import java.awt.Graphics;
import model.Observable;
import model.Observer;

import actions.TimerAction;

import view.Sprite;

public class Level extends GameObject implements Observer {
	
	Sprite testSprite, testSprite2;
	
	public Level(){
		for(int i = 0; i < 50; i++){
			Sprite s = new Sprite("test.png", (int)(Math.random() * Game.WIDTH),
					(int)(Math.random() * Game.HEIGHT));
			s.getPosition().setMirrored(true);
			addChild(s);
			
		}
		
		testSprite = new Sprite("test.png", 40,300);
		testSprite2 = new Sprite("test.png", 100,100);
		addChild(testSprite);
//		addChild(testSprite2);
		
//		testSprite2.getPosition().setScale(4,4);
//		testSprite.getPosition().mirror();
		
		addChild(new TimerAction(this, 90));
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
		System.out.println("Working");
	}
	
}
