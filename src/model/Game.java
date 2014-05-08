package model;


import input.Input;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import songPlayer.SongPlayer;
import utils.Observable;
import utils.Observer;

import actions.FadeToAction;





public class Game extends JPanel implements Runnable, Fadable {

	private static final long serialVersionUID = -7803629994015778818L;

	public static final boolean DEBUG = true;
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	
	public static final int FPSMUL = 2;
	public static final int FPS = 30 * FPSMUL;
	
	public static long frameNumber = 0;

	private static int FADE_TIME = 30*Game.FPSMUL;

	
	private Thread thread;
	private GameObject rootObject;
	
	private GameObject nextRoot;
	private double fade;
	
	public static void main(String[] args){
		
		Game game = new Game();

		JFrame window = new JFrame();
		window.setSize(WIDTH, HEIGHT);
		window.setLocation(100,50);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(game);
		window.setVisible(true);
		window.setResizable(false);
		
		game.startGame();
	}
	
	public Game(){
		rootObject = null;
		thread = null;
		
		addMouseListener(Input.getInstance());
		addMouseMotionListener(Input.getInstance());

		fade = 0;
		
		setRoot(new TitleMenu(this));
		
	}
	
	public void startGame(){
		thread = new Thread(this);
		thread.start();
		
		SongPlayer.playFile("./songFiles/space_clips.mp3");

	}
	
	private void setRoot(GameObject o){
		if(rootObject != null){
			rootObject.destroy();
		}
		
		if(DEBUG){
			Input.getInstance().printNumButtons();
			
		}
		
		rootObject = o;
	}
	
	
	public void transitionTo(GameObject o){
		nextRoot = o;
		Input.getInstance().setEnabled(false);
		
		FadeToAction fadeDown = new FadeToAction(this, 1.0, FADE_TIME, new Observer(){
			
			public void notified(Observable sender){
				
				setRoot(nextRoot);
				
				FadeToAction fadeUp = new FadeToAction(Game.this, 0, FADE_TIME, null);
				rootObject.addChild(fadeUp);
				fadeUp.start();
				
				Input.getInstance().setEnabled(true);
				
			}
		});
		
		rootObject.addChild(fadeDown);
		fadeDown.start();
	}

	public void run() {
				        		
		while (true) {
			
			frameNumber++;
			update();
			repaint();
			
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		Input.getInstance().update();
		rootObject.updateSelfAndChildren();	
	}
	
	public void paint(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		rootObject.drawSelfAndChildren(g);
		
		// fade
		g.setColor(new Color(0f,0f,0f,(float)fade));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

	}
	
	public void setFade(double fade){
		this.fade = fade;
	}
	
	public double getFade(){
		return fade;
	}
	
}
