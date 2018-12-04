package ui;


import java.util.ArrayList;

import constants.Images;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Tower;
import ui.component.ButtonMaker;

public class GameButton {
	
	private Button nextButton;
	private Button resumeButton; 
	private Button toMenuButton; 
	private Button pauseButton; // for pause menu
	private Button sellButton;
	private Button upgradeButton;
	private Button showPathButton;
	
	
	
	private ArrayList<ToggleButton> toggleButtons = new ArrayList<>();
	private ToggleGroup toggleGroup;
	
	public GameButton() {
		Font buttonFont = new Font("KenVector Future Regular", 20);
		Font buttonFontSmall = new Font("KenVector Future Regular", 12);
		sellButton = ButtonMaker.make(1364, 780, Images.buttonSell, Images.buttonSellPressed, Images.buttonSellHover, Images.buttonSellDisabled,
				buttonFont, "Sell Tower");		
		sellButton.setOnAction(e -> {
			GameManager.getInstance().sellTower();
		});
		nextButton = ButtonMaker.make(303, 879, Images.buttonNext, Images.buttonNextPressed, Images.buttonNextHover, Images.buttonNextDisabled,
				buttonFont, "Next Wave");
		nextButton.setOnAction(e -> {
			GameManager.getInstance().requestNextWave();
		});
		
		upgradeButton = ButtonMaker.make(1364, 724, Images.buttonUpgrade, Images.buttonUpgradePressed, Images.buttonUpgradeHover, Images.buttonUpgradeDisabled,
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
		
		pauseButton = ButtonMaker.make(55, 879, Images.buttonPause, Images.buttonPausePressed, Images.buttonPauseHover, Images.buttonPauseDisabled,
				buttonFont, "Pause");
		pauseButton.setOnAction(e -> {
			SuperManager.getInstance().onGamePause();
		});		
		
		showPathButton = ButtonMaker.make(455, 879, Images.buttonPause, Images.buttonPausePressed, Images.buttonPauseHover, Images.buttonPauseDisabled,
				buttonFont, "Show Path");
		showPathButton.setOnAction(e -> {
			BooleanProperty prop = SuperManager.getInstance().getShouldDisplayPathProp(); 
			prop.set(!prop.get());
		});		
		
		SuperManager.getInstance().getShouldDisplayPathProp().addListener((obs, old, nw) -> {
			boolean shouldShow = nw.booleanValue();
			if (shouldShow) {
				showPathButton.setText("Hide Path");
			} else  {
				showPathButton.setText("Show Path");
			}
		});
		
		
		toggleGroup = new ToggleGroup();
		for (int i=0; i<6; i++) {
			Tower twr = GameManager.getInstance().createTower(i, 0, 0);
			ToggleButton tg = ButtonMaker.makeTowerButton(1344+(i%3)*85, (i/3)*128,
					Images.towerButton, Images.towerButtonPressed, twr, buttonFontSmall, i);
			toggleButtons.add(tg);
		}
		toggleGroup.getToggles().addAll(toggleButtons);

		
		toggleGroup.selectedToggleProperty().addListener((obs, old, nw) -> {
			boolean paused = SuperManager.getInstance().getIsGamePausedProp().get();
			boolean isPlacing = nw != null;
			GameManager.getInstance().setSelectedTile(null);
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
			boolean gameOver = SuperManager.getInstance().getGameStateProp().get() != 0;
			resumeButton.setVisible(paused && !gameOver);
			toMenuButton.setVisible(paused);
			pauseButton.setDisable(paused || isPlacing);
			nextButton.setDisable(paused || isPlacing);
			sellButton.setDisable(paused || !canSell);
			upgradeButton.setDisable(paused || !canUp);
			for (ToggleButton tg: toggleButtons) {
				tg.setDisable(paused);
			}
		});
		
		SuperManager.getInstance().getGameStateProp().addListener((obs, old, nw) -> {
			boolean gameOver = nw.intValue() != 0;
			boolean paused = SuperManager.getInstance().getIsGamePausedProp().get();
			resumeButton.setVisible(paused && !gameOver);
		});
		
	
	}
	
	public void addMenuButtons(Pane pane) {
		pane.getChildren().addAll(resumeButton, toMenuButton);
	}
	
	public void addTowerButtons(Pane pane) {
		pane.getChildren().addAll(toggleButtons);
	}
	
	public void addControlButton(Pane pane) {
		pane.getChildren().addAll(pauseButton, showPathButton, nextButton);
	}
	public void setUpgradeText(String text) {
		upgradeButton.setText(text);
	}	
	
	public void addUpgradeButton(Pane pane) {
		pane.getChildren().addAll(upgradeButton, sellButton);
	}
}
