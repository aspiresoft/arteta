package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Explosion {
	private int x;
	private int y;
	private int width;
	private int height;
	private int frames;
	private int counter;
	private int cooldown;
	public static final int maxFrames = 90;
	private BufferedImage img = null;

	public Explosion(int x, int y) {
		width = 160;
		height = 120;
		this.x = x - width / 2;
		this.y = y - height / 2;
		frames = 0;
		counter = 0;
		cooldown = 1;
	}

	public void render(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
	}

	public void tick() {
		counter++;
		if (counter >= cooldown) {
			counter = 0;
			frames++;
			img = setImage(frames);
		}
	}

	private BufferedImage setImage(int i) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources/explosion/explosion1_00"
					+ returnId(i) + ".png"));
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
}
