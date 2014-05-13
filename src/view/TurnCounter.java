package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.GameObject;

public class TurnCounter extends GameObject{

	private static final long serialVersionUID = 4697017267822991397L;
	private static final int WIDTH = 150;
	private static final int HEIGHT = 50;
	
	private int turns;
	
	public TurnCounter(){
		turns = 0;
	}
	
	public TurnCounter(int turns) {
		this.turns = turns;
	}
	
	public void updateTurns(int amountToAdjustBy){
		turns += amountToAdjustBy;
	}
	
	public void incrementTurns(){
		turns += 1;
	}
	
	public void decrementTurns(){
		turns -= 1;
	}
	
	public void setTurns(int newTurnNumber){
		turns = newTurnNumber;
	}
	
	public void resetTurnsToZero(){
		turns = 0;
	}
	
	private void drawString(Graphics2D g2, String text, int x, int y) {
		for (String line : text.split("\n"))
			g2.drawString(line, x, y += g2.getFontMetrics().getHeight());
	}

	public void draw(Graphics g) {
		// I added these offsets to all the coordinates so that we can move it
		// around more easily
		int offsetX = 500;
		int offsetY = 0;

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		// Draw a black rect first as a background
		g2.setColor(Color.black);

		g2.fillRect(offsetX, offsetY, WIDTH, HEIGHT);
		
		// Draw a border (I just put the top right half for now)
		g2.setColor(Color.white);
		g2.drawLine(offsetX, offsetY, WIDTH+offsetX, offsetY);
		g2.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, HEIGHT+offsetY);
		g2.drawLine(offsetX, offsetY, offsetX, offsetY+HEIGHT);
		g2.drawLine(offsetX, offsetY+HEIGHT, WIDTH + offsetX, HEIGHT+offsetY);
		
		// Draw message
		Font font = g2.getFont();
		float titleFontSize = 18.0f;
		g2.setFont(font.deriveFont(titleFontSize));
		drawString(g2, "Turn Counter", offsetX + 20, offsetY);
		drawString(g2, turns + "", offsetX + 65, offsetY + 20);
	}
}

