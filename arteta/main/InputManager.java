package com.aspire.arteta.main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.aspire.arteta.skill.FireBall;

public class InputManager implements KeyListener {

	private Hero h;
	private Pause p;

	public InputManager(Hero h, Pause p, boolean muted) {
		this.h = h;
		this.p = p;
	}

	public void keyPressed(KeyEvent e) {
		if (p.isRunning()) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				h.goToLeft();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				h.goToRight();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				h.jump();
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (h.isCanAttack())
					h.fire();
				else
					h.setCanFire(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				h.fall();
			}
			if (e.getKeyCode() == KeyEvent.VK_P) {
				if (Deneme.isMuted) {
					Deneme.isMuted = false;
				} else {
					Deneme.isMuted = true;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				h.getFireballs().add(new FireBall(h));
			}
		} else {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				System.exit(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (p.isRunning())
				p.pause();
			else
				p.resume();
		}

	}

	public void keyReleased(KeyEvent e) {
		if (p.isRunning()) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				h.setGoingToLeft(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				h.setGoingToRight(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				h.setCanAttack(true);
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
