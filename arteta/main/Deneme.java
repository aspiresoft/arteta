package com.aspire.arteta.main;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;

import com.aspire.arteta.skill.FireBall;

public class Deneme extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	public static final int sensitivity = 5;

	public boolean running = true;
	private int tickCount = 0;
	private int escapedCount;
	public static boolean isMuted;
	private boolean ticker;

	public static Collision collision;
	private static JFrame frame;
	private Background bg;
	private BadGuyCreator[] badGuyCreators;
	private Building[] roofs;
	private ArrayList<Penguin> penguins;
	private Iterator<Penguin> penguinIterator;
	private Hero hero;
	private ArrayList<Missile> missiles;
	private Iterator<Missile> missileIterator;
	private ArrayList<Explosion> explosions;
	private Iterator<Explosion> explosionIterator;
	private Wave[] waves;
	private StatusDisplayer displayer;
	public static AudioPlayer musicPlayer;
	private Pause pauser;
	public static Portal gate;
	private ArrayList<Murloc> murlocs;
	private Iterator<Murloc> murlocIterator;
	private ArrayList<Axe> axes;
	private Iterator<Axe> axeIterator;
	private ArrayList<FireBall> fireballs;
	private Iterator<FireBall> fireballIterator;

	private int currentWave;
	private int enemyCount;
	private int cooldown;
	private int waveWait = 300;

	private Deneme() {
		bg = new Background(WIDTH, HEIGHT);

		roofs = new Building[] { new Building(-60, 0, 100, 600, 1),
				new Building(760, 0, 100, 600, 2),
				new Building(160, 180, 480, 240, 3),
				new Building(40, 300, 120, 240, 4),
				new Building(640, 300, 120, 240, 4),
				new Building(160, 420, 480, 180, 5),
				new Building(40, 540, 720, 60, 7) };

		collision = new Collision();
		gate = new Portal(340,460,120,100);
		collision.setPortal(gate);

		penguins = new ArrayList<>();
		fireballs = new ArrayList<>();
		
		badGuyCreators = new BadGuyCreator[] { new BadGuyCreator(200, 0),
				new BadGuyCreator(300, 0), new BadGuyCreator(400, 0),
				new BadGuyCreator(500, 0), new BadGuyCreator(600, 0) };
		for (BadGuyCreator bgc : badGuyCreators) {
			bgc.setPenguins(penguins);
			bgc.setMurlocs(murlocs);
			bgc.setAxes(axes);
		}

		missiles = new ArrayList<>();
		explosions = new ArrayList<>();
		murlocs = new ArrayList<>();
		axes = new ArrayList<>();

		hero = new Hero(300, 300, "RIGHT");
		hero.setMissiles(missiles);
		hero.setFireballs(fireballs);

		waves = new Wave[] { new Wave("Wave 1", 60, 10),
				new Wave("Wave 2", 60, 20), new Wave("Wave 3", 60, 30),
				new Wave("Wave 4", 50, 15), new Wave("Wave 5", 50, 30),
				new Wave("Wave 6", 40, 10), new Wave("Wave 7", 40, 25),
				new Wave("Wave 8", 40, 40), new Wave("Wave 9", 30, 15),
				new Wave("Wave 10", 30, 25), new Wave("Wave 11", 30, 40),
				new Wave("Wave 12", 20, 10), new Wave("Wave 13", 20, 20),
				new Wave("Wave 14", 20, 30), new Wave("Wave 15", 10, 20) };
		currentWave = 0;
		cooldown = waveWait;
		enemyCount = 0;
		waves[currentWave].start();
		escapedCount = 0;
		displayer = new StatusDisplayer(waves[currentWave].getName(),
				waves[currentWave].getMaxEnemyCount(), escapedCount,
				waves[currentWave].getDelay());

		ticker = true;
		pauser = new Pause(ticker, SIZE);
		isMuted = false;

		KeyListener listener = new InputManager(hero, pauser, isMuted);
		addKeyListener(listener);
		setFocusable(true);

		createBackgroundMusic();

	}

	public static void createBackgroundMusic() {
		musicPlayer = new AudioPlayer("resources/sounds/senya.wav", true);
	}

	public static void main(String[] args) {
		Deneme d = new Deneme();
		frame = new JFrame();
		frame.setPreferredSize(SIZE);
		frame.setLocation(50, 50);
		frame.add(d);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		d.start();
	}

	private void start() {
		new Thread(this).start();
		running = true;
	}

	public void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (delta >= 1) {
				ticks++;
				tickCount++;
				tick();
				delta--;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				frame.setTitle("Arteta | " + ticks + " ticks & " + frames
						+ " fps");
				frames = 0;
				ticks = 0;
			}
		}

	}

	private void tick() {
		ticker = pauser.isRunning();
		if (ticker) {
			gate.tick();
			if (waves[currentWave].isStarted()) {
				if (tickCount >= cooldown) {
					if (tickCount % waves[currentWave].getDelay() == 0
							&& enemyCount < waves[currentWave]
									.getMaxEnemyCount()) {
						Random r = new Random();
						badGuyCreators[r.nextInt(5)].createBadPenguin();
						enemyCount++;
					}
				}
			}

			hero.tick();
			
			missileIterator = missiles.iterator();
			while (missileIterator.hasNext()) {
				Missile m = missileIterator.next();
				m.setPenguins(penguins);
				m.setMurlocs(murlocs);
				m.setAxes(axes);
				m.setExplosion(explosions);
				m.tick();
				if(m.hitSomething()){
					m.explode();
				}
				if (!m.isAlive()) {
					if (!m.isExploded()) {
						m.explode();
					}
					missileIterator.remove();
				}
			}

			penguinIterator = penguins.iterator();
			while (penguinIterator.hasNext()) {
				Penguin p = penguinIterator.next();
				p.tick();

				if (p.isEscaped()) {
					escapedCount++;
					displayer.addEscapedCount();
				}
				if (!p.isAlive()) {
					waves[currentWave].setKilledEnemyCount(waves[currentWave]
							.getKilledEnemyCount() + 1);
					displayer.addKillCount();
					penguinIterator.remove();
				}
				
			}

			murlocIterator = murlocs.iterator();
			while (murlocIterator.hasNext()) {
				Murloc mur = murlocIterator.next();
				mur.frameImage();
				mur.tick();

				if (!mur.isAlive()) {
					waves[currentWave].setKilledEnemyCount(waves[currentWave]
							.getKilledEnemyCount() + 1);
					displayer.addKillCount();
					murlocIterator.remove();
				} else if (mur.isEscaped()) {
					escapedCount++;
					displayer.addEscapedCount();
					mur.setAlive(false);
				}
			}

			axeIterator = axes.iterator();
			while (axeIterator.hasNext()) {
				Axe a = axeIterator.next();
				a.frameImage();
				a.tick();

				if (!a.isAlive()) {
					waves[currentWave].setKilledEnemyCount(waves[currentWave]
							.getKilledEnemyCount() + 1);
					displayer.addKillCount();
					axeIterator.remove();
				} else if (a.isEscaped()) {
					escapedCount++;
					displayer.addEscapedCount();
					a.setAlive(false);
				}
			}

			explosionIterator = explosions.iterator();
			while (explosionIterator.hasNext()) {
				Explosion e = explosionIterator.next();
				e.tick();
				if (e.getFrames() >= Explosion.maxFrames) {
					explosionIterator.remove();
				}
			}
			
			fireballIterator = fireballs.iterator();
			while(fireballIterator.hasNext()){
				FireBall ball = fireballIterator.next();
				ball.tick();
				if(!ball.isActive()){
					fireballIterator.remove();
				}
			}

			if (enemyCount >= waves[currentWave].getMaxEnemyCount()
					&& penguins.isEmpty() && murlocs.isEmpty()
					&& axes.isEmpty()) {
				enemyCount = 0;
				waves[currentWave].finish();
				cooldown = tickCount + waveWait;
				if (currentWave != waves.length - 1) {
					currentWave++;
					waves[currentWave].start();
					displayer = null;
					displayer = new StatusDisplayer(
							waves[currentWave].getName(),
							waves[currentWave].getMaxEnemyCount(),
							escapedCount, waves[currentWave].getDelay());

				}
			}

		}
		pauser.tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		//
		// The last rendered thing drawn last
		// So it stays top most
		// =======================================
		if (ticker) {

			// Rendering Background
			bg.render(g);

			// Rendering Buildings
			for (Building r : roofs) {
				r.render(g);
			}

			// Rendering Status displayer
			displayer.render(g);

			// Rendering Penguins
			penguinIterator = penguins.iterator();
			while (penguinIterator.hasNext()) {
				penguinIterator.next().render(g);
			}

			// Rendering Missiles
			missileIterator = missiles.iterator();
			while (missileIterator.hasNext()) {
				missileIterator.next().render(g);
			}

			murlocIterator = murlocs.iterator();
			while (murlocIterator.hasNext()) {
				murlocIterator.next().render(g);
			}

			axeIterator = axes.iterator();
			while (axeIterator.hasNext()) {
				axeIterator.next().render(g);
			}

			// Rendering Main Character
			hero.render(g);

			// Rendering Exit Portal
			gate.render(g);
			
			// Rendering Fire Balls
			fireballIterator = fireballs.iterator();
			while(fireballIterator.hasNext()){
				fireballIterator.next().render(g);
			}

			// Rendering explosion
			explosionIterator = explosions.iterator();
			while (explosionIterator.hasNext()) {
				explosionIterator.next().render(g);
			}

		} else {
			// Rendering pause screen
			pauser.render(g);
		}
		g.dispose();
		bs.show();
	}
}
