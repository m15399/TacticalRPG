package view;

import input.Button;
import input.Input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import resources.ImageLibrary;
import specific_ships_items.*;
import model.GameObject;
import model.Level;
import model.Ship;

public class ShipSelectionScreen extends GameObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3102298403585336668L;
	static final int WIDTH = 200;
	static final int HEIGHT = 300;
	static final int LIMITEDHEIGHT = 210;
	
	private boolean visible;
	private Level level;
	
	private Tooltip tooltip;
	
	private ArrayList<Button> buttons;
	private String levelFileName;
	
	private int count;
	private boolean blink;
	
	public ShipSelectionScreen(Level newLevel){
		level = newLevel;
		visible = false;
		buttons = new ArrayList<Button>();
		
		tooltip = new Tooltip();
		addChild(tooltip);
		
		Button backgroundButton = new Button(0, 0, WIDTH, HEIGHT);
		backgroundButton.getPosition().setZ(10);
		addButton(backgroundButton);
		
		levelFileName = newLevel.getFileName();
		if(levelFileName.equals("maps/mission1")){
			addLimitedSelectionButtons();
		}
		else{
			addNormalSelectionButtons();
		}
		
		count = 0;
		blink = true;
	}
	
	private abstract class SelectionButton extends Button{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 964645507538207347L;

		public SelectionButton(int x, int y, int bwidth, int bheight){
			super(x, y, bwidth, bheight);
		}
		
		public abstract Ship getANewShip();
		
		public void mouseHovered(){
			tooltip.setText(getANewShip().getDescription());
			tooltip.setVisible(true);
		}
		
		public void mouseExited(){
			tooltip.setVisible(false);
		}
		
		public void mouseReleased(){
			level.warpInPlayerShip(getANewShip());
		}
	}
	
	public void addNormalSelectionButtons(){
		addButton(new SelectionButton(5, 50, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1268827975675296096L;

			public Ship getANewShip() {
				return new Scout(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(105, 50, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8087173238058455824L;

			public Ship getANewShip() {
				return new Fighter(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(5, 140, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8228955844389950709L;

			public Ship getANewShip() {
				return new Bomber(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(105, 140, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -495592822536264783L;

			public Ship getANewShip() {
				return new RepairShip(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(5, 230, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5623089346887608357L;

			public Ship getANewShip() {
				return new BattleCruiser(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(105, 230, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5369998606744884021L;

			public Ship getANewShip() {
				return new Sniper(new Point(-1, -1));
			}
		});
	}
	
	public void addLimitedSelectionButtons(){
		addButton(new SelectionButton(5, 50, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8919730048975882607L;

			public Ship getANewShip() {
				return new Scout(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(105, 50, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8480664852745950570L;

			public Ship getANewShip() {
				return new Fighter(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(5, 140, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1435448701359450020L;

			public Ship getANewShip() {
				return new Bomber(new Point(-1, -1));
			}
		});
		addButton(new SelectionButton(105, 140, 64, 64) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7328712900930906491L;

			public Ship getANewShip() {
				return new RepairShip(new Point(-1, -1));
			}
		});
	}
	
	public void update(){
		count++;
		if(count % 40 == 0)
			blink = !blink;
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
	
	public void addButton(Button button) {
		buttons.add(button);
		Input.getInstance().addButton(button);

	}
	
	public void setVisible(boolean newVisible){
		visible = newVisible;
		if(visible){
			for(Button b : buttons){
				b.enable();
			}
		} else {
			for(Button b : buttons){
				b.disable();
			}
		}
	}
	
	public void draw(Graphics g){
		if(!visible)
			return;
				
		if(levelFileName.equals("maps/mission1")){
			drawLimitedSelectionScreen(g);
		}
		else{
			drawSelectionScreen(g);
		}
	}
		
	public void drawSelectionScreen(Graphics g){
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
		if(blink)
			g2.drawString("Select a Ship:", 10, 20);
		g2.setFont(font);
		g2.drawString("Scout", 10, 40);
		g2.drawImage(ImageLibrary.getInstance().getImage("scout.png"), 5, 45, null);
		g2.drawString("Fighter", 110, 40);
		g2.drawImage(ImageLibrary.getInstance().getImage("fighter.png"), 105, 45, null);
		g2.drawString("Bomber", 10, 130);
		g2.drawImage(ImageLibrary.getInstance().getImage("bomber.png"), 5, 135, null);
		g2.drawString("Repair Ship", 110, 130);
		g2.drawImage(ImageLibrary.getInstance().getImage("repairship.png"), 105, 135, null);
		g2.drawString("Battle Cruiser", 10, 220);
		g2.drawImage(ImageLibrary.getInstance().getImage("battlecruiser.png"), 5, 225, null);
		g2.drawString("Sniper", 110, 220);
		g2.drawImage(ImageLibrary.getInstance().getImage("sniper.png"), 105, 225, null);
	}
	
	public void drawLimitedSelectionScreen(Graphics g){
		int offsetX = 0;
		int offsetY = 0;
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(2));
		
		// Draw a black rect first as a background
		g2.setColor(Color.black);
		g2.fillRect(offsetX, offsetY, WIDTH, LIMITEDHEIGHT);
		
		// Draw a border (I just put the top right half for now)
		g2.setColor(Color.white);
		g2.drawLine(offsetX, offsetY + LIMITEDHEIGHT, WIDTH + offsetX, offsetY + LIMITEDHEIGHT);
		g2.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, LIMITEDHEIGHT + offsetY);
		
		//Draw title of screen
		Font font = g2.getFont();
		float f = 18.0f;
		g2.setFont(font.deriveFont(f));
		if(blink)
			g2.drawString("Select a Ship:", 10, 20);
		g2.setFont(font);
		g2.drawString("Scout", 10, 40);
		g2.drawImage(ImageLibrary.getInstance().getImage("scout.png"), 5, 45, null);
		g2.drawString("Fighter", 110, 40);
		g2.drawImage(ImageLibrary.getInstance().getImage("fighter.png"), 105, 45, null);
		g2.drawString("Bomber", 10, 130);
		g2.drawImage(ImageLibrary.getInstance().getImage("bomber.png"), 5, 135, null);
		g2.drawString("Repair Ship", 110, 130);
		g2.drawImage(ImageLibrary.getInstance().getImage("repairship.png"), 105, 135, null);
	}
}
