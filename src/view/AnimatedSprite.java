package view;

import java.awt.Graphics;

/*
 * Sprite that runs an animation from a sprite sheet
 */
public class AnimatedSprite extends Sprite {
	private Size frameSize;
	private int delay, delayLeft, numberFrames, currentFrame;
	private int[] frameIndicies;

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

	private class Size {

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
