import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/**
 * This class provides functionality for any collision that occurs between
 * the player missile and the invader.
 * @author JamesGomatos
 *
 */
public class ShipMissile extends Missile
{
	// instance variables
	private Image image;
	private double x, y;
	private Game game;
	
	/**
	 * Constructor for missile
	 * @param xPos
	 * @param yPos
	 * @param gameObjectWidth
	 * @param gameObjectHeight
	 * @param xSpeed
	 * @param ySpeed
	 * @param image
	 * @param game
	 */
	public ShipMissile(double xP, double yP, double gameObjectWidth, double gameObjectHeight,
			double xSpeed, double ySpeed, Image image, Game game)
	{
		super(xP, yP, gameObjectWidth, gameObjectHeight, xSpeed, ySpeed, image, game);
		this.image = image;
		this.x = xP;
		this.y = yP;
		this.game = game;
	}
	
	/**
	 * Checks bounds
	 * @return
	 */
	public boolean isOutsideBounds() {
		return this.getBoundingBox_Max_Y() < 0;
	}
	
	/**
	 * Method moves the missile updating its RectBoundary/BoundBox each time
	 */
	public void autoMove(double delta) {
		y -= getySpeed() * delta;
		updateBoundingBox(x, (int)y);
	}


	/**
	 * Checks if missile collides 
	 *  with another gameObject returning a boolean flag
	 */
	@Override
	public boolean collidesWith(GameObject random) {
		boolean flag = random instanceof Alien && this.intersectsBoundingBox(random.getBoundingBox());
		return flag;
	}
	
	/**
	 * draws the ship missile
	 */
	public void draw(Graphics g) {
		g.drawImage(image, (int) x, (int) y, (int) getBoundingBoxWidth(), 
				(int)getBoundingBoxHeight(), game);
	}

	
}
