package model;

import java.awt.Color;
import java.awt.Graphics;

public class Map extends GameObject {

	public void draw(Graphics g) {
		// Tiles 64x64
		g.setColor(Color.WHITE);
		for (int i = 0; i <= 1024; i += 64) {
			g.drawLine(i, 0, i, 768);
		}
		for (int i = 0; i <= 768; i += 64) {
			g.drawLine(0, i, 1024, i);
		}
	}
}
