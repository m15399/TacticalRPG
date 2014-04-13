package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import utils.Direction;

/*
 * 2D array of Tiles 
 */
public class Map extends GameObject {

	public static final int TILESIZE = 64;

	private Tile[][] tiles;
	private int width, height;

	public Map(int width, int height) {
		this.width = width;
		this.height = height;

		// create tiles
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

	/*
	 * Convert the pixel coords to map/tile coords e.g. (100, 100) -> (2, 2)
	 */
	public static Point pixelToMapCoords(Point p) {
		int mapX = (int) Math.floor(p.getX() / TILESIZE);
		int mapY = (int) Math.floor(p.getY() / TILESIZE);
		return new Point(mapX, mapY);
	}

	/*
	 * Convert the map coords to pixel coords
	 */
	public static Point mapToPixelCoords(Point p) {
		int x = (int) p.getX() * Map.TILESIZE;
		int y = (int) p.getY() * Map.TILESIZE;
		return new Point(x, y);
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		int pxWidth = width * TILESIZE;
		int pxHeight = height * TILESIZE;

		for (int i = 0; i <= width; i++) {
			g.drawLine(i * TILESIZE, 0, i * TILESIZE, pxHeight);
		}
		for (int i = 0; i <= height; i++) {
			g.drawLine(0, i * TILESIZE, pxWidth, i * TILESIZE);
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
	public List<Direction> shortestPath(Point currentLocation, Point endLocation) {
		List<Direction> listOfMoves = new ArrayList<Direction>();
		Point temp = currentLocation;
		while (!temp.equals(endLocation)) {
			if (temp.x < endLocation.x) {
				temp.x += 1;
				listOfMoves.add(Direction.RIGHT);
			} else if (temp.x > endLocation.x) {
				temp.x -= 1;
				listOfMoves.add(Direction.LEFT);
			} else if (temp.y < endLocation.y) {
				temp.y += 1;
				listOfMoves.add(Direction.DOWN);
			} else if (temp.y > endLocation.y) {
				temp.y -= 1;
				listOfMoves.add(Direction.UP);
			}
		}
		return listOfMoves;
	}

	/*
	 * Added two setter methods for Tile. Just use whichever one is more
	 * convenient.
	 */

	public void setTile(int x, int y, Tile tile) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			tiles[x][y] = tile;
		}
	}

	public void setTile(Point point, Tile tile) {
		setTile(point.x, point.y, tile);
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
