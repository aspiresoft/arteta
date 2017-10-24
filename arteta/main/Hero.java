package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.aspire.arteta.skill.FireBall;

public class Hero {
	private static final int speed = 3;
	private static final int jumpSpeed = 4;
	private static final int maxHeight = 120;
	private static final int cooldown = 20;

	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage imgLeft;
	private BufferedImage imgRight;
	private String direction;
	private Collision c;

	private ArrayList<Missile> missiles;
	private boolean goingToRight;
	private boolean goingToLeft;
	private boolean isJumping;
	private boolean canJump;
	private int currentHeight;

	private boolean canAttack;
	private boolean canFire;
	private int counter = 0;
	private ArrayList<FireBall> fireballs;

	public Hero(int x, int y, String direction) {
		this.setX(x);
		this.setY(y);
		setWidth(40);
		height = 60;
		counter = 0;
		this.setDirection(direction);
		setImages();
		c = new Collision();
		setGoingToLeft(false);
		setGoingToRight(false);
		isJumping = false;
		canJump = false;
		setCanFire(true);
		setCanAttack(true);
	}

	public void render(Graphics g) {
		if (getDirection() == "LEFT") {
			g.drawImage(imgLeft, x, y, width, height, null);
		} else if (getDirection() == "RIGHT") {
			g.drawImage(imgRight, x, y, width, height, null);
		}
	}

	private void setImages() {
		imgLeft = null;
		imgRight = null;
		try {
			imgLeft = ImageIO.read(new File("resources/hero_left.png"));
			imgRight = ImageIO.read(new File("resources/hero_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fire() {
		if (isCanFire()) {
			if (canAttack) {
				setCanFire(false);
				canAttack = false;
				getMissiles().add(new Missile(x, y, width, height, direction));
			}
		} else {
			canAttack = false;
		}
	}

	public void tick() {

		if (canAttack) {
			counter++;
			if (counter >= cooldown) {
				counter = 0;
				setCanFire(true);
			}
		}
		for (int i = 1; i <= speed; i++) {
			if (isGoingToLeft()) {

				if (c.canGoLeft(x, y, width, height)) {
					x--;
				}
			}
		}
		for (int i = 1; i <= speed; i++) {
			if (isGoingToRight()) {

				if (c.canGoRight(x, y, width, height)) {
					x++;
				}
			}
		}
		for (int i = 1; i <= jumpSpeed; i++) {
			if (isJumping) {
				if (currentHeight <= maxHeight) {
					y--;
					currentHeight++;
				} else {
					isJumping = false;
				}
			} else {
				if (c.canGoDown(x, y, width, height)) {
					if (c.heroCanGoDown(x, y, width, height)) {
						y++;
					} else {
						canJump = true;
					}
				} else {
					canJump = true;
				}

			}
		}
	}

	public void jump() {
		if (canJump
				&& !isJumping
				&& (!c.canGoDown(x, y, width, height) || !c.heroCanGoDown(x, y,
						width, height))) {
			isJumping = true;
			canJump = false;
			currentHeight = 0;
		}
	}

	public void fall() {
		if (canJump
				&& !isJumping
				&& (!c.canGoDown(x, y, width, height) && c.heroCanGoDown(x, y,
						width, height))) {
			y += Deneme.sensitivity + 1;
		}
	}

	public void goToLeft() {
		setDirection("LEFT");
		setGoingToLeft(true);
		setGoingToRight(false);
	}

	public void goToRight() {
		setDirection("RIGHT");
		setGoingToLeft(false);
		setGoingToRight(true);
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public boolean isGoingToLeft() {
		return goingToLeft;
	}

	public void setGoingToLeft(boolean goingToLeft) {
		this.goingToLeft = goingToLeft;
	}

	public boolean isGoingToRight() {
		return goingToRight;
	}

	public void setGoingToRight(boolean goingToRight) {
		this.goingToRight = goingToRight;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isCanAttack() {
		return canAttack;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

	public boolean isCanFire() {
		return canFire;
	}

	public void setCanFire(boolean canFire) {
		this.canFire = canFire;
	}

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	public void setMissiles(ArrayList<Missile> missiles) {
		this.missiles = missiles;
	}

	public int getHeight() {
		return height;
	}
	public void setFireballs(ArrayList<FireBall> fireballs){
		this.fireballs = fireballs;
	}
	public ArrayList<FireBall> getFireballs(){
		return this.fireballs;
	}
}
