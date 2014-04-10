package src;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import src.model.GameObject;
import src.model.Level;


public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = -7803629994015778818L;

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	
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
	}
	
	public void startGame(){
		thread = new Thread(this);
		thread.start();
	}
	
	public void setRoot(GameObject o){
		rootObject = o;
	}

	public void run() {
		
		setRoot(new Level());
		        		
		while (true) {
			
			update();
			repaint();
			
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		rootObject.updateSelfAndChildren();	
	}
	
	public void paint(Graphics g){
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		rootObject.drawSelfAndChildren(g);
	}
	
}
