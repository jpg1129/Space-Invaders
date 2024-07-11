import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
/**
 * This class creates the window in which the game is played,
 * sets the title screen, and creates the background
 * @author JamesGomatos
 *
 */
public class Screen extends JComponent
{
	// Define instance variables
	private int screenWidth;
	private int screenHeight;
	private JFrame frame;

	/**
	 * Creates the main window screen for the game
	 * @param screenWidth
	 * @param screenHeight
	 */
	public Screen(int screenWidth, int screenHeight)
	{
		frame = new JFrame();
		frame.setTitle("SPACE INVADERS");
		frame.setBounds(0, 0, screenWidth * 2, screenWidth * 2);
		frame.add(this);
		frame.setVisible(true);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(screenWidth,
				screenWidth));
		panel.setBackground(Color.BLACK);
		frame.add(panel);
		frame.pack();
		// Don't want player to be able to resize
		frame.setResizable(false);	
		// get the cursor image
		BufferedImage cursorImg = new BufferedImage(24, 24,
				BufferedImage.TYPE_INT_ARGB);
		// set it to blank
		Cursor noCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "no cursor");
		frame.getContentPane().setCursor(noCursor);
		this.screenWidth = screenWidth;
		this.screenHeight = screenWidth;
		this.setFocusable(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}

	/**
	 * getter for the height of the content pane
	 * @return
	 */
	public int getContentPanelHeight() {
		return screenHeight;
	}

	/**
	 * getter for the width of the content pane
	 * @return
	 */
	public int getContentPanelWidth() {
		return screenWidth;
	}

	/**
	 * makes the cursor visible
	 */
	public void setCursorVisible() {
		frame.getContentPane().setCursor(Cursor.getDefaultCursor());
	}
	
	/**
	 * Gets rid of frame
	 */
	public void dispose() {
		frame.dispose();
	}
}
