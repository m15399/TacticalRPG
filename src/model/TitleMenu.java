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
import view.TitleMenuButton;

public class TitleMenu extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 680403353714047262L;
	
	public TitleMenu(){
		int buttonWidth = 400;
		int buttonHeight = 50;
		int offsetX = 100;
		int offsetY = 200;
		int buttonNumber = 0;
		
		File f = new File("save");
		if(f.exists() && !f.isDirectory()) { 
			addChild(new TitleMenuButton("Continue", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
				public void notified(Observable sender){
					Game.getInstance().loadGame();
				}
			}));
			buttonNumber++;
		}
		
		
		addChild(new TitleMenuButton("GraphicsTest", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new GraphicsTestLevel());
			}
		}));
		buttonNumber++;

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
		
		addChild(new TitleMenuButton("Campaign", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new Mission1Intro());
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("Multiplayer Devin", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new MultiplayerLevel(0));
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("Multiplayer Ethan", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new MultiplayerLevel(1));
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("Multiplayer Luis", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new MultiplayerLevel(2));
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("Multiplayer Mark", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new MultiplayerLevel(3));
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("Credits", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				Game.getInstance().transitionTo(new Credits());
			}
		}));
		buttonNumber++;
		
		
		
		
		
	}
	
	
	public void draw(Graphics g){
		g.setFont(new Font("Arial", Font.BOLD, 72));
		g.setColor(Color.white);
		g.drawString("Title", 400, 200);
	}
	
}
