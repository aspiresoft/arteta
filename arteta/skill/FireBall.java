package com.aspire.arteta.skill;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aspire.arteta.main.AudioPlayer;
import com.aspire.arteta.main.Collision;
import com.aspire.arteta.main.Hero;

public class FireBall{
	private static final int maxFrames = 8;
	private static final int distance = 600;
	private static final int rate = 3;
	private static final int speed = 8;

	private int x;
	private int y;
	private int width;
	private int height;
	private int counter;
	private int frames;

	private String direction;
	private BufferedImage img;
	private boolean active;
	private int startX;

	public FireBall(Hero caster) {
		direction = caster.getDirection();
		width = 40;
		height = 40;
		x = caster.getX() + caster.getWidth() / 2;
		y = caster.getY();
		img = null;
		active = true;
		startX = x;
		
		AudioPlayer player = new AudioPlayer("resources/skills/fireball/sound/fireball01.wav", false);
		player = null;

	}

	public void tick() {
		if (active) {
			frameImage();
			if (Math.abs(startX - x) >= distance || x < Collision.leftBlock.x + Collision.leftBlock.width || x > Collision.rightBlock.x) {
				active = false;
			} else {
				moveBall();
				// DEAL DAMAGE
			}
		}
	}

	public void render(Graphics g) {
		if (active) {
			g.drawImage(img, x, y, width, height, null);
		}
	}

	public void frameImage() {
		counter++;
		if (counter >= rate) {
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
			img = ImageIO.read(new File(
					"resources/skills/fireball/model/normal/frame_"
							+ returnId(i) + ".png"));
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

	private void moveBall() {
		if (direction == "LEFT") {
			x -= speed;
		} else if (direction == "RIGHT") {
			x += speed;
		}
	}

	public boolean isActive() {
		return active;
	}
}
