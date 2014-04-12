package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Map extends GameObject {

	public static final int TILESIZE = 64;
	
	private Tile[][] tiles;
	private int width, height;
	
	public Map(){
		width = height = 10;
		tiles = new Tile[width][height];
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				tiles[i][j] = new Tile(i, j);
				addChild(tiles[i][j]);
			}
		}
		
		tiles[0][0].setHighlight(Tile.Highlight.BLUE);
		tiles[1][0].setHighlight(Tile.Highlight.LIGHTBLUE);
		tiles[2][0].setHighlight(Tile.Highlight.RED);
		tiles[3][0].setHighlight(Tile.Highlight.LIGHTRED);
		tiles[4][0].setHighlight(Tile.Highlight.GREEN);
		tiles[5][0].setHighlight(Tile.Highlight.LIGHTGREEN);
	}
	
	public void draw(Graphics g) {
		// Tiles 64x64
		g.setColor(Color.WHITE);
		int counter1 = 0;
		int counter2 = 0;
		for (int i = 0; i <= Game.WIDTH; i += TILESIZE) {
			g.drawLine(i, 0, i, Game.HEIGHT);
			counter1++;
		}
		for (int i = 0; i <= Game.HEIGHT; i += TILESIZE) {
			g.drawLine(0, i, Game.WIDTH, i);
			counter2++;
		}
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
	
	public Tile getTile(int mapX, int mapY){
		return tiles[mapX][mapY];
	}
	
	
}
