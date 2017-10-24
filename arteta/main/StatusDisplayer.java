package com.aspire.arteta.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class StatusDisplayer {

	private int enemyKilled;
	private int enemyCount;
	private String str;
	private String waveName;
	private int fontSize = 20;
	private int escapedCount;
	private String speed;
	private int delay;
	private Color color;

	public StatusDisplayer(String waveName, int max, int escapedCount, int delay) {
		this.waveName = waveName;
		this.escapedCount = escapedCount;
		this.delay = delay;
		enemyKilled = 0;
		enemyCount = max;
		speed = setSpeed();
		color = new Color(185, 90, 255);
		setStatus();
	}

	public void addKillCount() {
		enemyKilled++;
		setStatus();
	}

	public void addEscapedCount() {
		escapedCount++;
		setStatus();
	}

	private String setSpeed() {
		if (delay == 60)
			return "VERY SLOW";
		else if (delay == 50)
			return "SLOW";
		else if (delay == 40)
			return "NORMAL";
		else if (delay == 30)
			return "FAST";
		else if (delay == 20)
			return "VERY FAST";
		else
			return "INSANE";
	}

	public void render(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		g.setColor(color);
		g.drawString(str, 30, 30);
	}

	public void setStatus() {
		str = waveName + " : " + enemyKilled + " / " + enemyCount
				+ "    Speed : " + speed + "    Escaped : " + escapedCount;
	}
}
