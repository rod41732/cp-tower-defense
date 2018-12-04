package controller.game;

import java.util.ArrayList;

import controller.SuperManager;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.Monster;

public class MonsterSpawningSequence extends Thread {
	private static boolean isPaused;
	
	public MonsterSpawningSequence(int delay, int delay2, int repeat, ArrayList<Monster> monstersList) {
		super(new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<repeat; i++) {
					try {
						while (isPaused) {
							Thread.sleep(16);
						}
						Thread.sleep(delay/1000, delay%1000000);
						for (Monster m: monstersList) {
							Thread.sleep(delay2/1000, delay2%1000000);
							while (isPaused) {
								Thread.sleep(16); // pause when game paused;
							}
							System.out.println("spawned " + i);
							GameManager.getInstance().addMonster((Monster) m.clone());
						}		
					}
					catch (InterruptedException e) {
						System.out.println("[W] A monster spawning sequence has been interrupted");
						break;
					}
	
				}
			}
		});
	}

	public MonsterSpawningSequence(int delay, int delay2, int repeat, Monster ...monstersList) {
		super(new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<repeat; i++) {
					try {
						Thread.sleep(delay/1000, delay%1000000);
						for (Monster m: monstersList) {
							Thread.sleep(delay2/1000, delay2%1000000);
							while (isPaused) {
								Thread.sleep(16); // pause when game paused;
							}
							if (Thread.currentThread().isInterrupted()) return;
							System.out.println("spawned2 " + i);
							GameManager.getInstance().addMonster((Monster) m.clone());
						}	
					}
					catch (InterruptedException e) {
						System.out.println("[W] A monster spawning sequence has been interrupted");
						return;
					}
	
				}
			}
		});	
	}
	
	@Override
	public void interrupt() {
		System.out.println("interrupted");
		Thread.currentThread().interrupt();
		System.out.println("is interrupted ? " + currentThread().isInterrupted());
		super.interrupt();
	}
	
	public static void onGamePause() {
		isPaused = true;
	}
	public static void onGameResume() {
		isPaused = false;
	}
	
	
	public MonsterSpawningSequence(int delay) { // empty spawner for delay
		this(delay, 1000, 1, new ArrayList<>()); 
	}
}
