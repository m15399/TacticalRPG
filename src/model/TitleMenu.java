package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;

import level_intros.Credits;
import level_intros.Mission1Intro;
import levels.*;
import utils.Observable;
import utils.Observer;
import view.MultiplayerMapSelectionView;
import view.Starfield;
import view.TitleMenuButton;

public class TitleMenu extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 680403353714047262L;
	
	private MultiplayerMapSelectionView mapView;
	private Starfield starfield;
	
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

		
		
		
		
		
	}
	
	public void update(){
		starfield.scrollBy(0, 2);
	}
	
	public void draw(Graphics g){
		g.setFont(new Font("Arial", Font.BOLD, 72));
		g.setColor(Color.white);
		
		int t = 180;
		
		g.drawString("Spaceballs", 320, t);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("The Game", 388, t + 60);

	}
	
}
