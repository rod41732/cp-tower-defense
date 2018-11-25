package controller.game;

import constants.Images;
import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.monster.GroundMonster;
import util.cpp;

public class MonsterSpawner {
	
	private static MonsterSpawner instance = new MonsterSpawner();
	private Timeline stage;
	private boolean isReady = true;
	private GameManager gm;

	
	public void bindTo(GameManager gm) {
		this.gm = gm;
	}
	
	public MonsterSpawner() {
		
		stage = new Timeline();
		stage.getKeyFrames().add(new KeyFrame(Duration.seconds(1./3), e ->  {
			cpp.pii startTile = gm.getStartTilePos();
			gm.updater.spawnMonster(new GroundMonster("Bear", Images.bear, startTile.first+0.5, startTile.second+0.5,
					0.3, 60, 1.5, 3, 10));
		}));
		stage.setOnFinished(e -> {
			isReady = true;
		});
		stage.setCycleCount(20);
	
		SuperManager.getInstance().getIsGamePausedProp().addListener((obs, old, nw) -> {
			boolean pause = nw.booleanValue();
			boolean inGame = SuperManager.getInstance().getIsInGameProp().get();
			if (!pause && inGame) {
				resumeWave();				
			}
			else {
				pauseWave();
			}
		});
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			boolean inGame = nw.booleanValue();
			boolean pause = SuperManager.getInstance().getIsGamePausedProp().get();
			if (!pause && inGame) {
				resumeWave();				
			}
			else {
				pauseWave();
			}
		});
	}
	public void nextWave() {
		isReady = false;
		stage.play();
	}
	
	public void pauseWave() {
		stage.pause();
	}
	
	public void cancelWave() {
		isReady = true;
		stage.stop();
	}
	
	public void resumeWave() {
		if (!isReady) 
			stage.play();
	}
	
	public void reset() {
		
	}
	
	
	public boolean isReady() {
		return isReady;
	}
	
	public static MonsterSpawner getInstace() {
		return instance;
	}
	
}
