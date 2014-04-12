package model;

import java.awt.Color;
import java.awt.Graphics;

public class Map extends GameObject {

	public void draw(Graphics g) {
		// Tiles 64x64
		g.setColor(Color.WHITE);
		for (int i = 0; i <= Game.WIDTH; i += 64) {
			g.drawLine(i, 0, i, Game.HEIGHT);
		}
		for (int i = 0; i <= Game.HEIGHT; i += 64) {
			g.drawLine(0, i, Game.WIDTH, i);
		}
	}
}
