package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import main.Main;

public class SuperManager {
	
	private static SuperManager instance = new SuperManager();
	
	private BooleanProperty isInGameProp = new SimpleBooleanProperty();
	private BooleanProperty isGamePausedProp = new SimpleBooleanProperty();
	private BooleanProperty nextWaveAvailableProp = new SimpleBooleanProperty();
	private BooleanProperty canUpgradeProp = new SimpleBooleanProperty();
	private BooleanProperty canSellProp = new SimpleBooleanProperty();
	private IntegerProperty towerChoiceProp = new SimpleIntegerProperty();
	
	public SuperManager() {
		isInGameProp.set(false);
		isInGameProp.set(true);
		nextWaveAvailableProp.set(false);
		canUpgradeProp.set(false);
		canSellProp.set(false);
		towerChoiceProp.set(-1);
	}
	
	
	public void onGamePause() {
		isGamePausedProp.set(true);
		isInGameProp.set(true);
	}
	
	public void onResumeGame() {
		isGamePausedProp.set(false);
		isInGameProp.set(true);
		Main.setScene(Main.getGameScene());
	}
	
	public void onLeaveGame() {
		isGamePausedProp.set(true);
		isInGameProp.set(false);
		new Timeline(new KeyFrame(Duration.seconds(0.3), e -> {
			Main.setScene(Main.getMainMenu());			
		})).play();
	}
	
	
	public void onReset() {
		MonsterSpawner.getInstace().cancelWave();
		MonsterSpawner.getInstace().reset();
		isGamePausedProp.set(true);
		isInGameProp.set(false);
	}


	public static SuperManager getInstance() {
		return instance;
	}

	public BooleanProperty getIsInGameProp() {
		return isInGameProp;
	}

	public BooleanProperty getIsGamePausedProp() {
		return isGamePausedProp;
	}

	public BooleanProperty getCanUpgradeProp() {
		return canUpgradeProp;
	}

	public BooleanProperty getCanSellProp() {
		return canSellProp;
	}

	public BooleanProperty getnextWaveAvailableProp() {
		return nextWaveAvailableProp;
	}

	public IntegerProperty getTowerChoiceProp() {
		return towerChoiceProp;
	}
	
	
}
