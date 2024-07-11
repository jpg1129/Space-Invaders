import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * The GameObject class is used to handle collision/movement depending
 * on the properties of its subclasses 
 * @author JamesGomatos
 *
 */
public abstract class GameObject extends RectBoundary
{
	// Define instance variables
	private double xSpeed;
	private double ySpeed;
	private Game game;

	// constructor for GameObject
	public GameObject(double xP, double yP, double gameObjectWidth,
			double gameObjectHeight, double xSpeed, double ySpeed, Game game)
	{
		super(xP, yP, gameObjectWidth, gameObjectHeight);
		setSpeed(xSpeed, ySpeed);
		this.game = game;
	}

	/**
	 *  This function checks and sees if any objects 
	 *  are outside the content pane
	 * @param x
	 * @param y
	 */
	public void checkParameter(double x, double y) {
		// get the screen length
		int screenWidth = game.getContentPanelWidth();
		int screenHeight = game.getContentPanelHeight();

		// check the x-axis
		int outside = (int)(getBoundingBoxWidth() - screenWidth);
		outside += x;
		if (x <= 0) {
			x = 0;
			xSpeed = -xSpeed;
		} else if (outside > 0){
			x = screenWidth - getBoundingBoxWidth();
			xSpeed = -xSpeed;
		}
		
		//  check the y-axis
		outside = (int)(getBoundingBoxHeight() / 2 - screenHeight);
		outside += y;
		if (y <= 0) {
			y = 0;
			ySpeed = -ySpeed;
		} else if (y >= screenWidth) {
			y = screenHeight - getBoundingBoxHeight();
			ySpeed = -ySpeed;
		}
		// place the shape in a new position
		updateBoundingBox(x, y);
	}
	

	/**
	 * Getter
	 * @return
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Set the speed of the object
	 * @param xSpeed
	 * @param ySpeed
	 */
	public void setSpeed(double xS, double yS) {
		this.xSpeed = xS;
		this.ySpeed = yS;
	}

	/**
	 * getter
	 * @return
	 */
	public double getySpeed() {
		return ySpeed;
	}
	
	public double getxSpeed() {
		return xSpeed;
	}

	/**
	 * Draw  method
	 */
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	/**
	 * detect collision
	 * @param other
	 * @return
	 */
	abstract public boolean collidesWith(GameObject other);
	
	/**
	 * responds to collision
	 * @param other
	 */
	abstract public void collisionResponse(GameObject other);

	/**
	 * automatically move the gameObjects
	 * @param delta
	 */
	abstract public void autoMove(double delta);

	/**
	 * move the player gameObject
	 * @param x
	 * @param y
	 */
	abstract public void playerMove(double x, double y);

	
}
