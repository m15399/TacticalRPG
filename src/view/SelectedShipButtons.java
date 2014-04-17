package view;

import input.Button;
import input.Input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import utils.Position;

import model.Entity;
import model.Ship;

/*
 * The buttons that pop up beside a ship when you select it
 */
public class SelectedShipButtons extends Entity {

	private static final int WIDTH = 100;
	private static final int HEIGHT = 25;

	private Ship ship;

	private ArrayList<Button> buttons;
	private ArrayList<String> buttonNames;

	public SelectedShipButtons() {
		ship = null;
		buttons = new ArrayList<Button>();
		buttonNames = new ArrayList<String>();
	}

	public void setShip(Ship ship) {
		this.ship = ship;
		clearButtons();
	}
	
	public void onDestroy(){
		clearButtons();
	}

	private void clearButtons() {
		// clear old buttons
		for (Button b : buttons) {
			Input.getInstance().removeButton(b);
		}
		buttons.clear();
		buttonNames.clear();
	}

	public void addButton(String name, Button button) {
		buttons.add(button);
		buttonNames.add(name);
		Input.getInstance().addButton(button);

		// parent button to self
		Position bp = button.getPosition();
		Position p = getPosition();
		bp.setParent(p);

		// set its position
		int index = buttons.size() - 1;
		bp.setLocation(0, index * HEIGHT);
		bp.setScale(WIDTH, HEIGHT);

	}

	public void setLocation(int x, int y) {

		x += 16;
		y += 16;

		getPosition().setLocation(x, y);
	}

	public void draw(Graphics g1) {
		if (ship == null)
			return;

		Graphics2D g = (Graphics2D) g1;

		int x = (int) getPosition().getLocalX();
		int y = (int) getPosition().getLocalY();

		g.setFont(new Font("Arial", Font.BOLD, 16));

		for (String s : buttonNames) {
			g.setColor(Color.lightGray);
			g.fillRect(x, y, WIDTH, HEIGHT - 2);
			g.setColor(Color.black);
			g.drawString(s, x + WIDTH / 4, y + HEIGHT - 7);
			y += HEIGHT;
		}

	}

}
