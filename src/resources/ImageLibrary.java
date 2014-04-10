package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLibrary {

	// files to load
	private static final String[] files = {
		"test.png"
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
		return images.get(filename);
	}

	private void loadImage(String filename) {
		try {
			BufferedImage img = ImageIO.read(new File("images" + File.separator
					+ filename));
			images.put(filename, img);
		} catch (IOException e) {
			System.out.println("Error loading image: " + filename);
		}
	}

}
