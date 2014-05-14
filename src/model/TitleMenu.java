package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import level_intros.Credits;
import level_intros.Mission1Intro;
import levels.*;
import shipVisuals.FighterVisual;
import specific_ships_items.Fighter;
import utils.Observable;
import utils.Observer;
import view.MultiplayerMapSelectionView;
import view.Sprite;
import view.Starfield;
import view.TitleMenuButton;

public class TitleMenu extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 680403353714047262L;
	
	private MultiplayerMapSelectionView mapView;
	private Starfield starfield;
	private List<FighterVisual> shipList;
	private List<FighterVisual> secondShipList;
	
	public TitleMenu(){
		int buttonWidth = 400;
		int buttonHeight = 50;
		int offsetX = 100;
		int offsetY = 430;
		int buttonNumber = 0;
		
		mapView = null;
		
		starfield = new Starfield(Game.WIDTH, Game.HEIGHT, 7, 100, 22, 27, 300);
		addChild(starfield);
		
		if(Game.DEBUG){
			buttonNumber -= 2;
			addChild(new TitleMenuButton("TestLevel", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
				public void notified(Observable sender){
					Game.getInstance().transitionTo(new TestLevel());
				}
			}));
			buttonNumber++;
			
			addChild(new TitleMenuButton("TestLevelFromFile", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
				public void notified(Observable sender){
					Game.getInstance().transitionTo(new TestLevelFromFile());
				}
			}));
			buttonNumber++;
		}
		
		File f = new File("save");
		if(f.exists() && !f.isDirectory()) { 
			addChild(new TitleMenuButton("Continue", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
				public void notified(Observable sender){
					Game.getInstance().loadGame();
				}
			}));
			buttonNumber++;
		} else {
			buttonNumber++;
		}
		
		addChild(new TitleMenuButton("New Campaign", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new Mission1Intro());
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("Multiplayer", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				if(mapView == null){
					mapView = new MultiplayerMapSelectionView();
					addChild(mapView);
				} else{
					mapView.destroy();
					mapView = null;
				}
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("Credits", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new Credits());
			}
		}));
		buttonNumber++;

		addChild(new TitleMenuButton("Quit", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				System.exit(0);
			}
		}));
		buttonNumber++;

		
		shipList = new ArrayList<FighterVisual>();
		secondShipList = new ArrayList<FighterVisual>();
		
		int x = 1200;
		int y = 30;
		for(int i = 0; i < 12; i++){
			Ship ship = new Fighter(new Point(0,0));
			ship.setIsTargetable(false);
			FighterVisual fighter = new FighterVisual(ship);
			fighter.getPosition().setLocation(x, y);
			fighter.getPosition().mirror();
			fighter.playMoveAnimation();
			x -= 15;
			y += 30;
			addChild(fighter);
			shipList.add(fighter);
		}
		
		x = -200;
		y = 30;
		
		for(int i = 0; i < 12; i++){
			Ship ship = new Fighter(new Point(0,0));
			ship.setIsTargetable(false);
			FighterVisual fighter = new FighterVisual(ship);
			fighter.getPosition().setLocation(x, y);
//			fighter.getPosition().mirror();			
			fighter.playMoveAnimation();
			x += 15;
			y += 30;
			addChild(fighter);
			secondShipList.add(fighter);
		}
		
		
		
		Sprite sprite3 = new Sprite("thegame.png", 500, 225);
		addChild(sprite3);
		
		Sprite sprite = new Sprite("title2.png", 500, 130);
		sprite.getPosition().setScale(2,2);
		addChild(sprite);
//		Sprite sprite2 = new Sprite("shiptitlemenu2.png", 700, 500);
//		addChild(sprite2);
		
	}
	
	public void update(){
		starfield.scrollBy(0, 2);
		
		int minX = -200;
		int maxX = 1200;
		int speed = 5;
		
		if(shipList.get(0).getPosition().getX() >= minX){
			for(FighterVisual ship: shipList){
				ship.getPosition().moveBy(-speed, 0);
			}
		}
		else{
			for(FighterVisual ship: shipList){
				ship.getPosition().moveBy(maxX*1.5, 0);
			}
		}
		
		if(secondShipList.get(0).getPosition().getX() <= maxX){
			for(FighterVisual ship: secondShipList){
				ship.getPosition().moveBy(speed, 0);
			}
		}
		else{
			for(FighterVisual ship: secondShipList){
				ship.getPosition().moveBy(-maxX*1.5, 0);
			}
		}
	}
	
	public void draw(Graphics g){
		g.setFont(new Font("Arial", Font.BOLD, 72));
		g.setColor(Color.white);
		
		
		
		
		

//		int t = 180;
		
//		g.drawString("Spaceballs", 320, t);
//		g.setFont(new Font("Arial", Font.BOLD, 48));
//		g.drawString("The Game", 388, t + 60);

	}
	
}
