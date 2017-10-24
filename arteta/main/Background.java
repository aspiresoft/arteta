package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {

	private BufferedImage img = null;
	private int width;
	private int height;

	public Background(int width, int height) {
		try {
			img = ImageIO.read(new File("resources/background01.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = width;
		this.height = height;
	}

	public void render(Graphics g) {
		g.drawImage(img, 0, 0, width, height, null);
	}
}
