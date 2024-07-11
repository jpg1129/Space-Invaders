import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * This class handles the cluster of aliens
 * @author JamesGomatos
 *
 */
public class AlienRectangleGroup extends AlienGroup
{
	// instance variables
	private Alien rightAlien;
	private Alien leftAlien;
	private Alien bottomAlien;
	private double xSpeed;
	private double ySpeed;
	private double firingRate;
	private double shotSpeed;
	private int shotNum;
	private int alienCounter;
	private Alien[][] aliens;
	private Game game;
	private int columns;
	private int rows;
	private int shotLimit;
	private final int SPAC = 20;

	/**
	 * Constructor for the group of invading aliens
	 * @param rows
	 * @param columns
	 * @param alienWidth
	 * @param alienHeight
	 * @param xSpeed
	 * @param ySpeed
	 * @param fireRate
	 * @param shotLimit
	 * @param alienImage
	 * @param alienLaserImage
	 * @param shotSpeed
	 * @param game
	 */
	public AlienRectangleGroup(int rows, int columns, int alienWidth, int alienHeight,
			double xSpeed,double ySpeed,double fireRate, int shotLimit, 
			Image alienImage, Image alienLaserImage,double shotSpeed, Game game)
	{
		super(rows, columns, alienWidth, alienHeight, xSpeed, 
				ySpeed, fireRate, shotLimit, alienImage, alienLaserImage, shotSpeed, game);
		
		this.game = game;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.columns = columns;
		this.rows = rows;
		this.shotLimit = shotLimit;
		this.firingRate = fireRate;
		this.shotSpeed = shotSpeed;
		this.shotNum = 0;

		aliens = new Alien[rows][columns];
		
		// set each alien with the properties of the group
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				aliens[i][j] = new Alien(SPAC + j * (alienWidth + SPAC),
						SPAC + i * (alienHeight + SPAC), alienWidth,
						alienHeight, xSpeed, ySpeed, alienImage, alienLaserImage, game);
			}
		}

		setAllAliens(aliens);
		this.alienCounter = rows * columns;
		this.rightAlien = aliens[0][columns -1];
		this.leftAlien = aliens[0][0];
		this.bottomAlien = aliens[rows - 1][columns - 1];
	}
	


	/**
	 * This method is responsible for moving the cluster of aliens
	 */
	public void autoMove(double delta){
		double yS = 0;
		double xS = xSpeed;

		if (!rightAlien.isAlive())
			rightAlien = findRightAlien();
		if (!leftAlien.isAlive())
			leftAlien = findLeftAlien();
		if (!leftAlien.isAlive())
			leftAlien = BottomAlien();
		
		if (rightAlien.getBoundingBox_Max_X() + xSpeed >= game.getContentPanelWidth()){
			xSpeed = -xSpeed;
			xS = xSpeed;
			// if alien out of bounds
			if (rightAlien.getBoundingBox_Max_X() + xSpeed > game.getContentPanelWidth())	{
				// reflect if out of bounds
				xS = game.getContentPanelWidth() - rightAlien.getBoundingBox_Max_X();	
				if (delta < 1)
					xS = xS/delta;
			}

			yS = this.ySpeed;
		}
		if (leftAlien.getXPos() + xSpeed <= 0)	{
			xSpeed = -xSpeed;
			xS = xSpeed;
			// if alien out of bounds on left
			if (leftAlien.getXPos() + xSpeed < 0)	{
				xS = -leftAlien.getXPos();		
				if (delta < 1)
					xS = xS/delta;
			}

			yS = this.ySpeed;
		}
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				aliens[i][j].autoMoveTwo(delta, xS, yS);
			}
		}
	}
	
	
	/**
	 * this function gets the aliens current alive
	 */
	public ArrayList<Alien> getAliveAliens() {
		ArrayList<Alien> a = new ArrayList<Alien>();

		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				if (aliens[i][j] != null && aliens[i][j].isAlive())
					a.add(aliens[i][j]);

		return a;
	}
	
	/**
	 * draws the individual aliens
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < rows; ++i)
			for (int j = 0; j < columns; ++j)
				if (aliens[i][j] != null && aliens[i][j].isAlive())
					aliens[i][j].draw(g);
	}


	/**
	 * This method is responsible for shooting
	 */
	public ArrayList<AlienMissile> shoot() {
		ArrayList<AlienMissile> missiles = new ArrayList<AlienMissile>();
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				if (aliens[i][j] != null && aliens[i][j].isAlive()) {
					// generate random number
					int random = (int)(Math.random() * 500) ;
					if (random < firingRate && shotNum <= shotLimit) {
						missiles.add(aliens[i][j].shoot(shotSpeed));
						shotNum++;
					}
				}
			}
		}

		return missiles;
	}
	
	/**
	 * THESE ARE GETTER AND SETTER BASIC FUNCTIONS
	 */
	
	@Override
	public Alien getBottomAlien() {
		return bottomAlien;
	}

	@Override
	public Alien getRightAlien() {
		return rightAlien;
	}

	@Override
	public Alien getLeftAlien() {
		return leftAlien;
	}
	
	@Override
	public int getAlienCounter() {
		return alienCounter;
	}
	
	@Override
	public void setAlienCounter(int x) {
		alienCounter = x;
	}
	
	@Override
	public void decrementAlienCounter()  {
		alienCounter = alienCounter - 1;
	}
	
	@Override
	public void resetShotCounter() {
		shotNum = 0;
	}
	
	public void increaseSpeed() {
		ySpeed = ySpeed +  1;
		xSpeed += xSpeed > 0 ? 1 : -1;
	}

	
	public void decrementShotCounter() {
		shotNum = shotNum -1;
	}

}
