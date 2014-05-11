package level_intros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import levels.Mission3;
import model.GameObject;

public class Mission3Intro extends LevelIntro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8620885094576078851L;

	
	public void draw(Graphics g){
		
		int ox = 100;
		int oy = 550;
		
		g.setColor(Color.white);
		
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("Mission 3:", ox, oy);
		
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Objective: Destroy the Mothership.", ox, oy + 60);
		g.drawString("Secondary Objective: Destroy all units.", ox, oy + 100);
		
	}

	@Override
	public GameObject getNextRoot() {
		return new Mission3();
	}
	
}
