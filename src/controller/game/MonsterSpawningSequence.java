package controller.game;

import java.util.ConcurrentModificationException;

import model.Monster;

public class MonsterSpawningSequence extends Thread {
	private static boolean isPaused;
	private static boolean shouldStop;
	public MonsterSpawningSequence(int delay, int delay2, int repeat, Monster ...monstersList) {
		super(new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<repeat && !shouldStop; i++) {
					try {
						Thread.sleep(delay/1000, delay%1000*1000);
						for (Monster m: monstersList) {
							Thread.sleep(delay2/1000, delay2%1000*1000);
							while (isPaused) {
								Thread.sleep(16); // pause when game paused;
//								if (shouldStop) break;
							}
							if (shouldStop) break;
							try {
								GameManager.getInstance().addMonsterDefault((Monster) m.clone());								
							}
							catch (ConcurrentModificationException e) {
								// prevent thread stopping
							}
						}	
					}
					catch (InterruptedException e) {
						System.out.println("[W] A monster spawning sequence has been interrupted");
						break;
					}
	
				}
				shouldStop = false;
			}
		});	
	}

	
	public static void onGamePause() {
		isPaused = true;
	}
	public static void onGameResume() {
		isPaused = false;
	}
	
	
	public MonsterSpawningSequence(int delay) { // empty spawner for delay
		this(delay, 1000, 1); 
	}

	public static void onGameReset() {
		shouldStop = true;
	}
}
