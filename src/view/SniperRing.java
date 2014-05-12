package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import actions.FadeToAction;

import model.Entity;
import model.Fadable;

public class SniperRing extends Entity implements Fadable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8458786760744094361L;
	
	private static final double HIGH_FADE = .6;
	private static final double LOW_FADE = .2;
	private static final int FADE_TIME = 40;
	
	private double fade;
	
	public SniperRing(double size, Entity spatialParent){
		
		fade = LOW_FADE;
		
		getPosition().setParent(spatialParent.getPosition());
		
		getPosition().setScale(size/2, size);
		
	}
	
	public void brighten(){
		FadeToAction f = new FadeToAction(this, HIGH_FADE, FADE_TIME, null);
		addChild(f);
		f.start();
	}
	
	public void dim(){
		FadeToAction f = new FadeToAction(this, LOW_FADE, FADE_TIME, null);
		addChild(f);
		f.start();
	}
	
	public void draw(Graphics g){
		getPosition().transform(g);
		
		g.setColor(new Color(1f,1f,1f,(float)fade));
		
		Graphics2D g2 = (Graphics2D) g;
		
		float strokeWidth = (float)(Math.pow(fade / LOW_FADE, .5) * 2 / getPosition().getScaleX());
		
		
		int s = 10;
		
		int gap = 80;

		g2.setStroke(new BasicStroke(strokeWidth));
		g.drawArc(-s/2, -s/2, s,s, gap/2, 360-gap);
		g2.setStroke(new BasicStroke(strokeWidth * .6f));
		g.drawArc(-s/2, -s/2, s,s, gap/2, 360-gap);
		g2.setStroke(new BasicStroke(strokeWidth * .4f));
		g.drawArc(-s/2, -s/2, s,s, gap/2, 360-gap);
		
		getPosition().untransform(g);

	}

	@Override
	public double getFade() {
		return fade;
	}

	@Override
	public void setFade(double fade) {
		this.fade = fade;
		
	}
	
}
