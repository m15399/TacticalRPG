package view;

import input.Button;
import input.Input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import resources.ImageLibrary;

import levels.MultiplayerLevel;
import model.Game;
import model.GameObject;

public class MultiplayerMapSelectionView extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2393881250225585758L;
	
	private static final int TOP = 390;
	private static final int LEFT = 500;
	private static final int MAPWIDTH = 200;
	private static final int MAPHEIGHT = 150;
	private static final int SPACING = 20;
	

	private ArrayList<Button> buttons;
	
	public MultiplayerMapSelectionView(){
		
		buttons = new ArrayList<Button>();
		
		addButton(new Button(LEFT, TOP, MAPWIDTH, MAPHEIGHT){

			/**
			 * 
			 */
			private static final long serialVersionUID = 7577504080259997222L;
			
			public void mouseReleased(){
				Game.getInstance().transitionTo(new MultiplayerLevel(0));
			}
			
		});
		
		addButton(new Button(LEFT + MAPWIDTH + SPACING, TOP, MAPWIDTH, MAPHEIGHT){

			/**
			 * 
			 */
			private static final long serialVersionUID = -4093340793507185774L;

			public void mouseReleased(){
				Game.getInstance().transitionTo(new MultiplayerLevel(1));
			}
			
		});
		
		addButton(new Button(LEFT, TOP + MAPHEIGHT + SPACING, MAPWIDTH, MAPHEIGHT){

			/**
			 * 
			 */
			private static final long serialVersionUID = 7008428398378984541L;

			public void mouseReleased(){
				Game.getInstance().transitionTo(new MultiplayerLevel(2));
			}
			
		});
		
		addButton(new Button(LEFT + MAPWIDTH + SPACING, TOP + MAPHEIGHT + SPACING, MAPWIDTH, MAPHEIGHT){

			/**
			 * 
			 */
			private static final long serialVersionUID = 6331256229130538470L;

			public void mouseReleased(){
				Game.getInstance().transitionTo(new MultiplayerLevel(3));
			}
			
		});
		
	}
	
	public void draw(Graphics g){
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 28));
		g.drawString("Choose a Map:", LEFT + 110, TOP - SPACING);
		
		g.drawImage(ImageLibrary.getInstance().getImage("map1.jpg"), LEFT, TOP, null);
		g.drawImage(ImageLibrary.getInstance().getImage("map2.jpg"), LEFT + MAPWIDTH + SPACING, TOP, null);
		g.drawImage(ImageLibrary.getInstance().getImage("map3.jpg"), LEFT, TOP + MAPHEIGHT + SPACING, null);
		g.drawImage(ImageLibrary.getInstance().getImage("map4.jpg"), LEFT + MAPWIDTH + SPACING, TOP + MAPHEIGHT + SPACING, null);
		
		
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
