package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import model.Tile.Highlight;
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

	public void draw(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		
		float v = 1.0f;
		g.setColor(new Color(v, v, v, .25f));
		
		int pxWidth = width * TILESIZE;
		int pxHeight = height * TILESIZE;

//		float dash[] = {2f};
//		g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.CAP_BUTT, 10.0f, dash, 0.0f));
		g.setStroke(new BasicStroke(2));
		
		for (int i = 0; i <= width; i++) {
			g.drawLine(i * TILESIZE, 0, i * TILESIZE, pxHeight);
		}
		for (int i = 0; i <= height; i++) {
			g.drawLine(0, i * TILESIZE, pxWidth, i * TILESIZE);
		}
		
		g.setStroke(new BasicStroke());
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
//	public List<Direction> shortestPath(Point currentLocation, Point endLocation) {
//		List<Direction> listOfMoves = new ArrayList<Direction>();
//		Point temp = currentLocation;
//		while (!temp.equals(endLocation)) {
//			if (temp.x < endLocation.x) {
//				temp.x += 1;
//				listOfMoves.add(Direction.RIGHT);
//			} else if (temp.x > endLocation.x) {
//				temp.x -= 1;
//				listOfMoves.add(Direction.LEFT);
//			} else if (temp.y < endLocation.y) {
//				temp.y += 1;
//				listOfMoves.add(Direction.DOWN);
//			} else if (temp.y > endLocation.y) {
//				temp.y -= 1;
//				listOfMoves.add(Direction.UP);
//			}
//		}
//		return listOfMoves;
//	}
	
	
	public List<Direction> shortestPath(Point curr, Point endLocation, int moves){
		System.out.println(curr.toString() + endLocation.toString() + "  " + moves);
		System.out.println(this.toString());
		Point beginning = curr;
		PriorityQueue<Tile> tilesLeft = new PriorityQueue<Tile>();
		List<Point> pointsToCheck = new ArrayList<Point>();
		for(int x = 0; x < tiles.length; x++){
			System.out.print("\nnew line: ");
			for(int y = 0; y < tiles[x].length; y++){
				tiles[x][y].setDistance(10000);
				tiles[x][y].setPreviousTile(null);
				tilesLeft.add(tiles[x][y]);
				System.out.print(tiles[x][y].getIsOccupied() + " ");
			}
		}
		tiles[curr.x][curr.y].setDistance(0);
		tilesLeft.remove(tiles[curr.x][curr.y]);
		while(tiles[endLocation.x][endLocation.y].getDistance() == 10000){
			System.out.println(curr.toString() + tiles[endLocation.x][endLocation.y].getDistance());
			if(checkIfTileExists(new Point(curr.x+1, curr.y)) && !tiles[curr.x+1][curr.y].getIsOccupied() && tiles[curr.x+1][curr.y].getDistance() == 10000){
				tiles[curr.x+1][curr.y].setDistance(tiles[curr.x][curr.y].getDistance()+1);
				if(tiles[curr.x][curr.y].getDistance() < moves){
					tiles[curr.x+1][curr.y].setHighlight(Highlight.BLUE);
				}
				tilesLeft.remove(tiles[curr.x+1][curr.y]);
				tiles[curr.x+1][curr.y].setPreviousTile(tiles[curr.x][curr.y]);
				pointsToCheck.add(new Point(curr.x+1,curr.y));
			}
			if(checkIfTileExists(new Point(curr.x, curr.y+1)) && !tiles[curr.x][curr.y+1].getIsOccupied() && tiles[curr.x][curr.y+1].getDistance() == 10000){
				tiles[curr.x][curr.y+1].setDistance(tiles[curr.x][curr.y].getDistance()+1);
				if(tiles[curr.x][curr.y].getDistance() < moves){
					tiles[curr.x][curr.y+1].setHighlight(Highlight.BLUE);
				}
				
				tilesLeft.remove(tiles[curr.x][curr.y+1]);
				tiles[curr.x][curr.y+1].setPreviousTile(tiles[curr.x][curr.y]);
				pointsToCheck.add(new Point(curr.x,curr.y+1));
			}
			if(checkIfTileExists(new Point(curr.x-1, curr.y)) && !tiles[curr.x-1][curr.y].getIsOccupied() && tiles[curr.x-1][curr.y].getDistance() == 10000){
				tiles[curr.x-1][curr.y].setDistance(tiles[curr.x][curr.y].getDistance()+1);
				if(tiles[curr.x][curr.y].getDistance() < moves){
					tiles[curr.x-1][curr.y].setHighlight(Highlight.BLUE);
				}
				
				tilesLeft.remove(tiles[curr.x-1][curr.y]);
				tiles[curr.x-1][curr.y].setPreviousTile(tiles[curr.x][curr.y]);
				pointsToCheck.add(new Point(curr.x-1,curr.y));
			}
			if(checkIfTileExists(new Point(curr.x, curr.y-1)) && !tiles[curr.x][curr.y-1].getIsOccupied() && tiles[curr.x][curr.y-1].getDistance() == 10000){
				tiles[curr.x][curr.y-1].setDistance(tiles[curr.x][curr.y].getDistance()+1);
				if(tiles[curr.x][curr.y].getDistance() < moves){
					tiles[curr.x][curr.y-1].setHighlight(Highlight.BLUE);
				}
				
				tilesLeft.remove(tiles[curr.x][curr.y-1]);
				tiles[curr.x][curr.y-1].setPreviousTile(tiles[curr.x][curr.y]);
				pointsToCheck.add(new Point(curr.x,curr.y-1));
			}
			if(pointsToCheck.size() > 0){
				curr = pointsToCheck.get(0);
				pointsToCheck.remove(0);
			}
		}
		return buildDirectionsList(endLocation, beginning);
	}
	
	private List<Direction> buildDirectionsList(Point end, Point beginning){
		List<Direction> list = new ArrayList<Direction>();
		while(!end.equals(beginning)){
			if(checkIfTileExists(new Point(end.x+1 , end.y)) && !end.equals(beginning) && tiles[end.x][end.y].getPreviousTile().equals(tiles[end.x+1][end.y])){
				list.add(Direction.LEFT);
				end = new Point(end.x+1, end.y);
			}
			if(checkIfTileExists(new Point(end.x-1 , end.y)) && !end.equals(beginning) && tiles[end.x][end.y].getPreviousTile().equals(tiles[end.x-1][end.y])){
				list.add(Direction.RIGHT);
				end = new Point(end.x-1, end.y);
			}
			if(checkIfTileExists(new Point(end.x , end.y+1)) && !end.equals(beginning) && tiles[end.x][end.y].getPreviousTile().equals(tiles[end.x][end.y+1])){
				list.add(Direction.UP);
				end = new Point(end.x, end.y+1);
			}
			if(checkIfTileExists(new Point(end.x , end.y-1)) && !end.equals(beginning) && tiles[end.x][end.y].getPreviousTile().equals(tiles[end.x][end.y-1])){
				list.add(Direction.DOWN);
				end = new Point(end.x, end.y-1);
			}
		}
		return list;
	}
	
	/**
	 *TODO: When we have enemies and friends this method will need to take a Ship to know which side it's on so it can highlight appropriately.
	 * @param ship
	 */
	
	public void highlightPossibleMoves(Ship ship){
		if(ship.getMoves() >= 1){
			privateHelperForHighlightPossibleMoves(ship, new Point(ship.getLocation().x+1, ship.getLocation().y), ship.getMoves(), 1);
			privateHelperForHighlightPossibleMoves(ship, new Point(ship.getLocation().x-1, ship.getLocation().y), ship.getMoves(), 1);
			privateHelperForHighlightPossibleMoves(ship, new Point(ship.getLocation().x, ship.getLocation().y+1), ship.getMoves(), 1);
			privateHelperForHighlightPossibleMoves(ship, new Point(ship.getLocation().x, ship.getLocation().y-1), ship.getMoves(), 1);
		}
	}
	
	private void privateHelperForHighlightPossibleMoves(Ship ship, Point location, int numberOfMoves, int currentMoves){
		if(checkIfTileExists(location) && numberOfMoves >= currentMoves && !tiles[location.x][location.y].getIsOccupied()){
			tiles[location.x][location.y].setHighlight(Highlight.BLUE);
			privateHelperForHighlightPossibleMoves(ship, new Point(location.x+1, location.y), numberOfMoves, currentMoves + 1);
			privateHelperForHighlightPossibleMoves(ship, new Point(location.x-1, location.y), numberOfMoves, currentMoves + 1);
			privateHelperForHighlightPossibleMoves(ship, new Point(location.x, location.y+1), numberOfMoves, currentMoves + 1);
			privateHelperForHighlightPossibleMoves(ship, new Point(location.x, location.y-1), numberOfMoves, currentMoves + 1);
		}
	}
	
	private boolean checkIfTileExists(Point point){
		if(point.x >= 0 && point.x < tiles.length && point.y >= 0 && point.y < tiles[0].length){
			return true;
		}
		return false;
	}
	
	public void clearHighLights(){
		for(int r = 0; r < tiles.length; r++){
			for(int c = 0; c < tiles[r].length; c++){
				if(tiles[r][c].getHighlight() != Highlight.NONE){
					tiles[r][c].setHighlight(Highlight.NONE);
				}
			}
		}
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
	
	
	/**
	 * TODO: Fix toString.  Currently displays x as y and y as x.
	 */
	public String toString(){
		String result = "";
		for(int x = 0; x < tiles[0].length; x++){
			for(int y = 0; y < tiles.length; y++){
				result += tiles[y][x].toString();
			}
			result += "\n";
		}
		return result + "\n";
	}

}
