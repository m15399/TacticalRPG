package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import model.Game;
import model.GameObject;

public class EndOfLevelGraphic extends GameObject {

	public enum WinnerType {
		SINGLEPLAYER, PLAYER1, PLAYER2, ENEMY;
	}
	
	private WinnerType winnerType;
	
	public EndOfLevelGraphic(WinnerType winnerType){
		this.winnerType = winnerType;
	}
	
	public void draw(Graphics g){
		
		g.setColor(Color.white);
		
		Font f = new Font("Arial", Font.BOLD, 72);
		g.setFont(f);		

		String s = "";
		
		switch(winnerType){
		case SINGLEPLAYER:
			s = "Mission Complete!";
			break;
		case PLAYER1:
			s = "Blue Wins!";
			break;
		case PLAYER2:
			s = "Red Wins!";
			break;
		case ENEMY:
			s = "Mission Failed!";
			break;
			
		}
		
		int width = g.getFontMetrics().stringWidth(s);

		g.drawString(s, Game.WIDTH/2 - width/2, Game.HEIGHT/2);
		
	}
	
}
