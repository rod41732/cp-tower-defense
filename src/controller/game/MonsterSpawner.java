package controller.game;

import java.util.ArrayList;

import constants.Images;
import model.Monster;
import model.monster.FlyingMonster;
import model.monster.GroundMonster;
import util.cpp;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private GameManager gm;
	private static ArrayList<MonsterSpawningStage> stages = new ArrayList<>();
	private int index= 0;
	static {
		for (int i=0; i<20; i++) {
			MonsterSpawningStage stage = new MonsterSpawningStage(500,
					new MonsterSpawningSequence(500000, 100000, i+1,
							new FlyingMonster("Fly", Images.bear, 5, 5, 0.2, 30, 5, 0.3, 5),
							new FlyingMonster("Fly", Images.bear, 5, 5, 0.2, 30, 5, 0.8, 5)
							),
					new MonsterSpawningSequence(500000, 100000, i+1,
							new GroundMonster("Fly", Images.moose, 5, 5, 0.2, 30, 5, 0.3, 5),
							new GroundMonster("Fly", Images.moose, 5, 5, 0.2, 30, 5, 0.8, 5)
							));
			
			stages.add(stage);
		}
	}
	
	
	public void bindTo(GameManager gm) {
		this.gm = gm;
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
//		stage.pause();
	}
	
	public void cancelWave() {
//		isReady = true;
//		stage.stop();
	}
	
	public void resumeWave() {
//		if (!isReady) 
//			stage.play();
	}
	
	public void reset() {
		
	}
	
	
	public boolean isReady() {
		return MonsterSpawnerThread.isIdle();
	}
	
	public static MonsterSpawner getInstace() {
		return instance;
	}
	
}

