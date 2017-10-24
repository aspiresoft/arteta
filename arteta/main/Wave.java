package com.aspire.arteta.main;
public class Wave {

	private String name;
	private int delay;
	private int maxEnemyCount;
	private boolean finished;
	private boolean started;
	private int killedEnemyCount;

	public Wave(String name, int delay, int maxEnemyCount) {
		this.setName(name);
		this.setDelay(delay);
		this.setMaxEnemyCount(maxEnemyCount);
		setStarted(false);
		setFinished(false);
		setKilledEnemyCount(0);
	}

	public void start() {
		setStarted(true);
		setFinished(false);
	}

	public void finish() {
		setFinished(true);
		setStarted(false);
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getMaxEnemyCount() {
		return maxEnemyCount;
	}

	public void setMaxEnemyCount(int maxEnemyCount) {
		this.maxEnemyCount = maxEnemyCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKilledEnemyCount() {
		return killedEnemyCount;
	}

	public void setKilledEnemyCount(int killedEnemyCount) {
		this.killedEnemyCount = killedEnemyCount;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
