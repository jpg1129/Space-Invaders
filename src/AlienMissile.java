import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;

/**
 * This class handles any collisions that occur between the alien
 * missile and the player
 * @author JamesGomatos
 *
 */
public class AlienMissile extends Missile
{
	// instance variables
	private Image image;
	private double x;
	private double y;
	private Game game;
	
	/**
	 * Constructor for the alien missile
	 * @param xP
	 * @param yP
	 * @param gameObjectWidth
	 * @param gameObjectHeight
	 * @param xSpeed
	 * @param ySpeed
	 * @param image
	 * @param game
	 */
	public AlienMissile(double xP, double yP, double gameObjectWidth, double gameObjectHeight,
			double xSpeed, double ySpeed, Image image, Game game)
	{
		super(xP, yP, gameObjectWidth, gameObjectHeight, xSpeed, ySpeed, image, game);
		this.x = xP;
		this.y = yP;
		this.image = image;
		this.game = game;
	}
	
	/**
	 * checks bounds
	 * @return
	 */
	public boolean isOutsideBounds() {
		return getYPos() > getGame().getContentPanelHeight() - 100;
	}
	
	/**
	 * Moves the alien missile
	 */
	public void autoMove(double delta) {
		y = y + getySpeed() *delta;
		updateBoundingBox((int) x, (int) y);
	}


	/**
	 * Checks if missile collides with player
	 */
	public boolean collidesWith(GameObject random) {
		boolean flag = random instanceof Ship && this.intersectsBoundingBox(random.getBoundingBox());
		return flag;
	}


	/**
	 * Draws the missile
	 */
	public void draw(Graphics g) {
		g.drawImage(image, (int)x, (int)y, (int)getBoundingBoxWidth(), (int)getBoundingBoxHeight(), game);
	}

	
}
