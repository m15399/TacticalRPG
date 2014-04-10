package src.view;

public class AnimatedSprite extends Sprite{
	private Size frameSize;
	private int delay, numberFrames, currentFrame;
	

	public AnimatedSprite(int frameWidth, int frameHeight){
		super();
		frameSize = new Size(frameWidth, frameHeight);
		//TODO implement
	}
	
	/*
	 * Setters and Getters for private instance variables.  Please add other methods above these so they are easier to find.
	 */
	
	public Size getFrameSize(){
		return frameSize;
	}
	
	public void setFrameSize(int width, int height){
		frameSize.setWidth(width);
		frameSize.setHeight(height);
	}
	
	public int getFrameWidth(){
		return frameSize.getWidth();
	}
	
	public int getFrameHeight(){
		return frameSize.getHeight();
	}
	
	public int getDelay(){
		return delay;
	}
	
	public void setDelay(int newDelay){
		delay = newDelay;
	}
	
	public int getNumberFrame(){
		return numberFrames;
	}
	
	public void setNumberFrames(int newNumberFrames){
		numberFrames = newNumberFrames;
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public void setCurrentFrame(int newCurrentFrame){
		currentFrame = newCurrentFrame;
	}
	
	private class Size{
		
		private int width, height;
		
		public Size(int newWidth, int newHeight){
			width = newWidth;
			height = newHeight;
		}
		
		public int getWidth(){
			return width;
		}
		
		public void setWidth(int newWidth){
			width = newWidth;
		}
		
		public int getHeight(){
			return height;
		}
		
		public void setHeight(int newHeight){
			height = newHeight;
		}
	}
}
