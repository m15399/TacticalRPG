package view;

import model.Entity;
import model.Game;
import input.Button;
import input.Input;

import java.awt.*;

public class MenuButton extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8016317081649466187L;
	
	static final int WIDTH = 50;
	static final int HEIGHT = 25;
	
	private Camera camera;
	private Button button;
	
	public MenuButton(Camera ncamera){
		super(Game.WIDTH - 65, Game.HEIGHT - 60);
		
		this.camera = ncamera;
		
		button = new Button(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 5178215921007798081L;

			public void mouseReleased(){
				addChild(new PauseMenu(camera));
			}
			
			public void mouseHovered(){
//				System.out.println("working");
			}
		};
		button.getPosition().setParent(getPosition());
		button.getPosition().setScale(WIDTH, HEIGHT);
		Input.getInstance().addButton(button);
		
	}
	
	public void onDestroy(){
		Input.getInstance().removeButton(button);
	}
	
	public void draw(Graphics g){
		int offsetX = (int)getPosition().getX();
		int offsetY = (int)getPosition().getY();
		
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(1));
		
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
		Font font = new Font("Arial", Font.PLAIN, 14);
		float titleFontSize = 14.0f;
		g2.setFont(font.deriveFont(titleFontSize));
		g2.drawString("Menu", offsetX + 7, offsetY + 18);
		
	}
	
	
	public void enable(){
		button.enable();
	}
	
	public void disable(){
		button.disable();
	}
	
	
	
}
