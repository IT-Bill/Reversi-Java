package controller;

import gameUtil.Path;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundController {
	private Clip clip;
	private boolean bgMusicOn = true;
	private boolean clickMusicOn = true;

	public synchronized void playSound(String path) {
			AudioInputStream ais = null;
			clip = null;
			try {
				File file = new File(path);
				ais = AudioSystem.getAudioInputStream(file);

				DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(ais);

				clip.addLineListener(e -> {
					if (e.getType() == LineEvent.Type.STOP) {
						synchronized (clip) {
							clip.notify();
						}
					}
				});

				clip.start();
				synchronized (clip) {
					clip.wait();
				}
				clip.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (clip != null) {
					clip.close();
				}
				try {
					if (ais != null) {
						ais.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

	public void bgMusic() {
		while (true) {
			playSound(Path.BG_MUSIC);
		}
	}

	public void flipMusic() {
		playSound(Path.CLICK_MUSIC);
	}

	public boolean isBgMusicOn() {
		return bgMusicOn;
	}

	public void setBgMusicOn(boolean bgMusicOn) {
		this.bgMusicOn = bgMusicOn;
	}

	public boolean isClickMusicOn() {
		return clickMusicOn;
	}

	public void setClickMusicOn(boolean clickMusicOn) {
		this.clickMusicOn = clickMusicOn;
	}
}
