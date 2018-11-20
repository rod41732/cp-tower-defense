package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SuperManager {
	
	private static SuperManager instance = new SuperManager();
	
	private BooleanProperty isInGameProp = new SimpleBooleanProperty(false);
	private BooleanProperty isGamePausedProp = new SimpleBooleanProperty(false);
	private BooleanProperty nextWaveAvailableProp = new SimpleBooleanProperty(false);
	private BooleanProperty canUpgradeProp = new SimpleBooleanProperty(false);
	private BooleanProperty canSellProp = new SimpleBooleanProperty(false);
	
	
	
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
