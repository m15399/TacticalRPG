package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameObject;
import model.Ship;

public class SelectedShipViewCopy extends JFrame{

	public static void main(String[] args){
		JFrame panel = new SelectedShipViewCopy();
		panel.setVisible(true);
	}
	public SelectedShipViewCopy(){
		final int WIDTHOFWINDOW =  500;
		final int HEIGHTOFWINDOW = 180;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTHOFWINDOW, HEIGHTOFWINDOW);
		this.setLocation(50, 25);
		this.setResizable(false);
		this.add(new SelectedShipPanel(new Ship(new Point(1,1))));
	}
	private class SelectedShipPanel extends JPanel{
	private Ship currentShip;
	
	
	public SelectedShipPanel(Ship selectedShip){
		currentShip = selectedShip;
		this.setSize(500, 180);
		this.setBackground(Color.BLACK);
		this.setBorder(BorderFactory.createLineBorder(Color.white, 5, false));
		
	}
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	
	//Method ship.getName() , ship.getDescription(),
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g2.setColor(Color.white);
		String shipName = "Scout";
		g2.drawString(shipName, 186, 40);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		String shipDescription = "Mobile scouting ship,\ngenerates and places\nmines.";
		drawString(g2, shipDescription, 186, 45 );
		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.drawString("Stats", 333, 30);
		g2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 12));
		g2.drawString("Hull:                 " + currentShip.getHull(), 333, 50);
		g2.drawString("Shields:          " + currentShip.getMaxShielding(), 333, 65);
		//need ship.getdmg();
		g2.drawString("Damage:          " + "30-34", 333, 80);
		g2.drawString("Accuracy:          " + currentShip.getAccuracy(), 333, 95);
		//need ship.getCrit()
		g2.drawString("Crit Chance:          " + "15%", 333, 110);
		g2.drawString("Speed:          " + currentShip.getMoves(), 333, 125);
		g2.drawString("Items:" ,333, 150);
	}
	
	}
	
}
