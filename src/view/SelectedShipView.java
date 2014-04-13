package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.Game;
import model.GameObject;
import model.Ship;

public class SelectedShipView extends GameObject {

	static final int WIDTH = 500;
	static final int HEIGHT = 180;

	private Ship currentShip;

	public SelectedShipView(Ship selectedShip) {
		currentShip = selectedShip;
	}

	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public void draw(Graphics g) {

		// I added these offsets to all the coordinates so that we can move it
		// around more easily
		int offsetX = 0;
		int offsetY = Game.HEIGHT - HEIGHT;

		// Draw a black rect first
		g.setColor(Color.black);
		g.fillRect(offsetX, offsetY, offsetX + WIDTH, offsetY + HEIGHT);

		// Draw a border (I just put the top right half for now)
		g.setColor(Color.white);
		g.drawLine(offsetX, offsetY, WIDTH + offsetX, offsetY);
		g.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, HEIGHT + offsetY);

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g2.setColor(Color.white);
		String shipName = "Scout";
		g2.drawString(shipName, 186 + offsetX, 40 + offsetY);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		String shipDescription = "Mobile scouting ship,\ngenerates and places\nmines.";
		drawString(g2, shipDescription, 186 + offsetX, 45 + offsetY);
		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.drawString("Stats", 333 + offsetX, 30 + offsetY);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		g2.drawString("Hull:                 " + currentShip.getHull(),
				333 + offsetX, 50 + offsetY);
		g2.drawString("Shields:          " + currentShip.getMaxShielding(),
				333 + offsetX, 65 + offsetY);
		// need ship.getdmg();
		g2.drawString("Damage:          " + "30-34", 
				333 + offsetX, 80 + offsetY);
		g2.drawString("Accuracy:          " + currentShip.getAccuracy(),
				333 + offsetX, 95 + offsetY);
		// need ship.getCrit()
		g2.drawString("Crit Chance:          " + "15%", 
				333 + offsetX, 110 + offsetY);
		g2.drawString("Speed:          " + currentShip.getMoves(),
				333 + offsetX, 125 + offsetY);
		g2.drawString("Items:", 333 + offsetX, 150 + offsetY);
	}

}
