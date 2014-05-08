package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import actions.TimerAction;

import utils.Observable;
import utils.Observer;

import model.Entity;

public class Explosion extends Entity {

	private List<Particle> particles;
	
	private double size;
	private boolean stopped;
	
	public Explosion(double size, int duration, Entity spatialParent) {

		particles = new ArrayList<Particle>();
		this.size = size;
		stopped = false;
		
		TimerAction timer = new TimerAction(duration, new Observer(){
			public void notified(Observable sender){
				stopParticles();

			}
		});
		addChild(timer);
		timer.start();
		
		// parent to the entity passed in
		if (spatialParent != null)
			setSpatialParent(spatialParent);
	}
	
	private void stopParticles(){
		stopped = true;
		TimerAction timer = new TimerAction(150, new Observer(){
			public void notified(Observable sender){
				destroy();
			}
		});
		addChild(timer);
		timer.start();
	}
	
	private void addParticles(){
		if(stopped)
			return;
		
		double x = getPosition().getX();
		double y = getPosition().getY();
		
		double scatter = 10 * size;
		double maxHV = 1.5 * size;
		double s = 10 * size;
		double sv = -.6 * size;
		int num = 3;
		
		for(int i = 0; i < num; i++){
			double px = x + Math.random() * scatter * 2 - scatter;
			double py = y + Math.random() * scatter * 2 - scatter;
			
			particles.add(new Particle(px, py, Math.random() * maxHV * 2 - maxHV, Math.random() * maxHV * 2 - maxHV, 
					s, sv));
		}
		
		
	}
	
	public synchronized void updateParticles(){
		Iterator<Particle> itr = getParticles().iterator();
		
		while(itr.hasNext()){
			Particle p = itr.next();
			p.update();
			if(p.size <= 0){
				itr.remove();
			}
		}
		
	}
	
	private synchronized List<Particle> getParticles(){
		return particles;
	}
	
	public synchronized void draw(Graphics g){
		
		addParticles();
		
		updateParticles();
		
		Iterator<Particle> itr = getParticles().iterator();
		
		while(itr.hasNext()){
			Particle p = itr.next();
			p.draw(g);
		}
		
//		System.out.println(particles.size());
	}
	
	private class Particle{
		
		double x, y, xv, yv;
		double size, sv;
		double initialSize;
		
		public Particle(double x, double y, double xv, double yv, double size, double sv){
			this.x = x;
			this.y = y;
			this.xv = xv;
			this.yv = yv;
			this.size = this.initialSize = size;
			this.sv = sv;
		}
		
		public void update(){
			x += xv;
			y += yv;
			size += sv;
		}
		
		public void draw(Graphics g){
			
			double sleft = size/initialSize;
			
			if(sleft > .9){
				g.setColor(Color.white);
			} else if (sleft > .8){
				g.setColor(Color.yellow);
			} else if (sleft > .5){
				g.setColor(Color.orange);
			} else if (sleft > .2){
				g.setColor(Color.red);
			} else {
				g.setColor(Color.gray);
			}
			
						
			g.fillArc((int)x,(int) y, (int)size, (int)size, 0, 360);
		}
		
	}

}
