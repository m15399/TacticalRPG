package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import specific_ships_items.BattleCruiser;
import specific_ships_items.Bomber;
import specific_ships_items.Fighter;
import specific_ships_items.Mothership;
import specific_ships_items.RepairShip;
import specific_ships_items.Scout;
import specific_terrains.AsteroidTerrain;
import specific_terrains.PlanetTerrain;

public class BuildTileMapFromTextFile {
	
	private Tile[][] tiles;
	
	private FileReader reader;
	private Scanner in;
	
	public BuildTileMapFromTextFile(String newFileName){
		File file = new File(newFileName);
		openFile(file);
		readFile();
		closeFile();
	}
	
	private void openFile(File file){
		try{
			if(!file.exists()){
				throw new FileNotFoundException();
			}
			if(!file.canRead() || !file.isFile()){
				throw new IOException();
			}
			reader = new FileReader(file);
			in = new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println("Could not find the file.");
			e.printStackTrace();
		}
		catch(IOException g){
			System.out.println("Some other error happened opening the file.");
			g.printStackTrace();
		}
	}
	
	private void readFile(){
		String levelInfo = "";
		int levelWidth = 0;
		int levelHeight = 0;
		while(in.hasNextLine()){
//			if(!levelInfo.equals("")){
//				levelInfo += ",";
//			}
			levelInfo += in.nextLine();
			levelHeight++;
			if(levelWidth == 0){
				levelWidth = levelInfo.length();
			}
		}
		processData(levelInfo, levelWidth, levelHeight);
	}
	
	/*
	 * Breakdown of what letters mean.  Please don't delete commented println's since they can be used to debug in the future.
	 * S = Scout
	 * F = Fighter
	 * B = Bomber
	 * C = Cruiser
	 * M = Mothership
	 * E = Engineer
	 * X = Sniper
	 * A = Asteroid
	 * P = Planet
	 * p = Planet (used for extending planet graphic beyond 1 tile)
	 * 0 = Unoccupied Tile
	 * 1 = Player spawn point
	 */
	
	private void processData(String levelInfo, int levelWidth, int levelHeight){
		//System.out.println(levelWidth + " " + levelHeight);
		//System.out.println(levelInfo);
		tiles = new Tile[levelWidth][levelHeight];
		int counter = 0;
		for(int r = 0; r < levelHeight; r++){
			for(int c = 0; c < levelWidth; c++){
				//System.out.println("[" + r + "," + c + "]");
				char temp = levelInfo.charAt(counter);
				if(temp == 'S'){
					Scout ship = new Scout(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'F'){
					Fighter ship = new Fighter(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'B'){
					Bomber ship = new Bomber(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'C'){
					BattleCruiser ship = new BattleCruiser(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'M'){
					Mothership ship = new Mothership(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'E'){
					RepairShip ship = new RepairShip(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
//				else if(temp == 'X'){
//					Sniper ship = new Sniper(new Point(c, r));
//					tiles[c][r] = new Tile(ship);
//				}
				else if(temp == 'A'){
					AsteroidTerrain terrain = new AsteroidTerrain(new Point(c, r));
					tiles[c][r] = new Tile(true, terrain);
				}
				else if(temp == 'P'){
					PlanetTerrain terrain = new PlanetTerrain(new Point(c, r));
					tiles[c][r] = new Tile(false, terrain);
				}
				else if(temp == 'p'){
					tiles[c][r] = new Tile(true);
				}
				else if(temp == '0'){
					tiles[c][r] = new Tile(c, r);
				}
				else{
					System.out.println("Error processing a character.  Set as empty tile.");
					tiles[c][r] = new Tile(c, r);
				}
				counter++;
			}
		}
	}
	
	private void closeFile(){
		try {
			in.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public String toString(){
		String result = "";
		for(int r = 0; r < tiles[0].length; r++){
			for(int c = 0; c < tiles.length; c++){
				result += tiles[c][r].toString();
			}
			result += "\n";
		}
		return result;
	}
}
