import java.awt.Image;

/**
 * This class Holds all game objects.
 * @author JamesGomatos
 *
 */
public class Level
{
	private Game game;
	private AlienGroup group;
	private Ship ship;
	private int levelNumber;
	private int shotDelay;
	private Image image;
	private double difficulty;
	
	/**
	 * 
	 * @param game
	 * @param cluster
	 * @param ship
	 * @param shotDelay
	 * @param levelNumber
	 * @param image
	 * @param difficulty
	 */
	public Level(AlienGroup cluster, Ship ship, int shotDelay,
			int levelNumber, Image image, double difficulty, Game sgame)
	{
		this.game = game;
		this.group = cluster;
		this.ship = ship;
		this.levelNumber = levelNumber;
		this.shotDelay = shotDelay;
		this.image = image;
		this.difficulty = difficulty;
	}
	
	
	/**
	 * Functions needed to run the game
	 * @return
	 */

	public double getDifficulty() {
		return difficulty;
	}

	public Image getImage() {
		return image;
	}

	public AlienGroup getAlienGroup() {
		return group;
	}

	public Ship getShip() {
		return ship;
	}

	public int getLevelNumber(){
		return levelNumber;
	}

	public int getShotDelay() {
		return shotDelay;
	}
}
