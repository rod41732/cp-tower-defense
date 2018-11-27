package controller.game;

import java.util.ArrayList;

import constants.Images;
import model.Monster;
import model.monster.GroundMonster;
import util.cpp;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private GameManager gm;

	
	public void bindTo(GameManager gm) {
		this.gm = gm;
	}
	
	public MonsterSpawner() {	
//		pii tile = gm.getStartTilePos();	
	}
	
	
	
	
	public void nextWave() {
		cpp.pii tile = gm.getStartTilePos();
		ArrayList<Monster> mons = new ArrayList<>();
		mons.add(new GroundMonster("brea", Images.bear, tile.first+0.5, tile.second+0.5, 0.5, 60, 1.5, 1.5, 3));
		MonsterSpawningSequence seq1 = new MonsterSpawningSequence(200000, 1000, mons, 30);
		MonsterSpawnerThread.addSequence(seq1);}
	
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

