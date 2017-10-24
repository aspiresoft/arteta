package com.aspire.arteta.main;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class AudioPlayer {

	private String name;
	public boolean isLooped;

	public AudioPlayer(String name, boolean isLooped) {
		this.name = name;
		this.isLooped = isLooped;
		if (!Deneme.isMuted) {
			File soundFile = new File(this.name);
			AudioInputStream sound = null;
			try {
				sound = AudioSystem.getAudioInputStream(soundFile);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// load the sound into memory (a Clip)
			DataLine.Info info = new DataLine.Info(Clip.class,
					sound.getFormat());
			Clip clip = null;
			try {
				clip = (Clip) AudioSystem.getLine(info);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				clip.open(sound);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// due to bug in Java Sound, explicitly exit the VM when
			// the sound has stopped.
			clip.addLineListener(new LineListener() {
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
						event.getLine().close();
					}
				}
			});

			// play the sound clip
			clip.start();
			if (isLooped)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
}
