package level_intros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import model.Game;
import model.GameObject;
import model.TitleMenu;

public class Credits extends LevelIntro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8330133783986559569L;

	public Credits(Game game){
		super(game);
	}
	
	public void draw(Graphics g){
		
		int ox = 300;
		int oy = 350;
		
		g.setColor(Color.white);
		
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("Created by", ox, oy-50);
		
		g.setFont(new Font("Arial", Font.BOLD, 24));
		
		int dy = 40;
		
		g.drawString("Devin Birtciel", ox, oy + dy * 0);
		g.drawString("Mark Gardner", ox, oy + dy * 1);
		g.drawString("Ethan Mangosing", ox, oy + dy * 2);
		g.drawString("Luis Teran", ox, oy + dy * 3);
		
	}

	@Override
	public GameObject getNextRoot() {
		return new TitleMenu(getGame());
	}
	
}
