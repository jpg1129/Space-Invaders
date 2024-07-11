import java.io.*;

import javax.sound.sampled.*;

/**
 * This class is responsible for handling the
 * sound effects of the game 
 * @author JamesGomatos
 *
 */
public enum SoundEffect {
	
	// declare variables
	EXPLODE("res" + File.separator + "music" + File.separator + "huge.wav"),	
	SHIPDIE("res" + File.separator + "music" + File.separator + "hit.wav"),
	BGM("res" + File.separator + "music" + File.separator + "bgm.au"),
	TITLEBGM("res" + File.separator + "music" + File.separator + "ok.wav"),
	WIN("res" + File.separator + "music" + File.separator + "win.wav"),
	ALIEN("res" + File.separator + "music" + File.separator + "alien.wav"),		
	ATTACK("res" + File.separator + "music" + File.separator + "ship.wav");

	/**
	 * Nested class which holds different volumes
	 * @author JamesGomatos
	 *
	 */
	public static enum Volume {
		MUTE, 
		LOW,
		MEDIUM, 
		HIGH
	}
	
	private Clip clipa;	
	public static Volume volume = Volume.HIGH;
		


	/**
	 * creates a element for each sound file the enum
	 * @param soundFileName
	 */
	SoundEffect(String FileName) {
		try {
			File soundFile = new File(FileName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			clipa = AudioSystem.getClip();
			// Load the samples
			clipa.open(audioInputStream);
		} // Handle Exceptions
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Play a sound effect, also includes rewinding
	 */
	public void play() {
		if (volume != Volume.MUTE) {
			if (clipa.isRunning()) {
				clipa.stop();	
			}
			// Rewind, Start Playing
			clipa.setFramePosition(0);	
			clipa.start();			
		}
	}

	public void loop() {
		clipa.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void endLoop() {
		if (clipa.isRunning())
			clipa.stop();
	}

	/**
	 * pre-loads all the sound files
	 */
	public static void init()
	{
		values();	
	}
}
