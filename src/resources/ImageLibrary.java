package resources;

import java.awt.image.BufferedImage;
import java.io.File;
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
		"scout.png",
		"scout_red.png",
		"fighter.png",
		"fighter_red.png",
		"battlecruiser.png",
		"battlecruiser_red.png",
		"bomber.png",
		"bomber_red.png",
		"sniper.png",
		"sniper_red.png",
		"sniperbeam.png",
		"repairship.png",
		"repairship_red.png",
		"healbeam.png",
		"mothership.png",
		"mothership_red.png",
		"mine.png",
		"mine_red.png",
		"mine_top_on.png",
		"mine_top_off.png",
		"warpgate.png",
		"warpgate_red.png",
		"jet_37x16.png",
		"jet_19x9.png",
		"guns_36x17.png",
		"guns_19x7.png",
		"speedboost.png",
		"asteroids.png",
		"scrapmetal.png",
		"MagneticShield.png",
		"planet2x2.png", //Courtesy of Nasa -- http://www.ehdwalls.com/1280x1024/space/nasa_-_neptune/
		"wall_bottom.png",
		"wall_horizontal.png",
		"wall_left.png",
		"wall_right.png",
		"wall_top.png",
		"wall_vertical.png",
		"asteroidfield.png",
		"spacewreckage.png",//placeholder image
		"gascloud.png",
		"radioactive.png",
		"spacewreckage1x3.png",
		"map1.jpg",
		"map2.jpg",
		"map3.jpg",
		"map4.jpg",
		"shiptitlemenu2.png",
		"title2.png",
		"thegame.png"
		
		};
	
	/*
	 * Singleton class (only one instance in program)
	 */
	private static ImageLibrary instance = null;
	public static synchronized ImageLibrary getInstance() {
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

//	public BufferedImage getRandomImageFromSheet(String filename){
//		BufferedImage image = images.get(filename);
//		if(image == null){
//			System.out.println("Error getting random image: " + filename);
//			System.exit(1);
//		}
//		BufferedImage[] images = new BufferedImage[image.getWidth()/64];
//		int x = 0;
//		for(int i = 0; i < image.getWidth()/64; i++){
//			images[i] = image.getSubimage(x, 0, 64, 64);
//			x += 64;
//		}
//		Random random = new Random();
//		int rand = random.nextInt(images.length);
//		return images[rand];
//	}
//	
	private void loadImage(String filename) {
		try {
			BufferedImage img = ImageIO.read(new File("images/" + filename));
//			BufferedImage img = ImageIO.read(ImageLibrary.class.getResource("/images/" + filename));
			images.put(filename, img);
		} catch (IOException e) {
			System.out.println("Error loading image: " + filename);
		}
	}
}
