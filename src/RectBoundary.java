import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

/**
 * 
 * @author JamesGomatos
 * This class is used to create the BoundingBox for each game object displayed
 * during the game.
 * 
 */
public abstract class RectBoundary implements BoundingBox
{
	private double xP;
	private double yP;
	private Rectangle boundingBox;
	public static final double DEFAULT_WIDTH = 2;
	public static final double DEFAULT_HEIGHT = 2;

	/**
	 * constructor which defines default parameters
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public RectBoundary(double x, double y, double width, double height) {
		xP = x;
		yP = y;

		boundingBox = new Rectangle(0, 0, 0, 0);
		setBoundingBox(x, y, width, height);
	}

	
	/**
	 * Getter for X coordinate
	 * @return
	 */
	public final double getXPos() {
		return xP;
	}


	/**
	 * Getter for Y coordinate
	 * @return
	 */
	public final double getYPos() {
		return yP;
	}

	
	/**
	 * getter function
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	/**
	 * retrieve height of boundingBox
	 */
	public double getBoundingBoxHeight() {
		return boundingBox.getHeight();
	}

	/**
	 * retrieve width of boundingBox
	 */
	public double getBoundingBoxWidth() {
		return boundingBox.getWidth();
	}

	/**
	 * Getter for Max BoundingBox X coordinate
	 */
	public double getBoundingBox_Max_X() {
		return boundingBox.getMaxX();
	}
	
	/**
	 * Getter for Max boundingBox Y coordinate 
	 */
	public double getBoundingBox_Max_Y() {
		return boundingBox.getMaxY();
	}

	/**
	 * Getter for Minimum BoundingBox X coordinate
	 */
	public double getBoundingBox_Min_X() {
		return boundingBox.getMinX();
	}

	/**
	 * Getter for Minimum BoundingBox Y coordinate
	 */
	public double getBoundingBox_Min_Y() {
		return boundingBox.getMinY();
	}

	/**
	 * Setter function 
	 */
	public void setBoundingBox(double xP, double yP, double width, double height) {
		boundingBox.setRect(xP, yP, width, height);
		this.xP = xP;
		this.yP = yP;
	}

	/**
	 * Same as setBoundingBox but only takes two parameters X and Y
	 */
	public void updateBoundingBox(double xP, double yP) {
		boundingBox.setRect(xP, yP, getBoundingBoxWidth(),
				getBoundingBoxHeight());
		this.xP = xP;
		this.yP = yP;
	}


	/**
	 * Function Checks if there is a collision or intersection
	 * with another box
	 */
	public boolean intersectsBoundingBox(Rectangle other) {
		return boundingBox.intersects(other);
	}


	public void draw(Graphics g) {
		g.setColor(Color.RED);
		// cast to 2d and draw
		((Graphics2D) g).draw(boundingBox);
	}
}
