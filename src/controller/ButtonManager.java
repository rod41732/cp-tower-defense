package controller;


import java.util.ArrayList;

import constants.Images;import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import main.Main;
import model.Tower;
import ui.ButtonMaker;

public class ButtonManager {
	
	private Button nextButton;
	private Button resumeButton; 
	private Button toMenuButton; 
	private Button pauseButton; // for pause menu
	private Button sellButton;
	private Button upgradeButton;
	
	
	
	private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
	private ToggleGroup toggleGroup;
	
	public ButtonManager(Pane pane) {
		Font buttonFont = new Font("KenVector Future Regular", 20);
		Font buttonFontSmall = new Font("KenVector Future Regular", 12);
		sellButton = ButtonMaker.make(1400, 700, Images.buttonSell, Images.buttonSellPressed,
				buttonFont, "Sell Tower");		
		sellButton.setOnAction(e -> {
			GameManager.getInstance().sellTower();
		});

		nextButton = ButtonMaker.make(1020, 80, Images.buttonNext, Images.buttonNextPressed,
				buttonFont, "Next Wave");
		nextButton.setOnAction(e -> {
			GameManager.getInstance().requestNextWave();
		});
		
		upgradeButton = ButtonMaker.make(1400, 760, Images.buttonUpgrade, Images.buttonUpgradePressed, 
				buttonFont, "Upgrade");
		upgradeButton.setOnAction(e -> {
			GameManager.getInstance().upgradeTower();
		});		
		
		toMenuButton = ButtonMaker.make(700, 480, Images.buttonNext, Images.buttonNextPressed, 
				buttonFont, "Main menu");
		toMenuButton.setVisible(false);
		toMenuButton.setOnAction(e -> {
			SuperManager.getInstance().onLeaveGame();
		});
		toMenuButton.setVisible(false);
		
		resumeButton = ButtonMaker.make(700, 400, Images.buttonUpgrade, Images.buttonUpgradePressed, buttonFont, "Resume");
		resumeButton.setVisible(false);
		resumeButton.setOnAction(e -> {
			SuperManager.getInstance().onResumeGame();
		});
		resumeButton.setVisible(false);
		
		pauseButton = ButtonMaker.make(1020, 20, Images.buttonPause, Images.buttonPausePressed,
				buttonFont, "Pause");
		pauseButton.setOnAction(e -> {
			SuperManager.getInstance().onGamePause();
		});		

		
		
		toggleGroup = new ToggleGroup();
		for (int i=0; i<4; i++) {
			Tower twr = GameManager.getInstance().createTower(i, 0, 0);
			ToggleButton tg = ButtonMaker.makeTowerButton(1344+(i%3)*85, (i/3)*128,
					Images.towerButton, Images.towerButtonPressed, twr, buttonFontSmall, i);
			toggleButtons.add(tg);
		}
		toggleGroup.getToggles().addAll(toggleButtons);
		
		pane.getChildren().addAll(pauseButton, resumeButton, toMenuButton,
				nextButton, upgradeButton, sellButton);
		pane.getChildren().addAll(toggleButtons);
		
		
		SuperManager.getInstance().getCanSellProp().addListener((obs, old, nw) -> {
//			if (old.booleanValue() != nw.booleanValue()) return ;
			boolean canSell = nw.booleanValue();
			boolean paused = SuperManager.getInstance().getIsGamePausedProp().get();
			sellButton.setDisable(!canSell || paused);
		});
		
		SuperManager.getInstance().getCanUpgradeProp().addListener((obs, old, nw) -> {
//			if (old.booleanValue() != nw.booleanValue()) return ;
			boolean canUpgrade = nw.booleanValue();
			boolean paused = SuperManager.getInstance().getIsGamePausedProp().get();
			upgradeButton.setDisable(!canUpgrade || paused);			
		});
		
		SuperManager.getInstance().getnextWaveAvailableProp().addListener((obs, old, nw) -> {
//			if (old.booleanValue() != nw.booleanValue()) return ;
			boolean avail = nw.booleanValue();
			boolean paused = SuperManager.getInstance().getIsGamePausedProp().get();
			nextButton.setDisable(!avail || paused);
		});
		SuperManager.getInstance().getIsGamePausedProp().addListener((obs, old, nw) -> {
//			if (old.booleanValue() != nw.booleanValue()) return ;
			boolean paused = nw.booleanValue();
			boolean inGame = SuperManager.getInstance().getIsInGameProp().get();
			resumeButton.setVisible(paused && inGame);
			toMenuButton.setVisible(paused && inGame);
			pauseButton.setVisible(!paused && inGame);
			nextButton.setVisible(!paused && inGame);
			sellButton.setDisable(paused);
			upgradeButton.setDisable(paused);
		});
		
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) ->{
//			if (old.booleanValue() != nw.booleanValue()) return ;
			boolean inGame = nw.booleanValue();
			boolean paused = SuperManager.getInstance().getIsGamePausedProp().get();
			resumeButton.setVisible(inGame && paused);
			toMenuButton.setVisible(inGame && paused);
			pauseButton.setVisible(inGame && !paused);
			nextButton.setVisible(inGame && !paused);
			sellButton.setVisible(inGame);
			upgradeButton.setVisible(inGame);
		});
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}
	
	
}
