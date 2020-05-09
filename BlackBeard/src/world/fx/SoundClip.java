package world.fx;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundClip {

	private SourceDataLine line = null;
	private byte[] audioBytes;
	private int numBytes;
	private boolean isOn = false;
	
	
	
	public SoundClip(String fileName) // Constructor
	{
		
		
		File soundFile = new File(fileName);
		AudioInputStream audioInputStream = null;
		// open file
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception ex) {
			System.out.println("Error 404: Unable to locate " + fileName);
			// System.exit(1);//Quit program as error.
		}

		AudioFormat audioFormat = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
		} catch (LineUnavailableException ex) {
			System.out.println(" Audio line unavailable ");
			//System.exit(1);
		}

		line.start();

		audioBytes = new byte[(int) soundFile.length()];

		try {
			numBytes = audioInputStream.read(audioBytes, 0, audioBytes.length);
		} catch (IOException ex) {
			System.out.println(" The following file may be corrupted, or in a file format \n not recognized by SoundClip: " + fileName + " ***");
			System.exit(1);
		}
	}

	public void play() {
		this.isOn = true;
		line.write(audioBytes, 0, numBytes);
		this.isOn = false;
	}
	
	

	

	public boolean isOn() {
		return isOn;
	}

	

}
