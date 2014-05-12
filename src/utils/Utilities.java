package utils;

public class Utilities {
	
	public static double angleBetween(double srcX, double srcY, double endX, double endY){
		double dx = endX - srcX;
		double dy = endY - srcY;
		
		double angle;
		
		if(dx != 0){
			angle = Math.atan(dy/dx);
			if(dx < 0)
				angle += Math.PI;
		} else {
			if(dy > 0)
				angle = Math.PI/2;
			else
				angle = Math.PI/2*3;
		}
		
		return angle;
	}

}
