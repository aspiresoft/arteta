package com.aspire.arteta.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Pause {
	private boolean running;
	private Dimension size;
	private int R;
	private int G;
	private int B;
	private int tickCount;
	private static final int cooldown = 60;
	private static final int fontSize = 30;

	public Pause(boolean running, Dimension size) {
		this.running = running;
		this.size = size;
		tickCount = 0;
		randomizeColors();
	}

	private void randomizeColors() {
		Random r = new Random();
		R = r.nextInt(255);
		G = r.nextInt(255);
		B = r.nextInt(255);
		r = null;
	}

	public void pause() {
		if (isRunning()) {
			setRunning(false);
		}
	}

	public void resume() {
		if (!isRunning()) {
			setRunning(true);
		}
	}

	public void tick() {
		tickCount++;
		if (tickCount >= cooldown) {
			tickCount = 0;
			randomizeColors();
		}

	}

	public void render(Graphics g) {
		if (!isRunning()) {
			g.setColor(new Color(R, G, B));
			g.fillRect(size.width / 2 - 200, size.height / 2 - 100, 400, 200);

			g.setColor(Color.BLACK);
			g.drawRect(size.width / 2 - 200, size.height / 2 - 100, 400, 200);

			g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize * 2 + 10));
			g.setColor(new Color((R + 127) % 255, (G + 127) % 255,
					(B + 127) % 255));
			g.drawString("PAUSED", size.width / 2 - 145, size.height / 2 - 25);

			g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			g.drawString("Enter to Exit \n Esc to Resume",
					size.width / 2 - 188, size.height / 2 + 50);
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
