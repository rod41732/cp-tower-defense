package controller.game;

import java.util.ArrayList;

import controller.SuperManager;
import model.monster.CarArmored;
import model.monster.CarBoss;
import model.monster.CarFast;
import model.monster.CarHeavy;
import model.monster.CarNormal;
import model.monster.SoldierArmored;
import model.monster.SoldierBoss;
import model.monster.SoldierFast;
import model.monster.SoldierHeavy;
import model.monster.SoldierNormal;
import model.monster.TankArmored;
import model.monster.TankBoss;
import model.monster.TankFast;
import model.monster.TankHeavy;
import model.monster.TankNormal;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private static ArrayList<MonsterSpawningStage> stages = new ArrayList<>(); 
	private int index= 0;
	static {
		for (int i=0; i<20; i++) {
			stages.add(new MonsterSpawningStage(400000));
		}
		for (int i=0; i<20; i++) {
			if (i <= 5) {
				stages.get(i).addSequence(
						new MonsterSpawningSequence(500000, 200000, 10,
								new SoldierNormal(0, 0, i/5),
								new SoldierFast(0, 0, i/5)));
			}
			else if (i >= 5 && i%2 == 0) { 
				stages.get(i).addSequence(
						new MonsterSpawningSequence(500000, 200000, 10,
								new SoldierArmored(0, 0, i/5),
								new SoldierNormal(0, 0, i/5)));
			}
			else {
				stages.get(i).addSequence(
						new MonsterSpawningSequence(500000, 200000, 10,
								new SoldierHeavy(0, 0, i/5)));
			}
		}
		for (int i=7; i<20; i++) {
			if (i%3 == 0) { 
				stages.get(i).addSequence(
						new MonsterSpawningSequence(500000, 200000, 5,
								new CarArmored(0, 0, i/5),
								new CarNormal(0, 0, i/5)));
			}
			else {
				stages.get(i).addSequence(
						new MonsterSpawningSequence(500000, 200000, 5,
								new CarHeavy(0, 0, i/5)));
			}
		}
		for (int i=9; i<20; i++) {
			if (i%4 == 0) { 
				stages.get(i).addSequence(
						new MonsterSpawningSequence(500000, 200000, 3,
								new TankArmored(0, 0, i/5),
								new TankNormal(0, 0, i/5)));
			}
			else {
				stages.get(i).addSequence(
						new MonsterSpawningSequence(500000, 200000, 3,
								new TankHeavy(0, 0, i/5)));
			}
		}
		for (int i=9; i<20; i++) {
			if (i%5 == 4) {
				stages.get(i).addSequence(new MonsterSpawningSequence(500000, 200000, i/4,
						new SoldierBoss(0, 0, i/5)));
			}
		}
		for (int i=9; i<20; i++) {
			if (i%5 == 3) {
				stages.get(i).addSequence(new MonsterSpawningSequence(500000, 200000, i/4,
						new TankBoss(0, 0, i/5)));
			}
		}
		for (int i=9; i<20; i++) {
			if (i%5 == 2) {
				stages.get(i).addSequence(new MonsterSpawningSequence(1000000, 200000, i/4,
						new CarBoss(0, 0, i/5)));
			}
		}
	}
	
	
	public void nextWave() {
		if (!isReady()) {
			System.out.println("not ready yet");
			return;
		}
		System.out.println("stage" + index);
		stages.get(index).play();
		index++;
		if (index >= stages.size()) {
			SuperManager.getInstance().getGameStateProp().set(2);
		}
	}
	
	public void pauseWave() {
		MonsterSpawnerThread.onGamePause();
	}
	
	public void cancelWave() {
		MonsterSpawnerThread.getInstance().onGameReset();
	}
	
	public void resumeWave() {
		MonsterSpawnerThread.onGameResume();	
	}
	
	public void reset() {
		cancelWave();
		index = 0;
	}
	
	
	public int getLevel() {
		return index;
	}
	
	
	public boolean isReady() {
		return MonsterSpawnerThread.isIdle();
	}
	
	public static MonsterSpawner getInstace() {
		return instance;
	}
	
}

