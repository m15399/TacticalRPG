package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import actions.TimerAction;

import utils.Observable;
import utils.Observer;

import model.Entity;

public class BouncingStat extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -969354159664514454L;
	private String text;
	private double yv;
	
	private static double MUL = .26;
	
	public BouncingStat(String text, Entity spatialParent) {
		
		this.text = text;
		this.yv = -4.2 * MUL;
		getPosition().moveBy(0, -10);
		
		TimerAction timer = new TimerAction(40, new Observer(){
			public void notified(Observable sender){
				destroy();
			}
		});
		timer.start();
		addChild(timer);
		
		// parent to the entity passed in
		if (spatialParent != null)
			setSpatialParent(spatialParent);
	}
	
	public void update(){
		getPosition().moveBy(0, yv);
		yv += .35 * MUL;
	}
	
	public void draw(Graphics g){
		boolean mirrored = getPosition().getMirrored();
		if(mirrored)
			getPosition().mirror();
		getPosition().transform(g);
		
		
		Font f = new Font("Arial", Font.BOLD, 12);
		g.setFont(f);	
		
		int width = g.getFontMetrics().stringWidth(text);

		
		g.setColor(Color.darkGray);
		g.drawString(text, -width/2+1, 1);
		g.setColor(new Color(.6f, 1f, .7f));
		g.drawString(text, -width/2, 0);
		
		getPosition().untransform(g);
	}

}
