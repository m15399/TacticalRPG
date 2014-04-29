package view;

import input.Input;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.Game;
import model.GameObject;

public class Tooltip extends GameObject {

	private int width, height;
	private String text;
	private boolean visible;

	public Tooltip() {
		width = height = 0;
		text = "Hello world";
		visible = false;
	}

	private void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setText(String text){
		this.text = text;
		
		int ysize = 16;
		
		for (@SuppressWarnings("unused") String line : text.split("\n"))
			ysize += 14;
		
		setSize(140, ysize);
	}
	
	private void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}

	public void draw(Graphics g1) {
		
		if(!visible)
			return;
		
		Graphics2D g = (Graphics2D) g1;

		g.setStroke(new BasicStroke(2));
		
		int offsetX = (int)Input.getInstance().getX();
		int maxOffsetX = Game.WIDTH - width - 2;
		offsetX = Math.min(offsetX, maxOffsetX);
		int offsetY = (int)Input.getInstance().getY() + 20;
		int maxOffsetY = Game.HEIGHT - height - 25;
		offsetY = Math.min(offsetY, maxOffsetY);
		
		// Draw a black rect first as a background
		g.setColor(Color.black);
		g.fillRect(offsetX, offsetY, width, height);

		// Draw a border (I just put the top right half for now)
		g.setColor(Color.white);
		g.drawLine(offsetX, offsetY + height, width + offsetX, offsetY
				+ height);
		g.drawLine(width + offsetX, offsetY, width + offsetX, height + offsetY);
		g.drawLine(offsetX, offsetY, width + offsetX, offsetY);
		g.drawLine(offsetX, offsetY, offsetX, height + offsetY);

		g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		
		drawString(g, text, offsetX + 8, offsetY + 2);
		
	}

}
