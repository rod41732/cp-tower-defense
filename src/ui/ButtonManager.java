package ui;


import constants.Images;
import constants.Numbers;
import controller.GameManager;
import controller.MonsterSpawner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import main.Main;
import model.Tower;

public class ButtonManager {
	
	private Button nextButton;
	private Button resumeButton; 
	private Button toMenuButton; 
	private Button pauseButton; // for pause menu
	private Button sellButton;
	private Button upgradeButton;
	
	
	public ButtonManager(Pane pane) {
		Font buttonFont = new Font("KenVector Future Regular", 20);
		
		sellButton = ButtonMaker.make(1400, 700,Images.buttonSell, Images.buttonSellPressed,
				buttonFont, "Sell Tower");		
		sellButton.setOnAction(e -> {
			GameManager.getInstance().sellTower();
		});

		nextButton = ButtonMaker.make(20, 80, Images.buttonNext, Images.buttonNextPressed,
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
		
		pauseButton = ButtonMaker.make(20, 20, Images.buttonPause, Images.buttonPausePressed,
				buttonFont, "Pause");
		pauseButton.setOnAction(e -> {
			onGamePause();
		});		
		
		pane.getChildren().addAll(pauseButton, resumeButton, toMenuButton,
				nextButton, upgradeButton, sellButton);
	}
	
	private void onGamePause() {
		GameManager.getInstance().pause();
		resumeButton.setVisible(true);
		pauseButton.setVisible(false);
		nextButton.setVisible(false);
		sellButton.setVisible(false);
		upgradeButton.setVisible(false);
	}
	
	private void onGameResume() {
		GameManager.getInstance().resume();
		resumeButton.setVisible(false);
		pauseButton.setVisible(true);
		nextButton.setVisible(true);
		sellButton.setVisible(true);
		upgradeButton.setVisible(true);
	}
	
	private void onGameLeft() {
		GameManager.getInstance().leaveGame();
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
