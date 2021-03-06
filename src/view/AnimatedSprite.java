package view;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

/*
 * Sprite that runs an animation from a sprite sheet
 */
public class AnimatedSprite extends Sprite {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1219984226392226186L;
	private Size frameSize;
	private int delay, delayLeft, numberFrames, currentFrame;
	private int[] frameIndicies;
	private boolean visible;

	public AnimatedSprite(){
		initAnimatedSprite();
	}
	
	public AnimatedSprite(String filename) {
		super(filename);
		initAnimatedSprite();
	}

	public AnimatedSprite(String filename, int x, int y) {
		super(filename, x, y);
		initAnimatedSprite();
	}

	private void initAnimatedSprite() {
		frameSize = new Size(0, 0);
		delay = delayLeft = numberFrames = currentFrame = 0;
		frameIndicies = null;
		visible = true;
	}
	
	public void setRandomFrame(){
		Random random = new Random();
		int rand = random.nextInt(numberFrames);
		int[] indicies = {0};
		indicies[0] = rand;
		setDelay(10);
		setFrameIndicies(indicies);
	}

	public void update() {
		delayLeft--;
		if(delayLeft <= 0){
			
			currentFrame++;
			if(currentFrame >= numberFrames){
				currentFrame = 0;
			}
			
			delayLeft = delay;
		}
	}
	
	public void draw(Graphics g){
		if(!visible)
			return;
		
		// transform to draw Sprite correctly
		getPosition().transform(g);
		
		// figure out which frame to draw
		int index;
		if(frameIndicies != null){ // use frameIndicies array if available
			index = frameIndicies[currentFrame];
		} else { // otherwise just use the current frame number
			index = currentFrame;
		}
		
		// figure out where in the image the frame is located
		int srcStartX = frameSize.width * index;
		int srcStartY = 0;
		while(srcStartX >= image.getWidth()){
			srcStartX -= image.getWidth();
			srcStartY += frameSize.height;
		}
		
		// draw the frame from the image
		g.drawImage(image, -frameSize.width/2, -frameSize.height/2,
				frameSize.width/2, frameSize.height/2,
				srcStartX, srcStartY, 
				srcStartX + frameSize.width, srcStartY + frameSize.height, null);
		
		
		// untransform 
		getPosition().untransform(g);
	}

	public void restart() {
		currentFrame = 0;
	}

	/*
	 * Setters and Getters for private instance variables. Please add other
	 * methods above these so they are easier to find.
	 */

	public void setVisible(boolean b){
		visible = b;
	}
	
	public boolean getVisible(){
		return visible;
	}
	
	public Size getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(int width, int height) {
		frameSize.setWidth(width);
		frameSize.setHeight(height);
	}

	public int getFrameWidth() {
		return frameSize.getWidth();
	}

	public int getFrameHeight() {
		return frameSize.getHeight();
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int newDelay) {
		delay = newDelay;
		delayLeft = delay;
	}

	public int getNumberFrame() {
		return numberFrames;
	}
	
	public void setNumberFrames(int num){
		numberFrames = num;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int newCurrentFrame) {
		currentFrame = newCurrentFrame;
	}

	public void setFrameIndicies(int[] indicies) {
		frameIndicies = indicies;
		numberFrames = indicies.length;
	}

	private class Size implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -4314896165222015097L;
		private int width, height;

		public Size(int newWidth, int newHeight) {
			width = newWidth;
			height = newHeight;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int newWidth) {
			width = newWidth;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int newHeight) {
			height = newHeight;
		}
	}
	
	
}
