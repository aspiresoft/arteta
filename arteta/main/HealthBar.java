package com.aspire.arteta.main;
import java.awt.Color;
import java.awt.Graphics;

public class HealthBar {
	private int x;
	private int y;
	private int width;
	private int height;
	private int greenWidth;
	private int redWidth;
	private int maxHealth;
	private int currentHealth;
	private float scale;

	public HealthBar(int x, int y, int maxHealth) {
		this.x = x + 5;
		this.y = y - 5;
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
		width = 30;
		height = 6;
		setSizes();

	}

	public void setX(int x) {
		this.x = x + 5;
	}

	public void setY(int y) {
		this.y = y - 5;
	}

	public void render(Graphics g) {
		if (currentHealth > 0) {
			if (scale != 0.0D) {
				g.setColor(Color.GREEN);
				g.fillRect(x, y, greenWidth, height);
			}
			if (scale != 1.0D) {
				g.setColor(Color.RED);
				g.fillRect(x + greenWidth, y, redWidth, height);
			}
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		}
	}

	public void dealDamage(int i) {
		currentHealth -= i;
		if (currentHealth < 0)
			currentHealth = 0;
	}

	public void setSizes() {
		scale = (float) currentHealth / (float) maxHealth;
		greenWidth = (int) (width * scale);
		redWidth = width - greenWidth;
	}

}
