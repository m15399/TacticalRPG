package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import utils.Observer;
import input.Button;
import input.Input;
import model.GameObject;

public class TitleMenuButton extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 230941741528334962L;

	private static final int FONTSIZE = 36;
	
	private Button button;
	private boolean hovered;
	private Observer onClick;
	private String text;
		
	public TitleMenuButton(String text, int x, int y, int width, Observer newOnClick){
		onClick = newOnClick;
		this.text = text;
		
		hovered = false;
				
		button = new Button(x, y, width, FONTSIZE + 10){
			/**
			 * 
			 */
			private static final long serialVersionUID = -7350608698110868762L;
			public void mouseHovered(){
				hovered = true;
			}
			public void mouseExited(){
				hovered = false;
			}
			public void mouseReleased(){
				onClick.notified(null);
			}
		};
		Input.getInstance().addButton(button);
	}
	
	public void onDestroy(){
		Input.getInstance().removeButton(button);
	}
	
	public void draw(Graphics g){
		g.setFont(new Font("Arial", Font.BOLD, FONTSIZE));
		if(hovered){
			g.setColor(Color.white);
		} else {
			g.setColor(Color.gray);
		}
		g.drawString(text, (int)button.getPosition().getX(), (int)button.getPosition().getY()+FONTSIZE);
	}
	
}
