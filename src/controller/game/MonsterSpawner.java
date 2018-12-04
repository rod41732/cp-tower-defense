package controller.game;

import java.util.ArrayList;

import constants.Images;
import model.Monster;
import model.monster.Boss1;
import model.monster.FlyingMonster;
import model.monster.GroundMonster;
import util.cpp;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private static ArrayList<MonsterSpawningStage> stages = new ArrayList<>(); 
	private int index= 0;
	static {
		for (int i=0; i<20; i++) {
			MonsterSpawningStage stage = new MonsterSpawningStage(500000, // delay between each "part" of wave
					// spawns two monster, with 100ms nanosec delay, i+1 times, with 500ms nanosec delay
					// so it will e like this xxx--yyy--------xxx--yyy---------xxx--yyy------ and so on
					// useful when spawning mixed multiple monsters 
					new MonsterSpawningSequence(500000, 100000, i+10, 
							new FlyingMonster("XXX", Images.bear, 5, 5, 0.2, 30, 5, 0.3, 5), 
							new FlyingMonster("YYY", Images.bear, 5, 5, 0.2, 30, 5, 0.8, 5)
							),
					// like above but with differnt set of monster
					new MonsterSpawningSequence(500000, 100000, i+10, // part 2 of wave
//							new Boss1(5, 5),
							new GroundMonster("Fly", Images.moose, 5, 5, 0.2, 30, 5, 0.8, 5)
							)); // .. there can be more
			
			stages.add(stage);
		}
	}
	

	
	public MonsterSpawner() {	
//		pii tile = gm.getStartTilePos();	
	}
	
	
	
	
	
	public void nextWave() {
		if (!isReady()) {
			System.out.println("not ready yet");
			return;
		}
		System.out.println("stage" + index);
		stages.get(index).play();
		index++;
	}
	
	public void pauseWave() {
		MonsterSpawnerThread.onGamePause();
	}
	
	public void cancelWave() {
		MonsterSpawnerThread.onGameReset();
	}
	
	public void resumeWave() {
		MonsterSpawnerThread.onGameResume();	
	}
	
	public void reset() {
		cancelWave();
		index = 0;
	}
	
	
	public boolean isReady() {
		return MonsterSpawnerThread.isIdle();
	}
	
	public static MonsterSpawner getInstace() {
		return instance;
	}
	
}

