package controller;

import javafx.beans.property.BooleanProperty;

public class SuperManager {
	
	private static SuperManager instance = new SuperManager();
	
	private BooleanProperty isInGameProp;
	private BooleanProperty isGamePausedProp;
	private BooleanProperty nextWaveAvailableProp;
	private BooleanProperty canUpgradeProp;
	private BooleanProperty canSellProp;
	
	
	
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
