package model;

/*
 * Position class that has info about an objects
 * location, rotation, and scale
 */
public class Position {

	private double x, y;
	private double sx, sy;
	private double rotation;
	private boolean mirrored;
	
	public Position(double x, double y) {
		init();
		setLocation(x, y);
	}

	private void init() {
		x = y = 0;
		sx = sy = 1;
		rotation = 0;
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getScaleX(){
		return sx;
	}
	
	public double getScaleY(){
		return sy;
	}
	
	public double getRotation(){
		return rotation;
	}
	
	public boolean getMirrored(){
		return mirrored;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setLocation(double x, double y) {
		setX(x);
		setY(y);
	}

	public void moveBy(double x, double y) {
		setLocation(this.x + x, this.y + y);
	}

	public void setRotation(double r) {
		rotation = r;
	}

	public void rotateBy(double r) {
		setRotation(rotation + r);
	}

	public void setScale(double sx, double sy) {
		this.sx = sx;
		this.sy = sy;
	}
	
	public void setMirrored(boolean m){
		mirrored = m;
	}
	
	public void mirror(){
		mirrored = !mirrored;
	}

	public void scaleBy(double sx, double sy) {
		setScale(this.sx * sx, this.sy * sy);
	}

}
