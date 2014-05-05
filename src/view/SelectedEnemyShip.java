
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import resources.ImageLibrary;
import model.Game;
import model.GameObject;
import model.Item;
import model.Ship;

public class SelectedEnemyShip extends GameObject {

	static final int WIDTH = 210;
	static final int HEIGHT = 325;
	
	private int x, y;
	private double displayHealth;

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
	
	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public void draw(Graphics g) {
		if(currentShip == null)
			return;

		// I added these offsets to all the coordinates so that we can move it
		// around more easily
		int offsetX = x;
		int offsetY = y;

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
		g2.drawString("Hull:            " + currentShip.getHull() + " / " + currentShip.getMaxHull(),
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
		ImageLibrary images = ImageLibrary.getInstance();
		BufferedImage scrapMetal = images.getImage("scrapmetal.png");
		BufferedImage magneticShield = images.getImage("MagneticShield.png");
		BufferedImage speedBoost = images.getImage("speedboost.png");
		g.drawImage(speedBoost, leftColumn+45, offsetY+240, null);
		g.drawImage(scrapMetal, leftColumn+70, offsetY+232, null);
		g.drawImage(magneticShield, leftColumn+110, offsetY+225, null);
		
		ArrayList<Item> itemList = (ArrayList<Item>) currentShip.getItemsList();
		int counterSB = 0;
		int counterMS = 0;
		int counterSM = 0;
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
		for(int i=0; i<itemList.size(); i++){
			if(itemList.get(i).getName().equals("Speed Boost")){
				counterSB++;
			}
			else if(itemList.get(i).getName().equals("Magnetic Shield")){
				counterMS++;
			}
			else if(itemList.get(i).getName().equals("Scrap Metal")){
				counterSM++;
			}
		}


			g2.drawString("x" + counterSB, leftColumn+60, offsetY+259);
			g2.drawString("x" + counterMS, leftColumn+140, offsetY+259);
			g2.drawString("x" + counterSM, offsetX+120, offsetY+259);
			
			
			// health bar

			g2.setStroke(new BasicStroke(0));

			// size
			int height = 15;
			int width = 150;
			// position
			int x = leftColumn;
			int y = offsetY+275;


			// border
			g.setColor(Color.white);
			g.drawRect(x-1, y-1, width+2,
					height+2);

			// color
			Color red = new Color(1.0f, .25f, .15f);
			Color green = new Color(0f, .8f, .0f);
			g2.setColor(green);
			displayHealth = currentShip.getHull();

			g.fillRect(x, y,
					(int) (width * displayHealth / currentShip.getMaxHull()) + 1,
					height + 1);
		
		
		
		
	
	}
}
