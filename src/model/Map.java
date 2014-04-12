package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * Built for no obstacles at the moment.  Returns a list of all tiles to move to not exclusive of starting tile, and inclusive of ending tile.
	 * TODO: Build to handle obstacles in path for later.
	 * @param currentLocation
	 * @param endLocation
	 * @return
	 */
	public List<Point> ShortestPath(Point currentLocation, Point endLocation){
		List<Point> listOfMoves = new ArrayList<Point> ();
		Point temp = currentLocation;
		while(!temp.equals(endLocation)){
			if(temp.x < endLocation.x)
				temp.x += 1;
			else if(temp.x > endLocation.x)
				temp.x -= 1;
			else if(temp.y < endLocation.y)
				temp.y += 1;
			else if(temp.y > endLocation.y)
				temp.y -= 1;
			listOfMoves.add(temp);
		}
		return listOfMoves;
	}
	
	public void setTile(){
		
	}
	
	
}
