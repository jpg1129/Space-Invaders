import java.awt.Toolkit;
import java.io.File;
import java.util.LinkedList;
import java.awt.Image;
import java.awt.MediaTracker;
/**
 * This function is where the two levels are generated
 * @author JamesGomatos
 *
 */
public class MakeLevels
{
	// instance variables
	private Game game;
	private Image alienImage;
	private Image shipImage;
	private Image levelImage;
	private Image ship_Missle_Image;
	private Image alien_Missle_Image;
	private LinkedList<Level> levels;

	/**
	 * constructor for the class
	 * @param g
	 */
	public MakeLevels(Game ok) {
		// initialize the different images
		shipImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "objects" + File.separator + "ship.png");
		alienImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "objects" + File.separator + "ufo.gif");
		levelImage = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "objects" + File.separator + "background.png");
		ship_Missle_Image = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "objects" + File.separator + "missile2.png");
		alien_Missle_Image = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "objects" + File.separator + "missile.png");

		try {
			// The MediaTracker class is a utility class to track the 
			// status of a number of media objects.
			MediaTracker tracker = new MediaTracker(ok);
			tracker.addImage(shipImage, 0);
			tracker.addImage(alienImage,1);
			// background image
			tracker.addImage(levelImage,2);
			tracker.addImage(ship_Missle_Image, 3);
			tracker.addImage(alien_Missle_Image, 4);
			tracker.waitForAll();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		game = ok;
	}

	/**
	 * Linked List that holds the two generated levels
	 * @return
	 */
	public LinkedList<Level> CreateLevels() {
		int shipHeight = 20;
		
		
		// Create a ship
		Ship ship = new Ship( 0, game.getContentPanelHeight() - shipHeight - 90, 45,
				shipHeight,0,0, shipImage, ship_Missle_Image,3, game);	
		
		AlienGroup groupOne = new AlienRectangleGroup(3, 6, 30, 30, 2, 10, 2, 8, alienImage, alien_Missle_Image, 3, game);
		AlienGroup groupTwo = new AlienRectangleGroup(3, 7, 30, 30, 2, 10, 2, 8, alienImage, alien_Missle_Image, 4, game);

		levels = new LinkedList<Level>();
		levels.add(new Level(groupOne, ship, 300, 1, levelImage, 0.6, game));
		levels.add(new Level(groupTwo, ship, 300, 2, levelImage, 0.5, game));
	

		return levels;
	}
}
