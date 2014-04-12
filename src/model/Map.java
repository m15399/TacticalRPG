package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Map extends GameObject {

	private Tile[][] tiles;
	
	public void draw(Graphics g) {
		// Tiles 64x64
		g.setColor(Color.WHITE);
		int counter1 = 0;
		int counter2 = 0;
		for (int i = 0; i <= Game.WIDTH; i += 64) {
			g.drawLine(i, 0, i, Game.HEIGHT);
			counter1++;
		}
		for (int i = 0; i <= Game.HEIGHT; i += 64) {
			g.drawLine(0, i, Game.WIDTH, i);
			counter2++;
		}
		tiles = new Tile[counter1][counter2];
	}
	
//	public Point[] ShortestPath(Point currentLocation, Point endLocation){
//		//TODO implement
//	}
	
	public void setTile(){
		
	}
	
	
}
