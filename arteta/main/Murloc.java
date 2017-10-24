package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Murloc extends BadGuy {
	private int frames;
	private int counter;
	private int cooldown;
	public static final int maxFrames = 18;

	public Murloc(int x, int y) {
		super(x, y);
		frames = 0;
		counter = 0;
		cooldown = 3;

		setAlive(true);
		speed = 2;
		maxHealth = currentHealth = 200;
		setWidth(50);
		setHeight(30);
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
			img = setImage(frames);
			frames++;
			if (frames == maxFrames)
				frames = 0;

		}
	}

	private BufferedImage setImage(int i) {
		BufferedImage img = null;
		try {
			if (direction == "RIGHT")
				img = ImageIO.read(new File("resources/murloc/model/move_right/frame_" + returnId(i)
						+ ".gif"));
			if (direction == "LEFT")
				img = ImageIO.read(new File("resources/murloc/model/move_left/frame_" + returnId(i)
						+ ".gif"));
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
	
	public int getFrames() {
		return this.frames;
	}
}
