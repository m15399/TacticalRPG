package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/*
 * 2D array of Tiles 
 */
public class Map extends GameObject {

	public static final int TILESIZE = 64;

	private Tile[][] tiles;
	private int width, height;

	public Map() {
		width = height = 10;
		tiles = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile(i, j);
				addChild(tiles[i][j]);
			}
		}

		// testing highlighting
		tiles[0][0].setHighlight(Tile.Highlight.BLUE);
		tiles[1][0].setHighlight(Tile.Highlight.BLUE);
		tiles[2][0].setHighlight(Tile.Highlight.RED);
		tiles[3][0].setHighlight(Tile.Highlight.RED);
		tiles[4][0].setHighlight(Tile.Highlight.GREEN);
		tiles[5][0].setHighlight(Tile.Highlight.GREEN);
	}

	public static Point pixelToMapCoords(Point p) {
		int mapX = (int) Math.floor(p.getX() / TILESIZE);
		int mapY = (int) Math.floor(p.getY() / TILESIZE);
		return new Point(mapX, mapY);
	}

	public static Point mapToPixelCoords(Point p) {
		int x = (int) p.getX() * Map.TILESIZE;
		int y = (int) p.getY() * Map.TILESIZE;
		return new Point(x, y);
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
	 * Built for no obstacles at the moment. Returns a list of all tiles to move
	 * to not exclusive of starting tile, and inclusive of ending tile. TODO:
	 * Build to handle obstacles in path for later.
	 * 
	 * @param currentLocation
	 * @param endLocation
	 * @return
	 */
	public List<Point> shortestPath(Point currentLocation, Point endLocation) {
		List<Point> listOfMoves = new ArrayList<Point>();
		Point temp = currentLocation;
		while (!temp.equals(endLocation)) {
			if (temp.x < endLocation.x)
				temp.x += 1;
			else if (temp.x > endLocation.x)
				temp.x -= 1;
			else if (temp.y < endLocation.y)
				temp.y += 1;
			else if (temp.y > endLocation.y)
				temp.y -= 1;
			listOfMoves.add(new Point(temp.x, temp.y));
		}
		return listOfMoves;
	}
	
	/*
	 * Added two setter methods for Tile.  Just use whichever one is more convenient.
	 */
	
	public void setTile(int x, int y, Tile tile){
		tiles[x][y] = tile;
	}
	
	public void setTile(Point point, Tile tile){
		tiles[point.x][point.y] = tile;
	}

	public Tile getTile(int mapX, int mapY) {
		if (mapX >= 0 && mapX < width && mapY >= 0 && mapY < height) {
			return tiles[mapX][mapY];
		} else {
			return null;
		}
	}

	public Tile getTile(Point p) {
		return getTile((int) p.getX(), (int) p.getY());
	}

}
