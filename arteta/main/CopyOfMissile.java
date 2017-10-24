package com.aspire.arteta.main;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class CopyOfMissile {
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
	public AudioPlayer player;
	private Collision c;
	private Point hitPoint;
	private boolean hasExploded;
	private int startX;
	private ArrayList<Penguin> penguins;
	private ArrayList<Murloc> murlocs;
	private ArrayList<Axe> axes;
	private ArrayList<Explosion> explosions;
	public static final int sensitivity = 5;
	public static final int radius = 32;

	private static final int radiusClose = 60;
	private static final int radiusFar = 80;
	private static final int radiusFarAway = 100;

	private static final int damageClose = 100;
	private static final int damageFar = 50;
	private static final int damageFarAway = 25;

	public CopyOfMissile(int x, int y, int width, int height, String direction) {
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
		this.speed = 5;
		this.setAlive(true);
		startX = this.x;
		hasExploded = false;
		c = new Collision();
		setImage();
		penguins = null;
		murlocs = null;
		axes = null;
		explosions = null;
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
	public void setExplosion(ArrayList<Explosion> explosions){
		this.explosions = explosions;
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
					|| c.outOfBorder(x, y, width, height)
					|| !c.canGoLeft(x, y, width, height)
					|| !c.canGoRight(x, y, width, height)) {
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
		this.hasExploded = true;
		setHitPoint(new Point(this.x + this.width / 2, this.y + this.height / 2));
	}

	public boolean getExploded() {
		return this.hasExploded;
	}
	public boolean hitSomething(){
		if(penguins != null && murlocs != null && axes != null){
			int missileX = this.x + this.width / 2;
			int missileY = this.y + this.height / 2;
			
			Iterator<Penguin> penguinIterator = penguins.iterator();
			Iterator<Murloc> murlocIterator = murlocs.iterator();
			Iterator<Axe> axeIterator = axes.iterator();
			
			while(penguinIterator.hasNext()){
				Penguin p = penguinIterator.next();
				int enemyX = p.getX() + p.getWidth() / 2;
				int enemyY = p.getY() + p.getHeight() / 2;
				
				if (inRange(missileX, missileY, enemyX, enemyY, radius)) {
					kill();
					p = null;
					return true;
				}
			}
			while(murlocIterator.hasNext()){
				Murloc m = murlocIterator.next();
				int enemyX = m.getX() + m.getWidth() / 2;
				int enemyY = m.getY() + m.getHeight() / 2;
				
				if (inRange(missileX, missileY, enemyX, enemyY, radius)) {
					kill();
					m = null;
					return true;
				}
			}
			while(axeIterator.hasNext()){
				Axe a = axeIterator.next();
				int enemyX = a.getX() + a.getWidth() / 2;
				int enemyY = a.getY() + a.getHeight() / 2;
				
				if (inRange(missileX, missileY, enemyX, enemyY, radius)) {
					kill();
					a = null;
					return true;
				}
			}
		}
		
		
		return false;
	}

	public boolean hitAPenguin() {
		if (penguins != null) {
			int missileX = this.x + this.width / 2;
			int missileY = this.y + this.height / 2;

			Iterator<Penguin> penguinIterator = penguins.iterator();
			while (penguinIterator.hasNext()) {
				Penguin p = penguinIterator.next();
				int enemyX = p.getX() + p.getWidth() / 2;
				int enemyY = p.getY() + p.getHeight() / 2;

				if (inRange(missileX, missileY, enemyX, enemyY, radius)) {
					setHitPoint(new Point(missileX, missileY));
					hasExploded = true;
					this.alive = false;
					p = null;
					return true;
				}
			}
		}
		return false;
	}

	public boolean hitAMurloc() {
		if (murlocs != null) {
			int missileX = this.x + this.width / 2;
			int missileY = this.y + this.height / 2;

			Iterator<Murloc> murlocIterator = murlocs.iterator();
			while (murlocIterator.hasNext()) {
				Murloc p = murlocIterator.next();
				int enemyX = p.getX() + p.getWidth() / 2;
				int enemyY = p.getY() + p.getHeight() / 2;

				if (inRange(missileX, missileY, enemyX, enemyY, radius)) {
					setHitPoint(new Point(missileX, missileY));
					hasExploded = true;
					this.alive = false;
					return true;
				}
			}
		}
		return false;
	}

	public boolean hitAnAxe() {
		if (axes != null) {
			int missileX = this.x + this.width / 2;
			int missileY = this.y + this.height / 2;

			Iterator<Axe> axeIterator = axes.iterator();
			while (axeIterator.hasNext()) {
				Axe p = axeIterator.next();
				int enemyX = p.getX() + p.getWidth() / 2;
				int enemyY = p.getY() + p.getHeight() / 2;

				if (inRange(missileX, missileY, enemyX, enemyY, radius)) {
					setHitPoint(new Point(missileX, missileY));
					hasExploded = true;
					this.alive = false;
					return true;
				}
			}
		}
		return false;
	}

	public void setHitPoint(Point point) {
		this.hitPoint = point;

	}

	private boolean inRange(int mx, int my, int px, int py, int radius) {
		if (Math.pow(px - mx, 2) + Math.pow(py - my, 2) <= Math.pow(radius, 2)) {
			return true;
		}
		return false;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Point getHitPoint() {
		return hitPoint;
	}
	public void explode(){
		if(penguins != null && murlocs != null && axes != null){
			player = new AudioPlayer("resources/sounds/boom1.wav", false);
			
			int missileX = this.x + this.width / 2;
			int missileY = this.y + this.height / 2;
			
			explosions.add(new Explosion(missileX,missileY));
			
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
	}

	public void explodePenguins() {
		if (penguins != null) {
			player = new AudioPlayer("resources/sounds/boom1.wav", false);
			this.setAlive(false);
			Iterator<Penguin> penguinIterator = penguins.iterator();

			int missileX = this.x + this.width / 2;
			int missileY = this.y + this.height / 2;
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
		}
	}

	public void explodeMurlocs(ArrayList<Murloc> murlocs) {
		player = new AudioPlayer("resources/sounds/boom1.wav", false);
		this.setAlive(false);
		Iterator<Murloc> murlocIterator = murlocs.iterator();

		int missileX = this.x + this.width / 2;
		int missileY = this.y + this.height / 2;
		while (murlocIterator.hasNext()) {
			Murloc p = murlocIterator.next();
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
	}

	public void explodeAxes(ArrayList<Axe> axes) {
		player = new AudioPlayer("resources/sounds/boom1.wav", false);
		this.setAlive(false);
		Iterator<Axe> axeIterator = axes.iterator();

		int missileX = this.x + this.width / 2;
		int missileY = this.y + this.height / 2;
		while (axeIterator.hasNext()) {
			Axe p = axeIterator.next();
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
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
