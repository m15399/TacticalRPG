package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import level_intros.Credits;
import level_intros.Mission1Intro;
import levels.*;
import utils.Observable;
import utils.Observer;
import view.AnimatedSprite;
import view.JetSprite;
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
	private List<Sprite> shipList = new ArrayList<Sprite>();
	private List<Sprite> secondShipList = new ArrayList<Sprite>();
	
	public TitleMenu(){
		int buttonWidth = 400;
		int buttonHeight = 50;
		int offsetX = 100;
		int offsetY = 430;
		int buttonNumber = 0;
		
		mapView = null;
		
		starfield = new Starfield(Game.WIDTH, Game.HEIGHT, 7, 100, 22, 27, 300);
		addChild(starfield);
		
		Sprite fighter = new Sprite("flipped_fighter.png", 1000, 30);
		Sprite fighter2 = new Sprite("flipped_fighter.png", 990, 60);
		Sprite fighter3 = new Sprite("flipped_fighter.png", 980, 90);
		Sprite fighter4 = new Sprite("flipped_fighter.png", 970, 120);
		Sprite fighter5 = new Sprite("flipped_fighter.png", 960, 150);
		Sprite fighter6 = new Sprite("flipped_fighter.png", 950, 180);
		Sprite fighter7 = new Sprite("flipped_fighter.png", 940, 210);
		Sprite fighter8 = new Sprite("flipped_fighter.png", 930, 240);
		Sprite fighter9 = new Sprite("flipped_fighter.png", 920, 270);
		Sprite fighter10 = new Sprite("flipped_fighter.png", 910, 300);
		Sprite fighter11 = new Sprite("flipped_fighter.png", 900, 330);
		Sprite fighter12 = new Sprite("flipped_fighter.png", 890, 360);
		addChild(fighter);
		addChild(fighter2);
		addChild(fighter3);
		addChild(fighter4);
		addChild(fighter5);
		addChild(fighter6);
		addChild(fighter7);
		addChild(fighter8);
		addChild(fighter9);
		addChild(fighter10);
		addChild(fighter11);
		addChild(fighter12);
		
		shipList.add(fighter);
		shipList.add(fighter2);
		shipList.add(fighter3);
		shipList.add(fighter4);
		shipList.add(fighter5);
		shipList.add(fighter6);
		shipList.add(fighter7);
		shipList.add(fighter8);
		shipList.add(fighter9);
		shipList.add(fighter10);
		shipList.add(fighter11);
		shipList.add(fighter12);
		
		Sprite rfighter = new Sprite("fighter.png", -2000, 30);
		Sprite rfighter2 = new Sprite("fighter.png", -1990, 60);
		Sprite rfighter3 = new Sprite("fighter.png", -1980, 90);
		Sprite rfighter4 = new Sprite("fighter.png", -1970, 120);
		Sprite rfighter5 = new Sprite("fighter.png", -1960, 150);
		Sprite rfighter6 = new Sprite("fighter.png", -1950, 180);
		Sprite rfighter7 = new Sprite("fighter.png", -1940, 210);
		Sprite rfighter8 = new Sprite("fighter.png", -1930, 240);
		Sprite rfighter9 = new Sprite("fighter.png", -1920, 270);
		Sprite rfighter10 = new Sprite("fighter.png", -1910, 300);
		Sprite rfighter11 = new Sprite("fighter.png", -1900, 330);
		Sprite rfighter12 = new Sprite("fighter.png", -1890, 360);
		
		addChild(rfighter);
		addChild(rfighter2);
		addChild(rfighter3);
		addChild(rfighter4);
		addChild(rfighter5);
		addChild(rfighter6);
		addChild(rfighter7);
		addChild(rfighter8);
		addChild(rfighter9);
		addChild(rfighter10);
		addChild(rfighter11);
		addChild(rfighter12);
		
		secondShipList.add(rfighter);
		secondShipList.add(rfighter2);
		secondShipList.add(rfighter3);
		secondShipList.add(rfighter4);
		secondShipList.add(rfighter5);
		secondShipList.add(rfighter6);
		secondShipList.add(rfighter7);
		secondShipList.add(rfighter8);
		secondShipList.add(rfighter9);
		secondShipList.add(rfighter10);
		secondShipList.add(rfighter11);
		secondShipList.add(rfighter12);
		
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

		
		
		Sprite sprite = new Sprite("title2.png", 500, 130);
		sprite.getPosition().setScale(2,2);
		addChild(sprite);	
		
		Sprite sprite3 = new Sprite("thegame.png", 500, 225);
		addChild(sprite3);
		
		
	}
	
	public void update(){
		starfield.scrollBy(0, 2);
	}
	
	public void draw(Graphics g){
		g.setFont(new Font("Arial", Font.BOLD, 72));
		g.setColor(Color.white);
		
		if(shipList.get(0).getPosition().getX() >= -2000){
			for(Sprite ship: shipList){
				ship.getPosition().moveBy(-5, 0);
			}
		}
		else{
			for(Sprite ship: shipList){
				ship.getPosition().moveBy(+3300, 0);
			}
		}
		
		if(secondShipList.get(0).getPosition().getX() <= 1000){
			for(Sprite ship: secondShipList){
				ship.getPosition().moveBy(5, 0);
			}
		}
		else{
			for(Sprite ship: secondShipList){
				ship.getPosition().moveBy(-3000, 0);
			}
		}
		
		
		

//		int t = 180;
		
//		g.drawString("Spaceballs", 320, t);
//		g.setFont(new Font("Arial", Font.BOLD, 48));
//		g.drawString("The Game", 388, t + 60);

	}
	
}
