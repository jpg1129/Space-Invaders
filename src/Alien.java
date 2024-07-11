import java.awt.Graphics;
import java.awt.Image;
/**
 * this class represents an alien invader, it creates, sets the movement, and enables
 * other properties for a alien.
 * @author JamesGomatos
 *
 */
public class Alien extends GameObject
{
	// instance variables
	private Game game;
	private double x;
	private double y;
	private boolean isAlive;
	private Image alienImage;
	private Image alienMissileImage;

	public Alien(double xP, double yP, double gameObjectWidth, double gameObjectHeight,
			double xSp, double ySp, Image alienImage, Image alienMisImage, Game game)
	{
		super(xP, yP, gameObjectWidth, gameObjectHeight, xSp, ySp, game);

		this.x = xP;
		this.y = yP;
		isAlive = true;
		this.alienImage = alienImage;
		this.alienMissileImage = alienMisImage;
		this.game = game;
	}

	/**
	 * This method is responsible for updating of the boundingBox
	 * on the alien
	 */
	public void autoMove(double delta) {
		if (isAlive) {
			super.checkParameter(getXPos(), getYPos());
			// calculate movement
			double x = getXPos() + getxSpeed() * delta;
			updateBoundingBox((int) x, getYPos());
		}
	}


	/**
	 * move the alien with regards to
	 * speed and time
	 * @param delta
	 * @param xSpeed
	 * @param ySpeed
	 */
	public void autoMoveTwo(double delta, double xSp, double ySp) {
		if (isAlive) {
			x = getXPos() + xSp * delta;
			y = getYPos() + ySp;
			updateBoundingBox(Math.round(x), (int) y);
		}
	}
	
	/**
	 * shoots an alien missile 
	 * @param ySpeed
	 * @return
	 */
	public AlienMissile shoot(double ySp) {
		double pHeight = 15;
		double pWidth = 15;

		if (isAlive) {
			return (new AlienMissile(getXPos() + (getBoundingBoxWidth() / 2),
					getYPos() + pHeight, pWidth, pHeight, 0, ySp, 
					alienMissileImage, getGame()));
		}

		return null;
	}
	

	/**
	 * checks if alien gets hit by a ship missile
	 */
	@Override
	public boolean collidesWith(GameObject random) {
		boolean flag = random instanceof ShipMissile && this.intersectsBoundingBox(random.getBoundingBox());
		if (flag) {
			isAlive = false;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Handles the collision response for alien
	 */
	@Override
	public void collisionResponse(GameObject random) {
		// Check if game is over
		if(random instanceof Ship) {
			SoundEffect.BGM.endLoop();
			SoundEffect.EXPLODE.play();
			game.isGameOver = true;
		} else if (random instanceof ShipMissile) {
			// destroy alien
			this.isAlive = false;
			game.group.decrementAlienCounter();
			SoundEffect.ALIEN.play();
			game.setScore(50);
			if (game.group.getAlienCounter() % game.getSpeedDifficulty() == 0) {
				game.group.increaseSpeed();
			}
			if (game.group.getAlienCounter() == 0) {
				if (game.levelIterator.hasNext()) {
					game.waitingForLevel = true;
				}
				else {
					game.playerWon = true;
					game.isGameOver = true;
					SoundEffect.BGM.endLoop();
					SoundEffect.WIN.play();
				}
			}
		}
	}



	/**
	 * draws the alien
	 */
	public void draw(Graphics g) {
		if (isAlive)
			g.drawImage(alienImage, (int) x, (int) y, (int) getBoundingBoxWidth(), 
					(int) getBoundingBoxHeight(), game);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setLife(boolean life) {
		isAlive = life;
	}

	/**
	 * NOT USED
	 */
	@Override
	public void playerMove(double x, double y)
	{
	}
}
