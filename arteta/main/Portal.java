package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Portal {
	private int x;
	private int y;
	private int width;
	private int height;
	private int frames;
	private int counter;
	private int cooldown;
	public static final int maxFrames = 8;
	private BufferedImage img = null;
	private boolean active;

	public Portal(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		active = true;
		frames = 0;
		counter = 0;
		cooldown = 2;
	}

	public void render(Graphics g) {
		if (active)
			g.drawImage(img, x, y, width, height, null);
	}

	public void tick() {
		if (active) {
			counter++;
			if (counter >= cooldown) {
				counter = 0;
				if (frames >= maxFrames)
					frames = 1;
				else
					frames++;
				img = setImage(frames);
			}
		}
	}

	private BufferedImage setImage(int i) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources/gate/" + returnId(i)
					+ ".png"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		return img;
	}

	private String returnId(int i) {
		if (i < 10) {
			return "0" + i;
		} else {
			return "" + i;
		}
	}

	public int getFrames() {
		return this.frames;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean b) {
		active = b;
	}
}
