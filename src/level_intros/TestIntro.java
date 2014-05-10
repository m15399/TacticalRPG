package level_intros;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import levels.Mission1;
import model.Game;
import model.GameObject;

public class TestIntro extends LevelIntro {

	public TestIntro(Game game){
		super(game);
	}
	
	public void draw(Graphics g){
		
		int ox = 100;
		int oy = 550;
		
		g.setColor(Color.white);
		
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("Mission 1:", ox, oy);
		
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Objective: Reach the end zone.", ox, oy + 60);
		g.drawString("Secondary Objective: Destroy all units.", ox, oy + 100);
		
	}

	@Override
	public GameObject getNextRoot() {
		return new Mission1(getGame());
	}
	
}
