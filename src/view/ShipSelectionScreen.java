package view;

import input.Button;
import input.Input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import resources.ImageLibrary;
import utils.Position;
import model.GameObject;
import model.Level;

public class ShipSelectionScreen extends GameObject{
	static final int WIDTH = 200;
	static final int HEIGHT = 400;
	
	private boolean visible;
	private Level level;
	
	private ArrayList<Button> buttons;
	
	public ShipSelectionScreen(Level newLevel){
		level = newLevel;
		visible = false;
		buttons = new ArrayList<Button>();
	}
	
	public void update(){
		
	}
	
	public void onDestroy(){
		clearButtons();
	}
	
	private void clearButtons() {
		// clear old buttons
		for (Button b : buttons) {
			Input.getInstance().removeButton(b);
		}
		buttons.clear();
	}
	
	public void addButton(String name, Button button) {
		buttons.add(button);
		Input.getInstance().addButton(button);

		// set its position
		Position bp = button.getPosition();
		int index = buttons.size() - 1;
		bp.setLocation(0, index * HEIGHT);
		bp.setScale(WIDTH, HEIGHT);

	}
	
	public void setVisible(boolean newVisible){
		visible = newVisible;
	}
	
	public void draw(Graphics g){
		if(!visible)
			return;
		
		int offsetX = 0;
		int offsetY = 0;
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(2));
		
		// Draw a black rect first as a background
		g2.setColor(Color.black);
		g2.fillRect(offsetX, offsetY, WIDTH, HEIGHT);
		
		// Draw a border (I just put the top right half for now)
		g2.setColor(Color.white);
		g2.drawLine(offsetX, offsetY + HEIGHT, WIDTH + offsetX, offsetY + HEIGHT);
		g2.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, HEIGHT + offsetY);
		
		//Draw title of screen
		Font font = g2.getFont();
		float f = 18.0f;
		g2.setFont(font.deriveFont(f));
		g2.drawString("Ship Selection Screen", 10, 20);
		g2.setFont(font);
		g2.drawString("Scout", 10, 40);
		g2.drawImage(ImageLibrary.getInstance().getImage("scout.png"), 5, 45, null);
		g2.drawString("Fighter", 110, 40);
		g2.drawString("Bomber", 10, 130);
		g2.drawString("Repair Ship", 110, 130);
		g2.drawString("Battle Cruiser", 10, 220);
		g2.drawString("Sniper", 110, 220);
		g2.drawString("Mothership", 10, 310);
	}
}
