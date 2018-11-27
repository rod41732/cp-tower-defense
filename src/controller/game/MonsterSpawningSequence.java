package controller.game;

import java.util.ArrayList;

import controller.SuperManager;
import javafx.animation.Timeline;
import model.Monster;

public class MonsterSpawningSequence extends Thread {

	
	public MonsterSpawningSequence(int delay, int delay2, int repeat, ArrayList<Monster> monstersList) {
		super(new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<repeat; i++) {
					try {
						Thread.sleep(delay/1000, delay%1000000);
						for (Monster m: monstersList) {
							Thread.sleep(delay2/1000, delay2%1000000);
							while (SuperManager.getInstance().getIsGamePausedProp().get()) {
								Thread.sleep(16); // pause when game paused;
							}
							System.out.println("spawned " + i);
							GameManager.getInstance().getMonsters().add((Monster) m.clone());
						}		
					}
					catch (InterruptedException e) {
						System.out.println("[W] A monster spawning sequence has been interrupted");
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
							while (SuperManager.getInstance().getIsGamePausedProp().get()) {
								Thread.sleep(16); // pause when game paused;
							}
							System.out.println("spawned " + i);
							GameManager.getInstance().getMonsters().add((Monster) m.clone());
						}		
					}
					catch (InterruptedException e) {
						System.out.println("[W] A monster spawning sequence has been interrupted");
					}
				
				}
			}
		});
	}
	
	public MonsterSpawningSequence(int delay) { // empty spawner for delay
		this(delay, 1000, 1, new ArrayList<>()); 
	}
}
