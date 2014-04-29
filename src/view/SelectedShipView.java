package view;

import input.Button;
import input.Input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import resources.ImageLibrary;
import model.Game;
import model.GameObject;
import model.Item;
import model.Ship;

public class SelectedShipView extends GameObject {

	static final int WIDTH = 500;
	static final int HEIGHT = 180;

	private Ship currentShip;
	private Button button;

	public SelectedShipView(){
		currentShip = null;
		button = new Button(0, Game.HEIGHT - HEIGHT, WIDTH, HEIGHT);
		Input.getInstance().addButton(button);
	}
	
	public void setShip(Ship ship){
		currentShip = ship;
	}
	
	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	public void update(){
		if(currentShip == null){
			button.disable();
		} else {
			button.enable();
		}
	}

	public void draw(Graphics g) {
		if(currentShip == null)
			return;

		// I added these offsets to all the coordinates so that we can move it
		// around more easily
		int offsetX = 0;
		int offsetY = Game.HEIGHT - HEIGHT;

		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		// Draw a black rect first as a background
		
		g2.setColor(Color.black);
		g2.fillRect(offsetX, offsetY, WIDTH, HEIGHT);

		// Draw a border (I just put the top right half for now)
		g2.setColor(Color.white);
		g2.drawLine(offsetX, offsetY, WIDTH + offsetX, offsetY);
		g2.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, HEIGHT + offsetY);

		
		int leftColumn = 152;
	
		g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g2.setColor(Color.white);
		String shipName = currentShip.getName();
		g2.drawString(shipName, leftColumn + offsetX, 40 + offsetY);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		drawString(g2, currentShip.getDescription(), leftColumn + offsetX, 45 + offsetY);
		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.drawString("Stats", 333 + offsetX, 30 + offsetY);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		g2.drawString("Hull:            " + currentShip.getHull() + " / " + currentShip.getMaxHull(),
				333 + offsetX, 50 + offsetY);
		g2.drawString("Shields:          " + currentShip.getShielding() + "%",
				333 + offsetX, 65 + offsetY);
		// need ship.getdmg();
		g2.drawString("Damage:          " + currentShip.getMinDamage() + "-" + currentShip.getMaxDamage(), 
				333 + offsetX, 80 + offsetY);
		g2.drawString("Accuracy:          " + currentShip.getAccuracy() + "%",
				333 + offsetX, 95 + offsetY);
		// need ship.getCrit()
		g2.drawString("Crit Chance:          " + currentShip.getCritChance() + "%", 
				333 + offsetX, 110 + offsetY);
		g2.drawString("Speed:          " + currentShip.getMoves(),
				333 + offsetX, 125 + offsetY);
		g2.drawString("Items:", 333 + offsetX, 150 + offsetY);
		
		g2.setStroke(new BasicStroke());
		ImageLibrary images = ImageLibrary.getInstance();
//		BufferedImage img = images.getImage(currentShip.getName().toLowerCase() + ".png");
//		g.drawImage(img, offsetX+40, offsetY + 50, null);
		BufferedImage img = images.getImage("scout.png");
		g.drawImage(img, offsetX+40, offsetY + 50, null);
		BufferedImage item = images.getImage("speedboost.png");
		g.drawImage(item, offsetX+380, offsetY+138, null);
//		
//		ArrayList<Item> itemList = (ArrayList<Item>) currentShip.getItemsList();
//		int counterSB = 0;
//		int counterMS = 0;
//		int counterSM = 0;
//		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 4));
//		for(int i=0; i<itemList.size(); i++){
//			if(itemList.get(i).getName().equals("Speed Boost")){
//				counterSB++;
//			}
//			else if(itemList.get(i).getName().equals("Magnetic Shield")){
//				counterMS++;
//			}
//			else if(itemList.get(i).getName().equals("Scrap Metal")){
//				counterSM++;
//			}
//		}
//		if(counterSB > 0){
//			g.drawImage(item, offsetX+380, offsetY+138, null);
//			g2.drawString("X" + counterSB, offsetX+380, offsetY+138);
//		}
//		if(counterMS > 0){
//			g.drawImage(item, offsetX+380, offsetY+138, null);
//			g2.drawString("X" + counterMS, offsetX+380, offsetY+138);
//		}
//		if(counterSM > 0){
//			g.drawImage(item, offsetX+380, offsetY+138, null);
//			g2.drawString("X" + counterSM, offsetX+380, offsetY+138);
//		}
		
		
		
	}
}
