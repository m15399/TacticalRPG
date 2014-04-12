package model;

import view.Camera;
import view.GraphicsTest;
import view.Starfield;


public class Level extends GameObject  {
	
	Starfield starfield;
	Camera camera;
	
	public Level(){
		
		camera = new Camera();
		
		starfield = new Starfield(Game.WIDTH, Game.HEIGHT, 7, 100, 22, 27, 300);
		addChild(starfield);
		
		camera.addChild(new Map());
		
		camera.addChild(new GraphicsTest());
		
		addChild(camera);

		
	}
	
	public void update(){
		double s = 0;
		starfield.scrollBy(s, s);
	}
	
	
	
}
