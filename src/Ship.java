import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

/**
 * this class creates the player controlled gameObject
 * which is the ship. it creates, sets the movement, and enables
 * other properties for the ship
 * @author JamesGomatos
 *
 */
public class Ship extends GameObject
{
	private double x;
	private double y;
	private int projectedSpeed;
	private int lives;
	private double xSpeed;
	private Game game;
	private Image shipImage;
	private Image MissileImage;
	private double previousMouse;
	private Rectangle2D.Double ship;
	public boolean FiredShot;


	/**
	 * constructor which creates a ship
	 * @param xP
	 * @param yP
	 * @param shapeWidth
	 * @param shapeHeight
	 * @param xSpeed
	 * @param ySpeed
	 * @param shipImage
	 * @param Image
	 * @param lives
	 * @param game
	 */
	public Ship(double xP, double yP, double gameObjectWidth,
			double gameObjectHeight, double xSpeed, double ySpeed, 
			Image shipImage, Image Image,int lives,
			Game game)
	{
		super(xP, yP, gameObjectWidth, gameObjectHeight, xSpeed, ySpeed, game);
		this.x = xP;
		this.y = yP;
		this.shipImage = shipImage;
		// lives of the player has 
		this.lives = lives; 
		// set the size
		this.ship = new Rectangle2D.Double(xP, yP, gameObjectWidth, gameObjectHeight);
		// set our default speed
		projectedSpeed = 9; 
		this.xSpeed = xSpeed;
		this.game = game;
		this.MissileImage = Image;
	}


	/**
	 * this function shoots the missile
	 * @return
	 */
	public ShipMissile shoot() {
		double projHeight = 5;
		double projWidth = 5;


		return new ShipMissile(getXPos() + (int) (getBoundingBoxWidth() / 2),
				getYPos() - projHeight, projWidth, projHeight, 0, 
				projectedSpeed, MissileImage, getGame());
	}


	/**
	 * move the ship according to mouse 
	 * movements made by the player 
	 */
	public void playerMove(double x, double y) {
		this.x = x;
		boolean rightBounds = x + this.getBoundingBoxWidth() / 2 >= getGame().getContentPanelWidth();
		if (rightBounds) {
			this.x = getGame().getContentPanelWidth() - getBoundingBoxWidth();
		}
		if (this.getBoundingBox_Min_X() <= 0) {
			this.x = 0;
		}

		// update the new area
		updateBoundingBox((int) x, this.y);
	}
	
	/**
	 * play sound and reduce life if ship has collision
	 */
	@Override
	public void collisionResponse(GameObject random) {
		if(random instanceof Alien) {
			// the player loses
			this.lives = 0;
		} else if (random instanceof AlienMissile) {
			// play the explosion sound
			SoundEffect.SHIPDIE.play();
			// reduce the number of lives
			this.lives = this.lives - 1;
			if (this.lives <= -1)	{
				// the player loses
				game.isGameOver = true;
				// play the sound effects
				SoundEffect.BGM.endLoop();
				SoundEffect.EXPLODE.play();
			}
		}
	}
	
	public void setPrevMouseX(double x) {
		previousMouse = x;
	}
	
	/**
	 * draws the ship
	 */
	public void draw(Graphics g) {
		g.drawImage(shipImage, (int) x, (int) y, (int)getBoundingBoxWidth(), 
				(int)getBoundingBoxHeight(), game);
	}

	/**
	 * Check if ship gets hit
	 */
	@Override
	public boolean collidesWith(GameObject random) {
		boolean flag = random instanceof AlienMissile && this.intersectsBoundingBox(random.getBoundingBox());
		return flag;
	}
	
	/**
	 * function reduces the number of lives
	 */
	public void reduceLife() {
		lives = lives - 1;
	}
	
	/**
	 * set number of player lives
	 * @param lives
	 */
	public void setNumLife(int num) {
		this.lives = num;
	}
	
	/**
	 * getter for lives
	 * @return
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * retrieve image attached to ship
	 * @return
	 */
	public Image getImage() {
		return shipImage;
	}
	
	// NOT USED 
	public void autoMove(double delta) {
	}
}


