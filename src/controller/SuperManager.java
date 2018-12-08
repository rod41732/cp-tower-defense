package controller;

import controller.game.GameManager;
import controller.game.MonsterSpawner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import main.Main;
import sharedobject.SharedObject;

public class SuperManager {
	
	private static SuperManager instance = new SuperManager();
	
	private BooleanProperty isInGameProp = new SimpleBooleanProperty();
	private BooleanProperty isGamePausedProp = new SimpleBooleanProperty();
	private BooleanProperty nextWaveAvailableProp = new SimpleBooleanProperty();
	private BooleanProperty canUpgradeProp = new SimpleBooleanProperty();
	private BooleanProperty canSellProp = new SimpleBooleanProperty();
	private IntegerProperty towerChoiceProp = new SimpleIntegerProperty();
	private IntegerProperty gameStateProp = new SimpleIntegerProperty();
	private BooleanProperty shouldDisplayPathProp = new SimpleBooleanProperty();
	
	public SuperManager() {
		shouldDisplayPathProp.set(true);
		isInGameProp.set(false);
		nextWaveAvailableProp.set(false);
		canUpgradeProp.set(false);
		canSellProp.set(false);
		gameStateProp.set(0);
		towerChoiceProp.set(-1);
	}
	
	// Renderer and Updater auto updates on State change so no need to pause them
	public void onGamePause() {
		isGamePausedProp.set(true);
		isInGameProp.set(true);
		MonsterSpawner.getInstace().pauseWave();
	}
	
	public void onResumeGame() {
		Main.setScene(Main.getGameScene());
		isGamePausedProp.set(false);
		isInGameProp.set(true);
		MonsterSpawner.getInstace().resumeWave();
	}
	
	public void onLeaveGame() {
		isGamePausedProp.set(true);
		isInGameProp.set(false);
		Main.setScene(Main.getMainMenu());
	}
	
	
	public void onReset() {
		MonsterSpawner.getInstace().reset();
		GameManager.getInstance().reset();
		SharedObject.getInstance().clear();
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

	public IntegerProperty getGameStateProp() {
		return gameStateProp;
	}


	public BooleanProperty getShouldDisplayPathProp() {
		return shouldDisplayPathProp;
	}
	
	
	
}
