package com.aspire.arteta.main;
import java.awt.Rectangle;

public class Collision {

	private Rectangle[] rectangles;
	public static Rectangle border = new Rectangle(0, 0, 800, 600);
	public static Rectangle leftBlock = new Rectangle(0, 0, 40, 600);
	public static Rectangle rightBlock = new Rectangle(760, 0, 40, 600);
	public static Rectangle pit = new Rectangle(40, 540, 720, 60);
	private Portal portal;

	public Collision() {
		rectangles = new Rectangle[] { new Rectangle(0, 0, 40, 600),
				new Rectangle(760, 0, 40, 600),
				new Rectangle(160, 180, 480, 240),
				new Rectangle(40, 300, 120, 240),
				new Rectangle(640, 300, 120, 240),
				new Rectangle(160, 420, 480, 120),
				new Rectangle(40, 540, 720, 60), };

	}

	public boolean canGoLeft(int x, int y, int width, int height) {
		for (int j = 0; j <= Deneme.sensitivity; j++)
			if (x - j <= leftBlock.x + leftBlock.width)
				return false;
		return true;
	}

	public boolean canGoRight(int x, int y, int width, int height) {
		for (int j = 0; j <= Deneme.sensitivity; j++)
			if (x + width + j >= rightBlock.x)
				return false;
		return true;
	}

	public boolean canGoDown(int x, int y, int width, int height) {
		for (int j = 0; j <= Deneme.sensitivity; j++) {
			for (Rectangle r : rectangles) {
				if (!(y + height > r.y)) {
					if (y + height + j >= r.y && x + width + j > r.x
							&& r.x + r.width + j > x)
						return false;
				}
			}
		}
		return true;
	}

	public boolean outOfBorder(int x, int y, int width, int height) {
		if (x < border.x || x > border.x + border.width || y < border.y
				|| y > border.y + border.height) {
			return true;
		}
		return false;
	}

	public boolean heroCanGoDown(int x, int y, int width, int height) {
		for (int j = 0; j <= Deneme.sensitivity; j++) {
			if (!(y + height > pit.y)) {
				if (y + height + j >= pit.y && x + width + j > pit.x
						&& pit.x + pit.width + j > x)
					return false;
			}

		}
		return true;
	}

	public boolean hasEscaped(int x, int y, int width, int height) {
		if (Missile.inRange(portal.getX() + portal.getWidth() / 2,
				portal.getY() + portal.getHeight() / 2, x + width / 2, y
						+ height / 2, portal.getWidth() / 4) && portal.isActive()) {
			return true;
		}
		return false;
	}

	public void setPortal(Portal portal) {
		this.portal = portal;
	}
}