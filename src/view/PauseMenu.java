package view;

import input.Button;
import input.Input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import model.Game;
import model.GameObject;

public class PauseMenu extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9115838445775394948L;

	private static final int WIDTH = 200;
	private static final int HEIGHT = 250;
	private static final int BHEIGHT = -10;
	

	private Camera camera;
	private ArrayList<Button> buttons;

	public PauseMenu(Camera newCamera) {
		buttons = new ArrayList<Button>();
		
		// block input on the rest of the game
		Button bgButton = new Button(0, 0, Game.WIDTH, Game.HEIGHT);
		bgButton.getPosition().setZ(BHEIGHT + 1);
		addButton(bgButton);
		
		int w = 120;
		int h = 42;
		Button continueButton = new Button(Game.WIDTH/2 - w/2, Game.HEIGHT/2 - h/2 - 25, w, h){

			/**
			 * 
			 */
			private static final long serialVersionUID = -4743445012874514713L;
			
			public void mouseHovered(){
//				System.out.println("continue");
			}
			
			public void mouseReleased(){
				destroy();
			}
		};
		continueButton.getPosition().setZ(BHEIGHT);
		addButton(continueButton);
		
		Button quitButton = new Button(Game.WIDTH/2 - w/2, Game.HEIGHT/2 - h/2 + 35, w, h){
			/**
			 * 
			 */
			private static final long serialVersionUID = -3544795303633587413L;

			public void mouseHovered(){
//				System.out.println("quit");
			}
			
			public void mouseReleased(){
				// safe bet that animations aren't happening
				if(!camera.getIsMoving()){
					destroy();
					Game.getInstance().saveGame();
					System.exit(0);
				}
			}
		};
		quitButton.getPosition().setZ(BHEIGHT);
		addButton(quitButton);
		
		this.camera = newCamera;

	}

	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		int offsetX = Game.WIDTH/2 - WIDTH/2;
		int offsetY = Game.HEIGHT/2 - HEIGHT/2-20;

		g2.setStroke(new BasicStroke(2));

		// Draw a black rect first as a background
		g2.setColor(Color.black);
		g2.fillRect(offsetX, offsetY, WIDTH, HEIGHT);

		// Draw a border (I just put the top right half for now)
		g2.setColor(Color.white);
		g2.drawLine(offsetX, offsetY + HEIGHT, WIDTH + offsetX, offsetY
				+ HEIGHT);
		g2.drawLine(WIDTH + offsetX, offsetY, WIDTH + offsetX, HEIGHT + offsetY);
		g2.drawLine(offsetX, offsetY, offsetX, offsetY + HEIGHT);
		g2.drawLine(offsetX, offsetY, offsetX + WIDTH, offsetY);

		// Draw Message
		Font font = new Font("Arial", Font.BOLD, 14);
		float titleFontSize = 24.0f;
		g2.setFont(font.deriveFont(titleFontSize));
		g2.drawString("Menu", offsetX + 68, offsetY + 50);
		titleFontSize = 18.0f;
		g2.setFont(font.deriveFont(titleFontSize));
		
		// Draw a Button
		int offsetButtonX = offsetX + 40;
		int offsetButtonY = offsetY + 100;

		g2.drawLine(offsetButtonX, offsetButtonY, offsetButtonX + 120,
				offsetButtonY);
		g2.drawLine(offsetButtonX, offsetButtonY, offsetButtonX,
				offsetButtonY + 40);
		g2.drawLine(offsetButtonX, offsetButtonY + 40, offsetButtonX + 120,
				offsetButtonY + 40);
		g2.drawLine(offsetButtonX + 120, offsetButtonY, offsetButtonX + 120,
				offsetButtonY + 40);

		int oy2 = 60;
		
		g2.drawLine(offsetButtonX, offsetButtonY+ oy2, offsetButtonX + 120,
				offsetButtonY+ oy2);
		g2.drawLine(offsetButtonX, offsetButtonY+ oy2, offsetButtonX,
				offsetButtonY + 40+ oy2);
		g2.drawLine(offsetButtonX, offsetButtonY+ oy2 + 40, offsetButtonX + 120,
				offsetButtonY + 40+ oy2);
		g2.drawLine(offsetButtonX + 120, offsetButtonY+ oy2, offsetButtonX + 120,
				offsetButtonY + 40+ oy2);
		
		g2.drawString("Continue", offsetButtonX + 20, offsetButtonY + 25);
		g2.drawString("Save & Quit", offsetButtonX + 9, offsetButtonY + 25 + oy2);
	}

	public void onDestroy() {
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

}
