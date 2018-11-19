package ui;


import constants.Images;
import controller.GameManager;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import main.Main;
import model.tower.BombTower;
import model.tower.NormalTower;

public class ButtonManager {
	
	private Button nextButton;
	private Button resumeButton; 
	private Button toMenuButton; 
	private Button pauseButton; // for pause menu
	private Button sellButton;
	private Button upgradeButton;
	
	
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
			onGameLeft();
		});
		
		resumeButton = ButtonMaker.make(700, 400, Images.buttonUpgrade, Images.buttonUpgradePressed, buttonFont, "Resume");
		resumeButton.setVisible(false);
		resumeButton.setOnAction(e -> {
			onGameResume();
		});
		
		pauseButton = ButtonMaker.make(1020, 20, Images.buttonPause, Images.buttonPausePressed,
				buttonFont, "Pause");
		pauseButton.setOnAction(e -> {
			onGamePause();
		});		
		

		
		
		ToggleGroup tg = new ToggleGroup();
		ToggleButton twr = ButtonMaker.makeTowerButton(1300, 400, Images.towerButton, Images.towerButtonPressed, new BombTower(0, 0), buttonFontSmall);
		ToggleButton twr2 = ButtonMaker.makeTowerButton(1300, 400, Images.towerButton, Images.towerButtonPressed, new NormalTower(0, 0), buttonFontSmall);

		tg.getToggles().addAll(twr, twr2);
		
		
		pane.getChildren().addAll(pauseButton, resumeButton, toMenuButton,
				nextButton, upgradeButton, sellButton, twr, twr2);
	}
	
	private void onGamePause() {
		GameManager.getInstance().pause();
		resumeButton.setVisible(true);
		pauseButton.setVisible(false);
		
		nextButton.setVisible(false);
		sellButton.setDisable(true);
		upgradeButton.setDisable(true);
		toMenuButton.setVisible(true);
	}
	
	public void onGameResume() {
		GameManager.getInstance().resume();
		resumeButton.setVisible(false);
		pauseButton.setVisible(true);
		nextButton.setVisible(true);
		sellButton.setDisable(false);
		upgradeButton.setDisable(false);
		toMenuButton.setVisible(false);
	}
	
	private void onGameLeft() {
		GameManager.getInstance().leaveGame();
		Main.setScene(Main.mainMenu);
		Main.mainMenu.resume();
		resumeButton.setVisible(false);
		pauseButton.setVisible(false);
		nextButton.setVisible(false);
		sellButton.setVisible(false);
		upgradeButton.setVisible(false);
		toMenuButton.setVisible(false);
	}
	
	
	
	
	public void setAllowNextWave(boolean allow) {
		nextButton.setDisable(!allow);
	}

}
