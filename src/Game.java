import java.io.*;

import java.awt.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author JamesGomatos
 * The game class is responsible for creating the gameObjects,
 * using appropriate response actions when collision in the occur, and adding
 * the score.
 * 
 */
public class Game extends Screen
{
	// Instance Variables
	public Ship ship;
	public AlienGroup group;
	public Iterator<Level> levelIterator;
	public boolean waitingForLevel;
	public boolean isGameOver;	
	public boolean playerWon; 		
	private ArrayList<ShipMissile> shipMissiles;
	private ArrayList<AlienMissile> alienMissiles;
	private ArrayList<ShipMissile> shipMissilesQueue;
	private int Score;
	private Level currentLevel;
	private Image isaliveImage;
	private Input input;
	private Font dynamicFont;
	private int speedDifficulty;
	private String stringFps;
	private Graphics bufferGraphics;
	private Image imageOverlay;
	private Image bufferImage;
	private Image background;

	/**
	 * Constructor for the game class
	 * @param environmentWidth
	 * @param environmentHeight
	 */
	public Game(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);

		isGameOver = false;
		playerWon = false;
		stringFps = null;
		speedDifficulty = 0;
		shipMissiles = new ArrayList<ShipMissile>();
		shipMissilesQueue = new ArrayList<ShipMissile>();
		alienMissiles = new ArrayList<AlienMissile>();
		imageOverlay = Toolkit.getDefaultToolkit().getImage("res"+ File.separator + "objects"+ File.separator + "bigger.png");

		File f = new File ("res"+ File.separator + "fonts"+ File.separator + "ka1.ttf");
		FileInputStream in;
		try {
			in = new FileInputStream (f);
			dynamicFont = Font.createFont (Font.TRUETYPE_FONT, in).deriveFont(20f);
		}
		catch (Exception e) {
			e.printStackTrace();
			dynamicFont = new Font("serif",Font.PLAIN,10);
		}

