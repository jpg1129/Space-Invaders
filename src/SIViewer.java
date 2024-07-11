/**
 * View Component which will have a static method that 
 * creates the JFrame and the objects the game needs
 * to run
 * @author JamesGomatos
 *
 */
public class SIViewer {
	// instance variables
	public static final int SCREENWIDTH = 541;
	public static final int SCREENHEIGHT = 691;

	public static void main(String[] args) {
		TitleScreen screen = new TitleScreen(SCREENWIDTH, SCREENHEIGHT);

		while (!screen.getNewGame()) {
			try {
				Thread.sleep(100);
				}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}

		screen.stopMusic();
		screen.dispose();
		// Create a new game
		Game g = new Game(SCREENWIDTH, SCREENHEIGHT);
		g.gameLoop();
	}
}
