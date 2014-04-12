package model;

import view.GraphicsTest;
import view.Starfield;


public class Level extends GameObject  {
	
	Starfield starfield;
	
	public Level(){
		
		starfield = new Starfield(Game.WIDTH, Game.HEIGHT, 7, 100, 22, 27, 300);
		addChild(starfield);
		
		addChild(new Map());
		
		addChild(new GraphicsTest());
		
		
	}
	
	public void update(){
		double s = 4;
		starfield.scrollBy(s, s);
	}
	
	
	
}
