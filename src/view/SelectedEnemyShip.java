
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.GameObject;
import model.Ship;

public class SelectedEnemyShip extends GameObject {

	static final int WIDTH = 210;
	static final int HEIGHT = 325;
	
	private int x, y;

	private Ship currentShip;

	public SelectedEnemyShip(){
		currentShip = null;
	}
	
	public SelectedEnemyShip(Ship selectedShip) {
		currentShip = selectedShip;
	}
	
	public void setShip(Ship ship){
		currentShip = ship;
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public void draw(Graphics g) {
		if(currentShip == null)
			return;

		// I added these offsets to all the coordinates so that we can move it
		// around more easily
		int offsetX = x + 16;
		int offsetY = y + 16;

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

		
		int leftColumn = offsetX+20;
		
		g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g2.setColor(Color.white);
		String shipName = currentShip.getName();
		g2.drawString(shipName, leftColumn, 40 + offsetY);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		drawString(g2, currentShip.getDescription(), leftColumn, 45 + offsetY);
		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.drawString("Stats", leftColumn, 120 + offsetY);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		g2.drawString("Hull:                 " + currentShip.getHull(),
				leftColumn, 145 + offsetY);
		g2.drawString("Shields:          " + currentShip.getShielding() + "%",
				leftColumn, 160 + offsetY);
		// need ship.getdmg();
		g2.drawString("Damage:          " + currentShip.getMinDamage() + "-" + currentShip.getMaxDamage(), 
				leftColumn, 175 + offsetY);
		g2.drawString("Accuracy:          " + currentShip.getAccuracy() + "%",
				leftColumn, 190 + offsetY);
		// need ship.getCrit()
		g2.drawString("Crit Chance:          " + currentShip.getCritChance() + "%", 
				leftColumn, 205 + offsetY);
		g2.drawString("Speed:          " + currentShip.getMoves(),
				leftColumn, 220 + offsetY);
		g2.drawString("Items:", leftColumn, 250 + offsetY);
		
		g2.setStroke(new BasicStroke());
	}
}
