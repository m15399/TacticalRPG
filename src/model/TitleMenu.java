package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import levels.*;
import songPlayer.SongPlayer;
import utils.Observable;
import utils.Observer;
import view.TitleMenuButton;

public class TitleMenu extends GameObject {

	private Game game;
	
	public TitleMenu(Game newGame){
		this.game = newGame;
		SongPlayer.playFile("./songFiles/space_clips.mp3");
		int buttonWidth = 400;
		int buttonHeight = 50;
		int offsetX = 100;
		int offsetY = 500;
		int buttonNumber = 0;
		
		addChild(new TitleMenuButton("GraphicsTest", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				game.transitionTo(new GraphicsTestLevel());
			}
		}));
		buttonNumber++;

		addChild(new TitleMenuButton("TestLevel", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				game.transitionTo(new TestLevel(game));
			}
		}));
		buttonNumber++;
		
		addChild(new TitleMenuButton("TestLevelFromFile", offsetX, offsetY+buttonHeight*buttonNumber, buttonWidth, new Observer(){
			public void notified(Observable sender){
				game.transitionTo(new TestLevelFromFile(game));
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
