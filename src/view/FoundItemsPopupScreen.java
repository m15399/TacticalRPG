package view;

import input.Button;
import input.Input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import model.GameObject;

public class FoundItemsPopupScreen extends GameObject{
	
	static final int WIDTH = 200;
	static final int HEIGHT = 150;
	
	private boolean visible;
	private List<Button> buttons;
	
	public FoundItemsPopupScreen(){
		buttons = new ArrayList<Button>();
		visible = false;
		
		addSelectionButtons();
	}
	
	public void draw(Graphics g){
		String title = "Item Discovery!";
		String message = "Your ship found an item.";
		String buttonMessage = "Sweet!";
		if(!visible)
			return;
		
		int offsetX = 200;
		int offsetY = 150;
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(2));
		
		// Draw a black rect first as a background
		g2.setColor(Color.black);
		g2.fillRect(offsetX, offsetY, WIDTH, HEIGHT);
		
		// Draw a border (I just put the top right half for now)
		g2.setColor(Color.white);
		g2.drawLine(offsetX, offsetY + HEIGHT, WIDTH + offsetX, offsetY + HEIGHT);
		g2.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, HEIGHT + offsetY);
		g2.drawLine(offsetX, offsetY, offsetX, offsetY + HEIGHT);
		g2.drawLine(offsetX, offsetY, offsetX + WIDTH, offsetY);
		
		//Draw Message
		Font font = g2.getFont();
		float titleFontSize = 18.0f;
		g2.setFont(font.deriveFont(titleFontSize));
		g2.drawString(title, offsetX + 40, offsetY + 20);
		float messageFontSize = 14.0f;
		g2.setFont(font.deriveFont(messageFontSize));
		g2.drawString(message, offsetX + 25, offsetY + 80);
		
		//Draw a Button
		int offsetButtonX = offsetX + 40;
		int offsetButtonY = offsetY + 100;
		
		g2.drawLine(offsetButtonX, offsetButtonY, offsetButtonX + 120, offsetButtonY);
		g2.drawLine(offsetButtonX, offsetButtonY, offsetButtonX, offsetButtonY + 40);
		g2.drawLine(offsetButtonX, offsetButtonY + 40, offsetButtonX + 120, offsetButtonY + 40);
		g2.drawLine(offsetButtonX + 120, offsetButtonY, offsetButtonX + 120, offsetButtonY + 40);
		
		g2.drawString(buttonMessage, offsetButtonX + 40, offsetButtonY + 20);
	}
	
	public void update(){
		
	}
	
	public void onDestroy(){
		clearButtons();
	}
		
	public void addSelectionButtons(){
		//Magic numbers should be equal to the button offset values in the draw method.  (offsetButtonX, and offsetButtonY)
		//Second set of values equate to the size of the button
		addButton(new SelectionButton(240, 250, 120, 40) {});
	}
	
	private abstract class SelectionButton extends Button{
		
		public SelectionButton(int x, int y, int bwidth, int bheight){
			super(x, y, bwidth, bheight);
		}
		
		public void mouseHovered(){
		}
		
		public void mouseExited(){
		}
		
		public void mouseReleased(){
			setVisible(false);
		}
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
}
