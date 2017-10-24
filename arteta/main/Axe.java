package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Axe extends BadGuy {

	public static final int maxDeathFrames = 30;
	public static final int maxMoveFrames = 26;
	public static final int maxStandFrames = 29;

	public Axe(int x, int y) {
		super(x, y);
		frames_death = 0;
		frames_move = 0;
		frames_stand = 0;
		counter = 0;
		cooldown = 3;

		setAlive(true);
		speed = 2;
		maxHealth = currentHealth = 300;
		setWidth(40);
		setHeight(40);
		setBar(new HealthBar(x, y, maxHealth));
	}

	public void render(Graphics g) {
		g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
		if (getBar() != null)
			getBar().render(g);
	}

	public void frameImage() {
		counter++;
		if (counter >= cooldown) {
			counter = 0;

			if (isDying()) {
				frames_death++;
				if (frames_death >= maxDeathFrames) {
					frames_death = 0;
				}
				setImage(frames_death);
			} else {
				if (isFalling()) {
					frames_stand++;
					if (frames_stand >= maxStandFrames) {
						frames_stand = 0;
					}
					setImage(frames_stand);
				} else {
					frames_move++;
					if (frames_move >= maxMoveFrames) {
						frames_move = 0;
					}
					setImage(frames_move);
				}
			}

		}
	}

	private BufferedImage setImage(int i) {
		BufferedImage img = null;
		try {
			if (isDying()) {
				img = ImageIO.read(new File(
						"resources/hell/grunt/model/death/frame_" + returnId(i)
								+ ".gif"));
			} else {
				if (direction == "RIGHT") {
					if (isFalling()) {
						img = ImageIO.read(new File(
								"resources/hell/grunt/model/standright/frame_"
										+ returnId(i) + ".gif"));
					} else {
						img = ImageIO.read(new File(
								"resources/hell/grunt/model/moveright/frame_"
										+ returnId(i) + ".gif"));
					}
				}
				if (direction == "LEFT") {
					if (isFalling()) {
						img = ImageIO.read(new File(
								"resources/hell/grunt/model/standleft/frame_"
										+ returnId(i) + ".gif"));
					} else {
						img = ImageIO.read(new File(
								"resources/hell/grunt/model/moveleft/frame_"
										+ returnId(i) + ".gif"));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	private String returnId(int i) {
		if (i < 10) {
			return "00" + i;
		} else {
			return "0" + i;
		}
	}

}
