package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import specific_ships_items.AISpawner;
import specific_ships_items.BattleCruiser;
import specific_ships_items.Bomber;
import specific_ships_items.Fighter;
import specific_ships_items.MagneticShield;
import specific_ships_items.MineShip;
import specific_ships_items.Mothership;
import specific_ships_items.RepairShip;
import specific_ships_items.Scout;
import specific_ships_items.ScrapMetal;
import specific_ships_items.Sniper;
import specific_ships_items.Speedboost;
import specific_ships_items.Turret;
import specific_ships_items.WarpGateShip;
import terrains.AsteroidFieldTerrain;
import terrains.AsteroidTerrain;
import terrains.GasCloudTerrain;
import terrains.PlanetTerrain2x2;
import terrains.RadioactiveTerrain;
import terrains.SpaceWallTerrain;
import terrains.SpaceWreckageTerrain;
import terrains.SpaceWreckageTerrain1x3;

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
		//System.out.println("levelWidth = " + levelWidth + " levelHeight = " + levelHeight);
	}
	
	/*
	 * Breakdown of what letters mean.  
	 * Please don't delete commented println's since they can be used to debug in the future.
	 * Ships:
	 * S = Scout
	 * F = Fighter
	 * B = Bomber
	 * C = BattleCruiser
	 * M = Mothership
	 * t = Mothership turret
	 * E = RepairShip
	 * X = Sniper
	 * W = WarpGateShip
	 * 2 = WarpGateShip (player 2)
	 * * = AISpawner (enemy warpgate)
	 * m = MineShip
	 * 
	 * Terrain:
	 * A = Asteroid
	 * P = Planet key
	 * p = spaces planet bleeds over into
	 * w = SpaceWreckageTerrain
	 * <, >, ^, v, -, | = SpaceWallTerrain
	 * f = AsteroidFieldTerrain
	 * G = GasCloudTerrain (horizontal) (Beginning of graphic) Graphic is three Tiles wide, so map should read... Ggg
	 * g = GasCloudTerrain (horizontal) (Additional graphic tiles)
	 * Z = RadioactiveTerrain (vertical) (Beginning of graphic) Graphic is three Tiles tall, so map should read... Zzz vertically
	 * z = RadioactiveTerrain (vertical) (Additional graphic tiles)
	 *
	 * this spacewreckage acts as a wall unlike previous wreckage
	 * N = SpaceWreckageTerrain1x3 (horizontal) (Beginning of graphic) Graphic is three Tiles wide, so map should read... Nnn
	 * n = SpaceWreckageTerrain1x3 (horizontal) (Additional graphic tiles)
	 * 
	 * Items:
	 * s = MagneticShield
	 * c = ScrapMetal
	 * x = Speedboost
	 * 
	 * Please update this if you make changes
	 * Remaining Letters and numbers
	 * DHIJKLNOQRTUVY
	 * abdehijklnoqrvuy
	 * 13456789
	 * 
	 * 0 = Default Unoccupied Tile
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
				//Ships
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
				else if(temp == 't'){
					Turret ship = new Turret(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'E'){
					RepairShip ship = new RepairShip(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'X'){
					Sniper ship = new Sniper(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'W'){
					WarpGateShip ship = new WarpGateShip(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == '2'){
					WarpGateShip ship = new WarpGateShip(new Point(c, r));
					ship.setTeam(1);
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == '*'){
					AISpawner ship = new AISpawner(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				else if(temp == 'm'){
					MineShip ship = new MineShip(new Point(c, r));
					tiles[c][r] = new Tile(ship);
				}
				//Terrain
				else if(temp == 'A'){
					AsteroidTerrain terrain = new AsteroidTerrain(new Point(c, r));
					tiles[c][r] = new Tile(false, terrain);
				}
				else if(temp == 'P'){
					PlanetTerrain2x2 terrain = new PlanetTerrain2x2(new Point(c, r));
					tiles[c][r] = new Tile(false, terrain);
				}
				else if(temp == 'p'){
					tiles[c][r] = new Tile(false, c, r);
				}
				else if(temp == 'w'){
					SpaceWreckageTerrain terrain = new SpaceWreckageTerrain(new Point(c, r));
					tiles[c][r] = new Tile(true, terrain);
				}
				else if(temp == 'f'){
					AsteroidFieldTerrain terrain = new AsteroidFieldTerrain(new Point(c, r));
					tiles[c][r] = new Tile(true, terrain);
				}
				else if(temp == 'G'){
					GasCloudTerrain terrain = new GasCloudTerrain(new Point(c, r), true);
					tiles[c][r] = new Tile(true, terrain);
				}
				else if(temp == 'g'){
					GasCloudTerrain terrain = new GasCloudTerrain(new Point(c, r), false);
					tiles[c][r] = new Tile(true, terrain);
				}
				else if(temp == 'Z'){
					RadioactiveTerrain terrain = new RadioactiveTerrain(new Point(c, r), true);
					tiles[c][r] = new Tile(true, terrain);
				}
				else if(temp == 'z'){
					RadioactiveTerrain terrain = new RadioactiveTerrain(new Point(c, r), false);
					tiles[c][r] = new Tile(true, terrain);
				}
				else if(temp == 'N'){
					SpaceWreckageTerrain1x3 terrain = new SpaceWreckageTerrain1x3(new Point(c, r), true);
					tiles[c][r] = new Tile(false, terrain);
				}
				else if(temp == 'n'){
					SpaceWreckageTerrain1x3 terrain = new SpaceWreckageTerrain1x3(new Point(c, r), false);
					tiles[c][r] = new Tile(false, terrain);
				}
				else if(temp == '<' || temp ==  '>' || temp ==  '^' || temp ==  'v' || temp ==  '-' || temp ==  '|'){
					SpaceWallTerrain terrain = new SpaceWallTerrain(new Point(c, r));
					switch(temp){
						case 'v': terrain.setFilename("wall_bottom.png");
						  break;
						case '-': terrain.setFilename("wall_horizontal.png");
						  break;
						case '<': terrain.setFilename("wall_left.png");
						  break;
						case '>': terrain.setFilename("wall_right.png");
						  break;
						case '^': terrain.setFilename("wall_top.png");
						  break;
						case '|': terrain.setFilename("wall_vertical.png");
						  break;
						default: System.out.println("There was an error");
					}
					terrain.setVisual();
					tiles[c][r] = new Tile(false, terrain);
				}
				//Items
				else if(temp == 'x'){
					tiles[c][r] = new Tile(c, r);
					tiles[c][r].getItems().add(new Speedboost());
				}
				else if(temp == 's'){
					tiles[c][r] = new Tile(c, r);
					tiles[c][r].getItems().add(new MagneticShield());
				}
				else if(temp == 'c'){
					tiles[c][r] = new Tile(c, r);
					tiles[c][r].getItems().add(new ScrapMetal());
				}
				//Default Tile
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
