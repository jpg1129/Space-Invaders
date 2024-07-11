import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.Timer;

/**
 * Creates the missile class which are fired by gameObjects
 * @author JamesGomatos
 *
 */
public class Missile extends GameObject
{
	private double x;
	private double y;
	private Game game;
	private Image image;

	/**
	 * constructor for the Missile
	 * @param xP
	 * @param yP
	 * @param shapeWidth
	 * @param shapeHeight
	 * @param xSpeed
	 * @param ySpeed
	 * @param image
	 * @param game
	 */
	public Missile(double xP, double yP, double shapeWidth,
			double shapeHeight, double xSpeed, double ySpeed, Image image, Game game) 
	{
		super(xP, yP, shapeWidth, shapeHeight, xSpeed, ySpeed, game);

		this.x = xP;
		this.y = yP;
		this.image = image;
		this.game = game;
	}


	/**
	 * Moves the missile
	 */
	@Override
	public void autoMove(double delta) {
		y += getySpeed();
		updateBoundingBox(x, y);
	}

	/**
	 * Checks if a gameObject collides 
	 *  with another gameObject returning a boolean flag
	 */
	@Override
	public boolean collidesWith(GameObject random) {
		boolean flag = random instanceof Alien && this.intersectsBoundingBox(random.getBoundingBox());
		return flag;
	}

	
	/**
	 * draws the missile
	 */
	public void draw(Graphics g) {
		g.drawImage(image, (int) x, (int) y, (int) getBoundingBoxWidth(), 
				(int) getBoundingBoxHeight(), game);
	}

	// NOT USED 
	@Override
	public void playerMove(double x, double y) {
	}
	@Override
	public void collisionResponse(GameObject other) {
	
	}
}