		// create a MakeLevels class
		MakeLevels lg = new MakeLevels(this);
		loadAllLevels(lg.CreateLevels());
	}


	/**
	 * This is the main loop running as the user is playing the game. 
	 * The loop will continually update the game
	 */
	public void gameLoop() {

		long lastFpsTime = 0;
		int fps = 0;
		long lastLoopTime = System.nanoTime();
		final long BEST_TIME = 1000000000 / 60;

		while (!isGameOver) {
			SoundEffect.BGM.loop();
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ( (double) BEST_TIME);

			lastFpsTime += updateLength;
			fps++;

			if (lastFpsTime >= 1000000000){
				lastFpsTime = 0;
				fps = 0;
			}
			updateGame(delta);
			repaint();

			if (waitingForLevel) {
				nextLevel();
			}
			try {
				Thread.sleep((System.nanoTime()-lastLoopTime)/1000000 + 10);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		this.setCursorVisible();
	}

	
	
	/**
	 * This functions deals with moving objects/ detects collision, sets the score, and keeps track
	 * of whether the game is over or not
	 * @param delta
	 */
	private synchronized void updateGame(double delta)
	{
		// Game Over or Not
		if (waitingForLevel || isGameOver){
			return;
		}
		
		group.autoMove(delta);
		if (group.BottomAlien().getBoundingBox_Max_Y() >= ship.getYPos()) {
			// Play SOund effects
			SoundEffect.BGM.endLoop();
			SoundEffect.EXPLODE.play();
			isGameOver = true;
		}
		// Keep track of each missile
		alienMissiles.addAll(group.shoot());

		if (!shipMissilesQueue.isEmpty()) {
			shipMissiles.addAll(shipMissilesQueue);
			shipMissilesQueue.clear();
		}
		// loops for each missile
		Iterator<ShipMissile> projectileIterator = shipMissiles.iterator();
		while (projectileIterator.hasNext()) {
			ShipMissile p = projectileIterator.next();
			p.autoMove(delta);
			// loops for each alien
			ArrayList<Alien> aliens = group.getAliveAliens();
			Iterator<Alien> alienIterator = aliens.iterator();
			while (alienIterator.hasNext()) {
				Alien a = alienIterator.next();
				// check if there was a collision between ship missile and aliens
				if (p.collidesWith(a)) 	{
					SoundEffect.ALIEN.play();
					a.setLife(false);
					group.decrementAlienCounter();
					projectileIterator.remove();
					setScore(100);
					if (group.getAlienCounter() % speedDifficulty == 0){
						group.increaseSpeed();
					}
					// no more aliens?
					if (group.getAlienCounter() == 0)
					{
						if (levelIterator.hasNext()) {
							waitingForLevel = true;
						}
						else
						{
							playerWon = true;
							isGameOver = true;
							SoundEffect.BGM.endLoop();
							SoundEffect.WIN.play();
						}
					}
					break;
				}
			}
			if (p.isOutsideBounds()) {
				projectileIterator.remove();
			}
		}
		// loop for the alien missiles
		Iterator<AlienMissile> amIterator = alienMissiles.iterator();
		boolean shipHit = false;
		while (amIterator.hasNext()) {
			AlienMissile a = amIterator.next();
			a.autoMove(delta);
			if (a.collidesWith(ship)) {
				SoundEffect.SHIPDIE.play();
				ship.reduceLife();
				shipHit = true;
				if (ship.getLives() <= -1) {
					isGameOver = true;
					SoundEffect.BGM.endLoop();
					SoundEffect.EXPLODE.play();
				}
				amIterator.remove();
				group.resetShotCounter();
				break;
			}
			else if (a.isOutsideBounds()) {
				amIterator.remove();
				group.decrementShotCounter();
			}
		}
		if (waitingForLevel || isGameOver || shipHit) {
			// clear variables
			shipMissilesQueue.clear();
			shipMissiles.clear();
			shipMissilesQueue.clear();
		}
		setScore(currentLevel.getLevelNumber());

		if ((isGameOver || playerWon)) {
			saveScore();
		}
	}

	/**
	 * This Method draws all gameObjects and displays
	 *  the score while user is playing game
	 * @param levels
	 */
	public synchronized void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(dynamicFont);
		g2.setColor(Color.WHITE);
		bufferImage = createImage(this.getWidth(), this.getHeight());
		bufferGraphics = bufferImage.getGraphics();
		bufferGraphics.drawImage(background, 0, 0, this);
		if (group != null) {
			group.draw(bufferGraphics);
		}
		if (ship != null) {
			ship.draw(bufferGraphics);
			for (int x = 0; x < ship.getLives(); ++x)
				bufferGraphics.drawImage(isaliveImage, (int) (x * (ship.getBoundingBoxWidth() + 10) + 6),
						(int) (getContentPanelHeight() - ship.getBoundingBoxHeight() - 25),
						(int) ship.getBoundingBoxWidth(), (int) ship.getBoundingBoxHeight(), this);
		}
		if (shipMissiles != null) { 
			for (ShipMissile p : shipMissiles) {
				p.draw(bufferGraphics);
			}
		}
		if (alienMissiles != null) {
			for (AlienMissile p : alienMissiles) {
				p.draw(bufferGraphics);
			}
		}
		g.drawImage(bufferImage, 0, 0, this);
		g.drawImage(imageOverlay,1, 1, 550, 700, this);
		g.drawString(Integer.toString(Score), (getContentPanelWidth() / 2) - 15, 
				getContentPanelHeight() - 32);
		if (stringFps != null) {
			g2.drawString(stringFps, 10, 20);
		}
		if (currentLevel != null) {
			g.drawString(Integer.toString(currentLevel.getLevelNumber()), getContentPanelWidth() - 80,
					getContentPanelHeight() - 32);
		}
		// waiting for level or game is over
		if (waitingForLevel) {
			g2.setColor(Color.GREEN);
			g2.drawString("SCORE: " + getScore(),
					(int)(getContentPanelWidth() * 0.35),
					30 + getContentPanelHeight() / 2);
		}
		else if (isGameOver) {
			if (playerWon) {
				// Congratulate the player on winning
				g2.setColor(Color.GREEN);
				g2.drawString("YOU'RE THE BEST TO EVER PLAY! YOU WIN!", (int)(getContentPanelWidth() * 0.25),
						getContentPanelHeight() / 2);
				g2.drawString("SCORE: " + getScore(), (int)(getContentPanelWidth() * 0.35),
						30 + getContentPanelHeight() / 2);
			}
			else {
				g2.setColor(Color.RED);
				g2.drawString("GAME OVER",
						(int)(getContentPanelWidth() * 0.25),
						getContentPanelHeight() / 2);
				g2.drawString("SCORE: " + getScore(),
						(int)(getContentPanelWidth() * 0.35),
						30 + getContentPanelHeight() / 2);
			}
			g2.setColor(Color.GREEN);
			// Thank the player for playing
			g2.drawString("Thank you for Playing!!",
					(int) (getContentPanelWidth() * 0.45), 60 + getContentPanelHeight() / 2);
			
		}
	}

	/*
	 * Load all the levels
	 */
	public void loadAllLevels(LinkedList<Level> levels)
	{
		//Get all the levels and load the first level
		levelIterator = levels.iterator();
		if (levelIterator.hasNext())
		{
			currentLevel = levelIterator.next();
			input = new Input(this, currentLevel.getShip());
			loadLevel(currentLevel);
		}
	}

	/**
	 * this method loads a level
	 * @param level
	 */
	private void loadLevel(Level level)
	{
		this.group = level.getAlienGroup();
		this.background = level.getImage();
		this.ship = level.getShip();
		this.isaliveImage = this.ship.getImage();
		speedDifficulty = (int) (group.getTotalNumAliens() * level.getDifficulty());
		shipMissiles.clear();
		alienMissiles.clear();
		shipMissilesQueue.clear();
		setShotDelay(level.getShotDelay());
		playerWon = false;
		waitingForLevel = false;
	}
	/**
	 * this method retrieves a next level
	 */
	public void nextLevel() {
		if (levelIterator.hasNext()) {
			currentLevel = levelIterator.next();
			loadLevel(currentLevel);
		}
		else{
			SoundEffect.BGM.endLoop();
			playerWon = true;
		}
	}
	
	/**
	 * THis method saves the user's score to a Score.txt file
	 */
	private void saveScore() {
		File file = new File("Score.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(Integer.toString(Score));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sets how fast the gameObjects move
	 * @return
	 */
	public int getSpeedDifficulty(){
		return speedDifficulty;
	}
	
	/**
	 * sets the shot delay for firing
	 * @param delay
	 */
	private void setShotDelay(int delay) {
		input.setShotDelay(delay);
	}

	/**
	 * 
	 * @param p
	 */
	public void addToShipMissiles(ShipMissile p) {
		shipMissilesQueue.add(p);
		SoundEffect.ATTACK.play();

	}
	
	/**
	 * this method sets the high score
	 * @param score
	 */
	public void setScore(int score) {
		Score += score;
	}

	/**
	 * this method gets a score
	 * @return
	 */
	public int getScore() {
		return Score;
	}

}
