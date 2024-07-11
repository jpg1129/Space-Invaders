import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;


/**
 * this method creates the group of aliens using a 
 * 2D array of rows and columns
 * @author JamesGomatos
 * 
 */
public abstract class AlienGroup
{
	private int rows;
	private int columns;
	private Alien[][] aliens;

	public AlienGroup(int rows,int columns, int alienWidth, int alienHeight,
			double xSpeed, double ySpeed,double fireRate, int shotLimit, Image alienImage,
			Image alienLaserImage,double shotSpeed, Game game)	
	{
		this.rows = rows;
		this.columns = columns;
		aliens = new Alien[rows][columns];
	}

	/**
	 * Finds the leftmost alien by 
	 * iterating through the 2d array
	 * @return
	 */
	public Alien findLeftAlien() {
		Alien current = null;
		
		for (int x = 0; x < rows; ++x) {
			for (int y = 0; y < columns; ++y) {
				if (current == null && aliens[x][y].isAlive()) {
					// last one
					current = aliens[x][y];
				} else if (aliens[x][y].isAlive() && aliens[x][y].getXPos() <= current.getXPos()) {
					// Found our leftmost alien
					current = aliens[x][y];
				}
			}
		}

		return current;
	}

	/**
	 * Finds the rightmost alien by
	 * iterating through the 2d array
	 * @return
	 */
	public Alien findRightAlien() {
		Alien current = null;

		for (int x = 0; x < rows; ++x) {
			for (int y = columns - 1; y >= 0; --y) {
				if (current == null && aliens[x][y].isAlive()) {
					// last one
					current = aliens[x][y];
				}
				else if (aliens[x][y].isAlive() && aliens[x][y].getXPos() >= current.getXPos()) {
					// Found our rightmost alien
					current = aliens[x][y];
				}
			}
		}
		return current;
	}

	/**
	 * returns the alien closest to the bottom
	 * @return
	 */
	public Alien BottomAlien() {
		Alien current = null;
		// start at bottom row
		for (int x = rows-1; x >= 0; --x) {
			// iterate through column normally
			for (int y = 0; y < columns; ++y) {
				if (current == null && aliens[x][y].isAlive()) {
					// last one
					current = aliens[x][y];
				}
				else if (aliens[x][y].isAlive() && aliens[x][y].getYPos() >= current.getYPos()) {
					// bottom
					current = aliens[x][y];
				}
			}
		}

		return current;
	}
	
	public void setAllAliens(Alien[][] aliens) {
		this.aliens = aliens;
	}

	public int getTotalNumAliens() {
		return rows * columns;
	}

	
	// abstract functions
	public abstract void setAlienCounter(int x);
	public abstract Alien getBottomAlien();
	public abstract Alien getRightAlien();
	public abstract Alien getLeftAlien();
	public abstract void autoMove(double delta);
	public abstract void increaseSpeed();
	public abstract void decrementShotCounter();
	public abstract void resetShotCounter();
	public abstract ArrayList<AlienMissile> shoot();
	public abstract void draw(Graphics g);
	public abstract ArrayList<Alien> getAliveAliens();
	public abstract void decrementAlienCounter();
	public abstract int getAlienCounter();

}
