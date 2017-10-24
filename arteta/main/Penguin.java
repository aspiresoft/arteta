package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Penguin extends BadGuy {
	public Penguin(int x, int y) {
		super(x, y);
		setAlive(true);
		speed = 2;
		maxHealth = currentHealth = 100;
		setWidth(40);
		setHeight(40);
		img = setImage();
		setBar(new HealthBar(x, y, maxHealth));
	}

	private BufferedImage setImage() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("resources/bad_guy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void render(Graphics g) {
		g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
		if (getBar() != null)
			getBar().render(g);
	}

}
