package controller;

import constants.Images;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.monster.GroundMonster;
import util.cpp;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private Timeline stage;
	private boolean isReady = true;
	
	
	public MonsterSpawner() {
		
		stage = new Timeline();
		stage.getKeyFrames().add(new KeyFrame(Duration.seconds(1./3), e ->  {
			cpp.pii startTile = GameManager.getInstance().getStartTilePos();
			GameManager.getInstance().spawnMonster(new GroundMonster("Bear", Images.bear, startTile.first+0.5, startTile.second+0.5,
					0.3, 60, 1.5, 3, 10));
			System.out.println("spawned monster");
		}));
		stage.setOnFinished(e -> {
			isReady = true;
		});
		stage.setCycleCount(20);
	}
	public void play() {
		isReady = false;
		stage.play();
	}
	
	public void pause() {
		stage.pause();
	}
	
	public void resume() {
		if (!isReady) 
			stage.play();
	}
	
	
	public boolean isReady() {
		return isReady;
	}
	
	public static MonsterSpawner getInstace() {
		return instance;
	}
	
}
