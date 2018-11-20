package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import main.Main;

public class SuperManager {
	
	private static SuperManager instance = new SuperManager();
	
	private BooleanProperty isInGameProp = new SimpleBooleanProperty();
	private BooleanProperty isGamePausedProp = new SimpleBooleanProperty();
	private BooleanProperty nextWaveAvailableProp = new SimpleBooleanProperty();
	private BooleanProperty canUpgradeProp = new SimpleBooleanProperty();
	private BooleanProperty canSellProp = new SimpleBooleanProperty();
	
	
	
	public SuperManager() {
		isInGameProp.set(false);
		isInGameProp.set(true);
		nextWaveAvailableProp.set(false);
		canUpgradeProp.set(false);
		canSellProp.set(false);
	}
	
	
	public void onGamePause() {
		MonsterSpawner.getInstace().pauseWave();
		isGamePausedProp.set(true);
		isInGameProp.set(true);
	}
	
	public void onResumeGame() {
		MonsterSpawner.getInstace().resumeWave();
		isGamePausedProp.set(false);
		isInGameProp.set(true);
	}
	
	public void onLeaveGame() {
		MonsterSpawner.getInstace().pauseWave();
		isGamePausedProp.set(true);
		isInGameProp.set(false);
		Main.setScene(Main.getMainMenu());
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

}
