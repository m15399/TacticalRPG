package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.Game;
import model.GameObject;
import model.Item;
import model.Popup;
import terrains.SpaceWreckageTerrain;

public class TerrainOrItemInfoGraphic extends GameObject{

	private static final long serialVersionUID = 4697017267822991397L;
	private static final int WIDTH = 150;
	private static final int HEIGHT = 40;
	
	private int x, y;

	private Popup currentItemOrTerrain;

	public TerrainOrItemInfoGraphic(){
		currentItemOrTerrain = null;
	}
	
	public TerrainOrItemInfoGraphic(Popup hoveredTerrain) {
		currentItemOrTerrain = hoveredTerrain;
	}
	
	public void setTerrainOrItem(Popup hoveredTerrain){
		currentItemOrTerrain = hoveredTerrain;
	}
	
	public void setLocation(int x, int y){
		x += 16;
		y += 16;
		
		int maxX = Game.WIDTH - WIDTH - 5;
		int maxY = Game.HEIGHT - HEIGHT - 27;
		int minY = 5;
		
		if(x > maxX)
			x = maxX;
		if(y > maxY)
			y = maxY;
		else if (y < minY)
			y = minY;
		
		this.x = x;
		this.y = y;
	}
	
	private void drawString(Graphics2D g2, String text, int x, int y) {
		for (String line : text.split("\n"))
			g2.drawString(line, x, y += g2.getFontMetrics().getHeight());
	}

	public void draw(Graphics g) {
		if(currentItemOrTerrain == null)
			return;
		
		// I added these offsets to all the coordinates so that we can move it
		// around more easily
		int offsetX = x;
		int offsetY = y;

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		// Draw a black rect first as a background
		g2.setColor(Color.black);
		if(currentItemOrTerrain instanceof SpaceWreckageTerrain){
			int moreOffsetX = 30;
			int moreOffsetY = 43;
			g2.fillRect(offsetX, offsetY, WIDTH + moreOffsetX, HEIGHT + moreOffsetY);
			// Draw a border (I just put the top right half for now)
			g2.setColor(Color.white);
			g2.drawLine(offsetX, offsetY, WIDTH + offsetX + moreOffsetX, offsetY);
			g2.drawLine(WIDTH + offsetX + moreOffsetX, offsetY, WIDTH + offsetX + moreOffsetX, HEIGHT + offsetY + moreOffsetY);
			g2.drawLine(offsetX, offsetY, offsetX, offsetY + HEIGHT + moreOffsetY);
			g2.drawLine(offsetX, offsetY + HEIGHT + moreOffsetY, WIDTH + offsetX + moreOffsetX, HEIGHT + offsetY + moreOffsetY);
		}
		else if(currentItemOrTerrain instanceof Item){
			int moreOffsetX = -10;
			int moreOffsetY = 13;
			g2.fillRect(offsetX, offsetY, WIDTH + moreOffsetX, HEIGHT + moreOffsetY);
			// Draw a border (I just put the top right half for now)
			g2.setColor(Color.white);
			g2.drawLine(offsetX, offsetY, WIDTH + offsetX + moreOffsetX, offsetY);
			g2.drawLine(WIDTH + offsetX + moreOffsetX, offsetY, WIDTH + offsetX + moreOffsetX, HEIGHT + offsetY + moreOffsetY);
			g2.drawLine(offsetX, offsetY, offsetX, offsetY + HEIGHT + moreOffsetY);
			g2.drawLine(offsetX, offsetY + HEIGHT + moreOffsetY, WIDTH + offsetX + moreOffsetX, HEIGHT + offsetY + moreOffsetY);
		}
		else{
			g2.fillRect(offsetX, offsetY, WIDTH, HEIGHT);
			// Draw a border (I just put the top right half for now)
			g2.setColor(Color.white);
			g2.drawLine(offsetX, offsetY, WIDTH+offsetX, offsetY);
			g2.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, HEIGHT+offsetY);
			g2.drawLine(offsetX, offsetY, offsetX, offsetY+HEIGHT);
			g2.drawLine(offsetX, offsetY+HEIGHT, WIDTH + offsetX, HEIGHT+offsetY);
		}
		
		// Draw message
		Font font = g2.getFont();
		float titleFontSize = 12.0f;
		g2.setFont(font.deriveFont(titleFontSize));
		drawString(g2, currentItemOrTerrain.getName(), offsetX + 5, offsetY);
//		float normalFontSize = 10.0f;
//		g2.setFont(font.deriveFont(normalFontSize));
		//g2.setFont(font);
		drawString(g2, currentItemOrTerrain.getDescription(), offsetX + 5, offsetY + 18);
	}
}
