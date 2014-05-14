package view;

import actions.ActionQueue;
import actions.TimerAction;
import utils.Observable;
import utils.Observer;
import model.Entity;
import model.GameObject;

public class GunSprite extends AnimatedSprite {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5435813892729073891L;


	public enum GunType {
		CANNON, MACHINE_GUN;
	}
	
	public enum GunSize {
		H8, H16;
	}
	
	private static final int FIRE_RATE = 2;
	private static final int CANNON_TIME = 10;
	private RapidFirer rapidFirer;
	
	private GunType type;
	
	public GunSprite(int x, int y, Entity spatialParent, GunSize size, GunType type){
		
		this.type = type;
		
		getPosition().setLocation(x, y);
		
		// set up sprite
		String fn = "";
		int w = 0, h = 0;
		
		switch(size){
		case H16:
			fn = "guns_36x17.png";
			w = 36;
			h = 17;
			break;
		case H8:
			fn = "guns_19x7.png";
			w = 19;
			h = 7;
		}
		
		setImage(fn);
		setFrameSize(w, h);
		
		// set animation properties
		setDelay(3);
		int frames[] = {0, 1, 2, 3};
		setFrameIndicies(frames);
		
		// parent to the entity passed in
		if(spatialParent != null)
			setSpatialParent(spatialParent);
		
		if(type == GunType.MACHINE_GUN){
			rapidFirer = new RapidFirer();
			addChild(rapidFirer);
			
		}
		
		setVisible(false);
	}
	
	public void playGunAnimation(int delay, int totalTime){
		if(type == GunType.MACHINE_GUN)
			playMachineGunAnimation(delay, totalTime);
		else
			playCannonAnimation(delay, totalTime);		
	}
	
	private void playMachineGunAnimation(int delay, int totalTime){
		
		ActionQueue q = new ActionQueue(null);
	
		q.addAction(new TimerAction(delay, new Observer(){
			public void notified(Observable sender){
				rapidFirer.start();
			}
		}));
		
		q.addAction(new TimerAction(totalTime - delay, new Observer(){
			public void notified(Observable sender){
				rapidFirer.stop();
			}
		}));
		
		addChild(q);
		q.start();
		
	}
	
	private void playCannonAnimation(int delay, int totalTime){
		ActionQueue q = new ActionQueue(null);
		
		q.addAction(new TimerAction(delay, new Observer(){
			public void notified(Observable sender){
				setVisible(true);
			}
		}));
		
		q.addAction(new TimerAction(CANNON_TIME, new Observer(){
			public void notified(Observable sender){
				setVisible(false);
			}
		}));
		
		addChild(q);
		q.start();
	}

	
	private class RapidFirer extends GameObject {
				
		/**
		 * 
		 */
		private static final long serialVersionUID = 25151812920092619L;
		int count;
		boolean going;
		
		public RapidFirer(){
			count = 0;
			going = false;
		}
		
		public void start(){
			going = true;
			count = 1;
			setVisible(true);
		}
		
		public void stop(){
			going = false;
			setVisible(false);
		}
		
		public void update(){
			if(going){
				count++;
				if(count % FIRE_RATE == 0){
					setVisible(!getVisible());
				}
			}
			
		}
		
	}
	
	
}
