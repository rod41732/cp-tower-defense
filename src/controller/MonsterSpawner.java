package controller;

import constants.Images;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.monster.GroundMonster;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private Timeline stage;
	private boolean isReady = true;
	
	
	public MonsterSpawner() {
		
		stage = new Timeline();
		stage.getKeyFrames().add(new KeyFrame(Duration.seconds(1./3), e ->  {
			GameManager gi = GameManager.getInstance();
			GameManager.getInstance().spawnMonster(new GroundMonster("Bear", Images.bear, gi.getStartCol()+0.5, gi.getStartRow()+0.5,
					0.3, 100, 1.5, 3, 1));
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
	
	public boolean isReady() {
		return isReady;
	}
	
	public static MonsterSpawner getInstace() {
		return instance;
	}
	
}
