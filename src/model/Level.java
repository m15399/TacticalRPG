package model;

import java.awt.Color;
import java.awt.Graphics;

public class Level extends GameObject {
	
	int x,y;
	
	public Level(){
		x = 0;
		y = 0;
	}
	
	public void update(){
		x += 2;
		y += 2;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.white);
		g.fillRect(x, y, 20, 20);
	}
	
}
