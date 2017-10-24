package com.aspire.arteta.main;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BadGuy {
	private int width;
	private int height;
	protected BufferedImage img;
	protected int maxHealth;
	private int x;
	private int y;
	protected String direction;
	private boolean alive;
	protected int currentHealth;
	protected int speed;
	private boolean canMove;
	private HealthBar bar;
	private boolean escaped;
	private boolean falling;
	private boolean dying;
	protected int frames_death;
	protected int frames_move;
	protected int frames_stand;
	protected int counter;
	protected int cooldown;

	public BadGuy(int x, int y) {
		this.setX(x);
		this.setY(y);
		direction = directionRandomizer();
		canMove = true;
		setFalling(false);
		setEscaped(false);
		dying = false;
	}

	private String directionRandomizer() {
		String str = "LEFT";
		Random r = new Random();
		if (r.nextInt(2) == 1) {
			str = "LEFT";
		} else {
			str = "RIGHT";
		}
		return str;
	}

	public void dealDamage(int i) {
		currentHealth -= i;
	}

	public void tick() {
		if (getBar() != null) {
			getBar().setX(this.getX());
			getBar().setY(this.getY());
			getBar().setSizes();
		}
		if (isAlive() && canMove) {
			if (direction == "LEFT") {
				if (Deneme.collision.canGoLeft(getX(), getY(), getWidth(), getHeight())) {
					setX(getX() - speed);
				} else {
					direction = "RIGHT";
					frames_move = 0;

				}
			} else if (direction == "RIGHT") {
				if (Deneme.collision.canGoRight(getX(), getY(), getWidth(), getHeight())) {
					setX(getX() + speed);
				} else {
					direction = "LEFT";
					frames_move = 0;
				}

			}
			if (Deneme.collision.canGoDown(getX(), getY(), getWidth(), getHeight())) {
				setY(getY() + speed);
				setFalling(true);
			} else {
				setFalling(false);
				frames_stand = 0;
			}
		}
		if (Deneme.collision.hasEscaped(x, y, width, height)) {
			setEscaped(true);
			kill();
		}
		if (currentHealth <= 0) {
			kill();
		}
	}

	public void kill() {
		setBar(null);
		setAlive(false);
	}

	public void setMove(boolean b) {
		canMove = b;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isEscaped() {
		return escaped;
	}

	public void setEscaped(boolean escaped) {
		this.escaped = escaped;
	}

	public HealthBar getBar() {
		return bar;
	}

	public void setBar(HealthBar bar) {
		this.bar = bar;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isDying() {
		return dying;
	}

	public void setDying(boolean dying) {
		this.dying = dying;
	}
}
