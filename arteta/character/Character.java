package com.aspire.arteta.character;

import java.awt.Graphics;

import com.aspire.arteta.main.Collision;

public class Character {
	private int x;
	private int y;
	private int width;
	private int height;
	private String direction;
	private String status; // MOVING - ATTACKING - SPELL CASTING - IDLE
	private boolean canAct;
	
	private boolean goingToRight;
	private boolean goingToLeft;
	private boolean isJumping;
	private boolean canJump;
	private int currentHeight;
	
	private Collision c;

	public Character(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		canAct = true;
		direction = "LEFT";
		status = "IDLE";

		c = new Collision();

	}

	public void tick() {
		
	}

	public void render(Graphics g) {

	}

}
