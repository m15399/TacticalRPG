package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import model.GameObject;

/*
 * Background starfield
 */
public class Starfield extends GameObject {

	private class Star {
		double x, y, z, size;

		public Star(double x, double y, double z, double size) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.size = size;
		}

		public void draw(Graphics g, double offsetX, double offsetY,
				double offsetZ) {
			g.setColor(Color.white);

			double nz = z + offsetZ;

			// size gets smaller based on depth
			int ns = (int) Math.ceil(size / nz);

			double nx = x / nz + offsetX;
			double ny = y / nz + offsetY;

			g.fillRect((int) nx, (int) ny, ns, ns);
		}

	}

	ArrayList<Star> stars;
	double offsetX, offsetY, offsetZ;
	double width, height;
	int numStars;

	public Starfield(double width, double height, double minDepth,
			double maxDepth, double minSize, double maxSize, int numStars) {
		
		stars = new ArrayList<Star>();

		offsetX = width / 2;
		offsetY = height / 2;
		offsetZ = 0;

		this.width = width;
		this.height = height;

		this.numStars = numStars;

		// plot stars randomly in a frustrum (such math)
		for (int i = 0; i < numStars; i++) {
			// choose a depth
			double z = Math.random() * (maxDepth - minDepth) + minDepth;
			
			// bounds of star at that specific depth
			double minX = minX(z);
			double maxX = maxX(z);
			double minY = minY(z);
			double maxY = maxY(z);

			// plot randomly within bounds
			double x = Math.random() * (maxX - minX) + minX;
			double y = Math.random() * (maxY - minY) + minY;
			double size = Math.random() * (maxSize - minSize) + minSize;

			stars.add(new Star(x, y, z, size));
		}

	}

	private double minX(double z) {
		return -width / 2 * z;
	}

	private double maxX(double z) {
		return width / 2 * z;
	}

	private double minY(double z) {
		return -height / 2 * z;
	}

	private double maxY(double z) {
		return height / 2 * z;
	}

	private double widthAt(double z) {
		return width * z;
	}

	private double heightAt(double z) {
		return height * z;
	}

	/*
	 * Scroll the starfield by moving all the stars
	 */
	public void scrollBy(double x, double y) {
		for (Star s : stars) {

			double z = s.z;

			s.x += x;
			s.y += y;

			/*
			 * check if the stars went out of bounds, 
			 * move them to the other side if they did
			 */
			if (s.x < minX(z))
				s.x += widthAt(z);
			else if (s.x > maxX(z))
				s.x -= widthAt(z);

			if (s.y < minY(z))
				s.y += heightAt(z);
			else if (s.y > maxY(z))
				s.y -= heightAt(z);

		}
	}

	public void draw(Graphics g) {
		for (int i = 0; i < numStars; i++) {
			stars.get(i).draw(g, offsetX, offsetY, offsetZ);
		}
	}

}
