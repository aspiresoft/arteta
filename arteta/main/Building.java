package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Building {
	private int width;
	private int height;
	private int x;
	private int y;
	private BufferedImage img;
	private int type;

	public Building(int x, int y, int width, int height, int type) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.type = type;
		this.img = setImage();
	}

	private BufferedImage setImage() {
		BufferedImage img = null;

		try {
			switch (type) {
			case 1:
				img = ImageIO.read(new File("resources/building02.png"));
				break;
			case 2:
				img = ImageIO.read(new File("resources/building01.png"));
				break;
			case 3:
				img = ImageIO.read(new File("resources/window3.png"));
				break;
			case 4:
				img = ImageIO.read(new File("resources/window6.png"));
				break;
			case 5:
				img = ImageIO.read(new File("resources/window1.png"));
				break;
			case 6:
				img = ImageIO.read(new File("resources/window1.png"));
				break;
			case 7:
				img = ImageIO.read(new File("resources/window4.png"));
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	public void render(Graphics g) {
		if (type == 1 || type == 2) {
			g.drawImage(img, x, y, width, height, null);
		} else {
			for (int i = 0; i < width / 60; i++) {
				for (int j = 0; j < height / 60; j++) {
					g.drawImage(img, x + i * 60, y + j * 60, 60, 60, null);
				}
			}
		}
	}
}
