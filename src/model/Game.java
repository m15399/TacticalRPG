package model;


import input.Input;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;





public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = -7803629994015778818L;

	public static final boolean DEBUG = false;
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	
	public static final int FPSMUL = 2;
	public static final int FPS = 30 * FPSMUL;
	
	public static long frameNumber = 0;
	
	private Thread thread;
	private GameObject rootObject;
	
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

		setRoot(new TitleMenu(this));
		
	}
	
	public void startGame(){
		thread = new Thread(this);
		thread.start();
	}
	
	public void setRoot(GameObject o){
		if(rootObject != null){
			rootObject.destroy();
		}
		rootObject = o;
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
	}
	
}
