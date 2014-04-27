package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/*
 * Loads and stores all images at the start of the 
 * program and lets you access them by name
 */
public class ImageLibrary {

	// files to load at startup
	private static final String[] files = {
		"test.png",
		"test2.png",
		"smurf_sprite.png",
		"scout.png",
		"jet_37x16.png"
		};
	
	/*
	 * Singleton class (only one instance in program)
	 */
	private static ImageLibrary instance = null;
	public static ImageLibrary getInstance() {
		if (instance == null) {
			instance = new ImageLibrary();
		}
		return instance;
	}
	
	
	private HashMap<String, BufferedImage> images;
	
	private ImageLibrary(){
		images = new HashMap<String, BufferedImage>();
		
		for(int i = 0; i < files.length; i++){
			loadImage(files[i]);
		}
	}

	public BufferedImage getImage(String filename){
		BufferedImage image = images.get(filename);
		if(image == null){
			System.out.println("Error getting image: " + filename);
			System.exit(1);
		}
		return image;
	}

	private void loadImage(String filename) {
		try {
			BufferedImage img = ImageIO.read(ImageLibrary.class.getResource("/images/" + filename));
			images.put(filename, img);
		} catch (IOException e) {
			System.out.println("Error loading image: " + filename);
		}
	}

}
