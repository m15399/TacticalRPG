package input;

public class TestButton extends Button {
	
	public TestButton(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	
	public void mousePressed(){
		System.out.println("Pressed");
	}
	public void mouseReleased(){
		System.out.println("Released");
	}
	public void mouseDragged(){
//		System.out.println("Dragged");
	}
	public void mouseHovered(){
//		System.out.println("Hovered");
	}
	public void mouseExited(){
		System.out.println("Exited");
	}
}
