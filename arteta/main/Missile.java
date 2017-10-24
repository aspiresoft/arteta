package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class Missile {
	private int x;
	private int y;
	private int width;
	private int height;
	private int speed;
	private boolean alive;
	private static int distance = 800;
	private String direction;
	private BufferedImage imgLeft;
	private BufferedImage imgRight;
	private boolean exploded;
	private int startX;
	
	private ArrayList<Penguin> penguins;
	private ArrayList<Murloc> murlocs;
	private ArrayList<Axe> axes;
	private ArrayList<Explosion> explosions;

	private static final int radiusClose = 60;
	private static final int radiusFar = 80;
	private static final int radiusFarAway = 100;

	private static final int damageClose = 150;
	private static final int damageFar = 75;
	private static final int damageFarAway = 50;

	public Missile(int x, int y, int width, int height, String direction) {
		this.width = 24;
		this.height = 16;
		this.direction = direction;
		if (direction == "LEFT") {
			this.x = x - this.width;
			this.y = y + 15;
		} else if (direction == "RIGHT") {
			this.x = x + width;
			this.y = y + 15;
		}
		speed = 5;
		alive = true;
		startX = this.x;
		exploded = false;
		setImage();
		penguins = null;
		murlocs = null;
		axes = null;
		explosions = null;
	}

	public void render(Graphics g) {
		if (direction == "LEFT") {
			g.drawImage(imgLeft, x, y, width, height, null);
		} else if (direction == "RIGHT") {
			g.drawImage(imgRight, x, y, width, height, null);
		}
	}

	private void setImage() {
		imgLeft = null;
		imgRight = null;
		try {
			imgLeft = ImageIO.read(new File("resources/rocket_left.png"));
			imgRight = ImageIO.read(new File("resources/rocket_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if (isAlive()) {
			if (Math.abs(startX - x) >= distance
					|| Deneme.collision.outOfBorder(x, y, width, height)) {
				kill();
			} else {
				if (direction == "LEFT") {
					for (int i = 1; i <= speed; i++) {
						x--;
					}
				} else if (direction == "RIGHT") {
					for (int i = 1; i <= speed; i++) {
						x++;
					}
				}
			}
		}
	}

	private void kill() {
		this.alive = false;
	}

	private void createExplosionEffect() {
		explosions.add(new Explosion(this.x + this.width / 2, this.y
				+ this.height / 2));
		AudioPlayer player = new AudioPlayer("resources/sounds/boom1.wav", false);
		player = null;
	}

	public boolean hitSomething() {
		int missileX = this.x + this.width / 2;
		int missileY = this.y + this.height / 2;

		Iterator<Penguin> penguinIterator = penguins.iterator();
		Iterator<Murloc> murlocIterator = murlocs.iterator();
		Iterator<Axe> axeIterator = axes.iterator();

		while (penguinIterator.hasNext()) {
			Penguin p = penguinIterator.next();
			int enemyX = p.getX() + p.getWidth() / 2;
			int enemyY = p.getY() + p.getHeight() / 2;

			if (inRange(missileX, missileY, enemyX, enemyY, p.getWidth() / 2
					+ this.width / 2)) {
				kill();
				p = null;
				return true;
			}
		}
		while (murlocIterator.hasNext()) {
			Murloc m = murlocIterator.next();
			int enemyX = m.getX() + m.getWidth() / 2;
			int enemyY = m.getY() + m.getHeight() / 2;

			if (inRange(missileX, missileY, enemyX, enemyY, m.getWidth() / 2
					+ this.width / 2)) {
				kill();
				m = null;
				return true;
			}
		}
		while (axeIterator.hasNext()) {
			Axe a = axeIterator.next();
			int enemyX = a.getX() + a.getWidth() / 2;
			int enemyY = a.getY() + a.getHeight() / 2;

			if (inRange(missileX, missileY, enemyX, enemyY, a.getWidth() / 2
					+ this.width / 2)) {
				kill();
				a = null;
				return true;
			}
		}

		return false;
	}

	public static boolean inRange(int mx, int my, int px, int py, int radius) {
		if (Math.pow(px - mx, 2) + Math.pow(py - my, 2) <= Math.pow(radius, 2)) {
			return true;
		}
		return false;
	}

	public void explode() {

		this.exploded = true;

		int missileX = this.x + this.width / 2;
		int missileY = this.y + this.height / 2;

		createExplosionEffect();

		Iterator<Penguin> penguinIterator = penguins.iterator();
		Iterator<Murloc> murlocIterator = murlocs.iterator();
		Iterator<Axe> axeIterator = axes.iterator();

		while (penguinIterator.hasNext()) {
			Penguin p = penguinIterator.next();
			if (p.isAlive()) {
				int enemyX = p.getX() + p.getWidth() / 2;
				int enemyY = p.getY() + p.getHeight() / 2;
				if (inRange(missileX, missileY, enemyX, enemyY, radiusClose)) {
					p.dealDamage(damageClose);
					p.getBar().dealDamage(damageClose);
				} else if (inRange(missileX, missileY, enemyX, enemyY,
						radiusFar)) {
					p.dealDamage(damageFar);
					p.getBar().dealDamage(damageFar);
				} else if (inRange(missileX, missileY, enemyX, enemyY,
						radiusFarAway)) {
					p.dealDamage(damageFarAway);
					p.getBar().dealDamage(damageFarAway);
				}
			}
		}
		while (murlocIterator.hasNext()) {
			Murloc m = murlocIterator.next();
			if (m.isAlive()) {
				int enemyX = m.getX() + m.getWidth() / 2;
				int enemyY = m.getY() + m.getHeight() / 2;
				if (inRange(missileX, missileY, enemyX, enemyY, radiusClose)) {
					m.dealDamage(damageClose);
					m.getBar().dealDamage(damageClose);
				} else if (inRange(missileX, missileY, enemyX, enemyY,
						radiusFar)) {
					m.dealDamage(damageFar);
					m.getBar().dealDamage(damageFar);
				} else if (inRange(missileX, missileY, enemyX, enemyY,
						radiusFarAway)) {
					m.dealDamage(damageFarAway);
					m.getBar().dealDamage(damageFarAway);
				}
			}
		}
		while (axeIterator.hasNext()) {
			Axe a = axeIterator.next();
			if (a.isAlive()) {
				int enemyX = a.getX() + a.getWidth() / 2;
				int enemyY = a.getY() + a.getHeight() / 2;
				if (inRange(missileX, missileY, enemyX, enemyY, radiusClose)) {
					a.dealDamage(damageClose);
					a.getBar().dealDamage(damageClose);
				} else if (inRange(missileX, missileY, enemyX, enemyY,
						radiusFar)) {
					a.dealDamage(damageFar);
					a.getBar().dealDamage(damageFar);
				} else if (inRange(missileX, missileY, enemyX, enemyY,
						radiusFarAway)) {
					a.dealDamage(damageFarAway);
					a.getBar().dealDamage(damageFarAway);
				}
			}
		}

	}

	public void setPenguins(ArrayList<Penguin> penguins) {
		this.penguins = penguins;
	}

	public void setMurlocs(ArrayList<Murloc> murlocs) {
		this.murlocs = murlocs;
	}

	public void setAxes(ArrayList<Axe> axes) {
		this.axes = axes;
	}

	public void setExplosion(ArrayList<Explosion> explosions) {
		this.explosions = explosions;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isExploded() {
		return exploded;
	}

}
