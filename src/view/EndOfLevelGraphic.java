package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import actions.TimerAction;

import utils.*;

import model.Game;
import model.GameObject;

public class EndOfLevelGraphic extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017947871279307544L;

	public enum WinnerType {
		SINGLEPLAYER, PLAYER1, PLAYER2, ENEMY, STARTBLUETURN, STARTREDTURN;
	}
	
	private WinnerType winnerType;
	private String reason;
	
	public EndOfLevelGraphic(WinnerType winnerType, String reason){
		this.winnerType = winnerType;
		this.reason = reason;
	}
	
	public EndOfLevelGraphic(WinnerType winnerType, String reason, int duration){
		this.winnerType = winnerType;
		
		this.reason = reason;
		
		TimerAction timer = new TimerAction(duration, new Observer(){
			public void notified(Observable sender){
				destroy();
			}
		});
		addChild(timer);
		timer.start();
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
		case STARTBLUETURN:
			s = "BLUE'S TURN";
			break;
		case STARTREDTURN:
			s = "RED'S TURN";
			break;
			
		}
		
		int width = g.getFontMetrics().stringWidth(s);

		g.drawString(s, Game.WIDTH/2 - width/2, Game.HEIGHT/2-30);
		
		g.setFont(new Font("Arial", Font.BOLD, 48));
		
		int width2 = g.getFontMetrics().stringWidth(reason);
		
		g.drawString(reason, Game.WIDTH/2 - width2/2, Game.HEIGHT/2+ 40);

		
		
	}
	
}
