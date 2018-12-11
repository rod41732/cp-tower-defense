package ui;


import java.util.ArrayList;

import constants.Images;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
		//sell and Upgrade menu
		sellButton = ButtonMaker.make(Images.buttonOrange, Images.buttonOrangePressed, Images.buttonOrangeHover, Images.buttonOrangeDisabled,
				buttonFont, "Sell Tower");		
		sellButton.setOnAction(e -> {
			GameManager.getInstance().sellTower();
		});
		upgradeButton = ButtonMaker.make(Images.buttonGreen, Images.buttonGreenPressed, Images.buttonGreenHover, Images.buttonGreenDisabled,
				buttonFont, "Upgrade");
		upgradeButton.setOnAction(e -> {
			GameManager.getInstance().upgradeTower();
		});
		
		//Pause menu
		toMenuButton = ButtonMaker.make(Images.buttonPurple, Images.buttonPurplePressed, Images.buttonPurpleHover, Images.buttonPurpleDisabled,
				buttonFont, "Main menu");
		toMenuButton.setVisible(false);
		toMenuButton.setOnAction(e -> {
			SuperManager.getInstance().onLeaveGame();
		});
		toMenuButton.setVisible(false);
		toMenuButton.setLayoutX(700);
		toMenuButton.setLayoutY(400);
		resumeButton = ButtonMaker.make(Images.buttonGreen, Images.buttonGreenPressed, Images.buttonGreenHover, Images.buttonGreenDisabled,
				buttonFont, "Resume");
		resumeButton.setVisible(false);
		resumeButton.setOnAction(e -> {
			SuperManager.getInstance().onResumeGame();
		});
		resumeButton.setLayoutX(700);
		resumeButton.setLayoutY(480);
		resumeButton.setVisible(false);
		
		//Bottom bar
		pauseButton = ButtonMaker.make(Images.buttonYellow, Images.buttonYellowPressed, Images.buttonYellowHover, Images.buttonYellowDisabled,
				buttonFont, "Pause");
		pauseButton.setOnAction(e -> {
			SuperManager.getInstance().onGamePause();
		});		
		
		showPathButton = ButtonMaker.make(Images.buttonYellow, Images.buttonYellowPressed, Images.buttonYellowHover, Images.buttonYellowDisabled,
				buttonFont, "Hide Path");
		showPathButton.setOnAction(e -> {
			BooleanProperty prop = SuperManager.getInstance().getShouldDisplayPathProp(); 
			prop.set(!prop.get());
		});		
		nextButton = ButtonMaker.make(Images.buttonPurple, Images.buttonPurplePressed, Images.buttonPurpleHover, Images.buttonPurpleDisabled,
				buttonFont, "Next Wave");
		nextButton.setOnAction(e -> {
			GameManager.getInstance().requestNextWave();
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
		for (int i=0; i<9; i++) { // loop through num of variant
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
			boolean shouldSpawn = SuperManager.getInstance().getnextWaveAvailableProp().get();
			nextButton.setDisable(paused || isPlacing || !shouldSpawn);
			
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
		VBox upGradePane = new VBox(4);
		upGradePane.setPadding(new Insets(5 , 0, 0, 0));
		upGradePane.setAlignment(Pos.CENTER);
		upGradePane.getChildren().addAll(upgradeButton, sellButton);
		pane.getChildren().add(upGradePane);
	}
}
