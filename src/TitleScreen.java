import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

/**
 * This class defines the title screen
 * @author JamesGomatos
 *
 */
public class TitleScreen extends Screen
{	
	// instance variables
	private volatile boolean newGame;

	/**
	 * Constructor for the title screen
	 * @param screenWidth
	 * @param screenHeight
	 */
	public TitleScreen(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);

		Input input = new Input(this);
		this.newGame = false;
		// initialize all our sound effects
		SoundEffect.init();	
		SoundEffect.TITLEBGM.loop();
	}
	
	/**
	 * Getter for newGame
	 * @return
	 */
	public boolean getNewGame() {
		return newGame;
	}

	/**
	 * setter boolean flag
	 * @param madeGame
	 */
	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

	/**
	 * stop the music
	 */
	public void stopMusic()
	{
		SoundEffect.TITLEBGM.endLoop();
	}

	
	public void paintComponent(Graphics g)
	{
		Image imageOverlay = Toolkit.getDefaultToolkit().getImage("res" + File.separator + "objects" + File.separator + "title.jpg");
		g.drawImage(imageOverlay, 0, 0, SIViewer.SCREENWIDTH, SIViewer.SCREENHEIGHT, this);
	}
}
