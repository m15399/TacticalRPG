package model;

import view.GraphicsTest;


public class Level extends GameObject  {
	
	
	public Level(){
		
		addChild(new Map());
		
		addChild(new GraphicsTest());
		
		
	}
	
	
	
}
