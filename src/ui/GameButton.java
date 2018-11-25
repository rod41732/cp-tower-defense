package ui;


import java.util.ArrayList;

import constants.Images;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Tower;

public class GameButton {
	
	private Button nextButton;
	private Button resumeButton; 
	private Button toMenuButton; 
	private Button pauseButton; // for pause menu
	private Button sellButton;
	private Button upgradeButton;
	
	
	
	private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
	private ToggleGroup toggleGroup;
	
	public GameButton(Pane pane) {
		Font buttonFont = new Font("KenVector Future Regular", 20);
		Font buttonFontSmall = new Font("KenVector Future Regular", 12);
		sellButton = ButtonMaker.make(1400, 780, Images.buttonSell, Images.buttonSellPressed, Images.buttonSellHover, Images.buttonSellDisabled,
				buttonFont, "Sell Tower");		
		sellButton.setOnAction(e -> {
			GameManager.getInstance().sellTower();
		});
		nextButton = ButtonMaker.make(820, 0, Images.buttonNext, Images.buttonNextPressed, Images.buttonNextHover, Images.buttonNextDisabled,
				buttonFont, "Next Wave");
		nextButton.setOnAction(e -> {
			GameManager.getInstance().requestNextWave();
		});
		
		upgradeButton = ButtonMaker.make(1400, 840, Images.buttonUpgrade, Images.buttonUpgradePressed, Images.buttonUpgradeHover, Images.buttonUpgradeDisabled,
				buttonFont, "Upgrade");
		upgradeButton.setOnAction(e -> {
			GameManager.getInstance().upgradeTower();
		});		
		
		toMenuButton = ButtonMaker.make(700, 480, Images.buttonNext, Images.buttonNextPressed, Images.buttonNextHover, Images.buttonNextDisabled,
				buttonFont, "Main menu");
		toMenuButton.setVisible(false);
		toMenuButton.setOnAction(e -> {
			SuperManager.getInstance().onLeaveGame();
		});
		toMenuButton.setVisible(false);
		
		resumeButton = ButtonMaker.make(700, 400, Images.buttonUpgrade, Images.buttonUpgradePressed, Images.buttonUpgradeHover, Images.buttonUpgradeDisabled,
				buttonFont, "Resume");
		resumeButton.setVisible(false);
		resumeButton.setOnAction(e -> {
			SuperManager.getInstance().onResumeGame();
		});
		resumeButton.setVisible(false);
		
		pauseButton = ButtonMaker.make(1020, 0, Images.buttonPause, Images.buttonPausePressed, Images.buttonPauseHover, Images.buttonPauseDisabled,
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
		
		pane.getChildren().addAll(pauseButton, // resumeButton, toMenuButton,
				nextButton, upgradeButton, sellButton);
		pane.getChildren().addAll(toggleButtons);
		
		toggleGroup.selectedToggleProperty().addListener((obs, old, nw) -> {
			boolean paused = SuperManager.getInstance().getIsGamePausedProp().get();
			boolean isPlacing = nw != null;

			pauseButton.setDisable(paused || isPlacing);
			nextButton.setDisable(paused || isPlacing);
			

			SuperManager.getInstance().getTowerChoiceProp().set(isPlacing ? (int)nw.getUserData() : -1);				
					 
		});
		SuperManager.getInstance().getTowerChoiceProp().addListener((obs, old, nw) -> {
			int val = nw.intValue();
			if (val >= 0) {
				toggleGroup.selectToggle(toggleButtons.get(val)); 
			}
			else {
				toggleGroup.selectToggle(null);
			}
		});
		
		SuperManager.getInstance().getCanSellProp().addListener((obs, old, nw) -> {
			boolean canSell = nw.booleanValue();
			sellButton.setDisable(!canSell);
		});
		
		SuperManager.getInstance().getCanUpgradeProp().addListener((obs, old, nw) -> {
			boolean canUpgrade = nw.booleanValue();
			upgradeButton.setDisable(!canUpgrade);			
		});
		
		SuperManager.getInstance().getnextWaveAvailableProp().addListener((obs, old, nw) -> {
			boolean avail = nw.booleanValue();
			nextButton.setDisable(!avail);
		});
		SuperManager.getInstance().getIsGamePausedProp().addListener((obs, old, nw) -> {
			boolean paused = nw.booleanValue();
			boolean isPlacing = toggleGroup.getSelectedToggle() != null;
			boolean canUp = SuperManager.getInstance().getCanUpgradeProp().get();
			boolean canSell = SuperManager.getInstance().getCanSellProp().get();
			resumeButton.setVisible(paused);
			toMenuButton.setVisible(paused);
			pauseButton.setDisable(paused || isPlacing);
			nextButton.setDisable(paused || isPlacing);
			sellButton.setDisable(paused || !canSell);
			upgradeButton.setDisable(paused || !canUp);
			for (ToggleButton tg: toggleButtons) {
				tg.setDisable(paused);
			}
		});
		
	
	}
	
	public void addMenuButtons(Pane pane) {
		pane.getChildren().addAll(resumeButton, toMenuButton);
	}
	public void setUpgradeText(String text) {
		upgradeButton.setText(text);
	}	
}
