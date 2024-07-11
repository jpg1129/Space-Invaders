import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/***
 * 
 * @author JamesGomatos
 * This class is responsible for handling the movement of the ship 
 */
public class Input
{
	private Screen screen;
	private Ship ship;
	private Robot myBot;
	private int shotDelay;

	public Input(final Screen screen)
	{
		class Mousey implements MouseListener
		{
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// start the game when the mouse is pressed
				if (screen instanceof TitleScreen)
					((TitleScreen)screen).setNewGame(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		}
		MouseListener listener = new Mousey();
		screen.addMouseListener(listener);
	}

	/**
	 * Takes input from the game screen and the players mouse
	 * @param screen
	 * @param ship
	 */
	public Input(final Screen screen, final Ship ship) {
		this.ship = ship;
		// default
		shotDelay = 100; 
		try {
			// get native system inputs
			myBot = new Robot();
		} catch (Exception e) {
			System.out.println("ROBOT EXCEPTION");
		}
		// get last shot time
		class MouseList implements MouseListener {
			long lastTime = 0;
			@Override
			public void mouseClicked(MouseEvent e) {
				Game game = (Game) screen;
				if (!game.isGameOver && !game.waitingForLevel) {
					long currentTime = System.currentTimeMillis();
					if (currentTime - lastTime> shotDelay) {
						((Game)screen).addToShipMissiles(ship.shoot());
						lastTime = currentTime;
					}
				}
			}

			/**
			 * shoot if past delay
			 */
			@Override
			public void mousePressed(MouseEvent e)	{
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastTime > shotDelay) {
					Game game = ((Game)screen);
					if (!game.waitingForLevel && !game.isGameOver) {
						((Game)screen).addToShipMissiles(ship.shoot());
						lastTime = currentTime;
					}
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Game g = (Game) screen;
				if (!g.isGameOver && !g.waitingForLevel)
					setPreviousX(e.getX());
			}
			@Override
			public void mouseExited(MouseEvent e){
			}
			@Override
			public void mouseReleased(MouseEvent e){
			}
		}

		class MouseM implements MouseMotionListener{
			@Override
			public void mouseDragged(MouseEvent e) {
				Game game = (Game)screen;
				if (!game.isGameOver && !game.waitingForLevel)
					moveShip(e);
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				Game g = (Game) screen;
				if (!g.isGameOver && !g.waitingForLevel)
					moveShip(e);
			}
		}
		MouseListener mouseListener = new MouseList();
		MouseMotionListener mouseMotion = new MouseM();
		screen.addMouseListener(mouseListener);
		screen.addMouseMotionListener(mouseMotion);
	}

	/**
	 * Set the delay of the ship
	 * @param delay
	 */
	public void setShotDelay(int delay) {
		shotDelay = delay;
	}

	/**
	 * move ship in relation to mouse
	 * @param e
	 */
	private void moveShip(MouseEvent ev) {
		double x = (double) ev.getX();
		double y = (double) ev.getY();
		ship.playerMove(x, y);
	}
	
	/**
	 * set the ships previous position
	 * @param x
	 */
	private void setPreviousX(double x) {
		ship.setPrevMouseX(x);
	}
}
