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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import main.Main;
import model.Tower;

public class GameScene extends Scene {
	public Button next;
	private Button resumeButton, toMenuButton; // for pause menu
	
	
	public GameScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane) getRoot();
		Canvas canvas = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Font buttonFont = new Font("KenVector Future Regular", 20);
		
		Button sell = new Button("sell");
		sell.setFont(new Font("KenVector Future Regular", 20));
		sell.setStyle("-fx-background-image: url('ui/button/button_sell.png');");
		sell.setPrefWidth(190);
		sell.setPrefHeight(49);
		sell.setPadding(Insets.EMPTY);
		
		sell.setOnMousePressed(e -> {
			sell.setStyle("-fx-background-image: url('ui/button/button_sell_hover.png');");
			sell.setPrefHeight(45);
		});
		sell.setOnMouseReleased(e -> {
			sell.setStyle("-fx-background-image: url('ui/button/button_sell.png');");
			sell.setPrefHeight(49);
		});
		
		sell.setOnAction(e -> {
			GameManager.getInstance().sellTower();
		});
		
		sell.setLayoutX(1200);
		sell.setLayoutY(700);
//		
		
		next = ButtonMaker.make(Images.buttonNext, Images.buttonNextPressed,
				buttonFont, "Next Wave");
		next.setOnAction(e -> {
			GameManager.getInstance().requestNextWave();
		});
		next.setLayoutX(1200);
		next.setLayoutY(820);
		
//		
		Button upgrade = ButtonMaker.make(Images.buttonUpgrade, Images.buttonUpgradePressed,
				buttonFont, "Upgrade");
		upgrade.setOnAction(e -> {
			GameManager.getInstance().upgradeTower();
		});
		
		upgrade.setLayoutX(1200);
		upgrade.setLayoutY(760);
		
		
		toMenuButton = ButtonMaker.make(Images.buttonNext, Images.buttonNextPressed, buttonFont, "Main menu");
		toMenuButton.setOnAction(e -> {
			GameManager.getInstance().setPaused(true);
			PauseMenu.hide();
			Main.setScene(Main.mainMenu);
			Main.mainMenu.resume();
		});
		toMenuButton.setVisible(false);
		toMenuButton.setLayoutX(800);
		toMenuButton.setLayoutY(500);
		
		resumeButton = ButtonMaker.make(Images.buttonUpgrade, Images.buttonUpgradePressed, buttonFont, "Resume");
		resumeButton.setOnAction(e -> {
			GameManager.getInstance().setPaused(false);
			PauseMenu.hide();
		});
		resumeButton.setVisible(false);
		resumeButton.setLayoutX(800);
		resumeButton.setLayoutY(400);
		
//		Button sell = new Button("Sell");
//		sell.setOnAction(e -> {
//			GameManager.getInstance().sellTower();
//		});
//		sell.setLayoutX(1400);
//		sell.setLayoutY(760);
		Button pauseButton = ButtonMaker.make(Images.buttonPause, Images.buttonPausePressed,
				buttonFont, "Pause");
		pauseButton.setOnAction(e -> {
			PauseMenu.show();
			GameManager.getInstance().setPaused(true);
		});
		pauseButton.setLayoutX(1200);
		pauseButton.setLayoutY(500);
		
		
		KeyFrame render = new KeyFrame(Duration.seconds(1./60), e ->  {
			if (GameManager.getInstance().isRunning()) {
				if (!GameManager.getInstance().isPaused())
					GameManager.getInstance().update();
				GameManager.getInstance().render(canvas.getGraphicsContext2D());				
			}
		});
		
		setOnMouseMoved(e -> {
			GameManager.getInstance().updateMousePos(e.getX(), e.getY());
		});
		
		setOnMouseClicked(e -> {
			PauseMenu.handleMouseClick(e);
			TowerMenu.handleClick(e);
			GameManager.getInstance().handleClick(e);								
		});
		
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.G) {
				GameManager.getInstance().requestNextWave();			
			} else if (e.getCode() == KeyCode.P) {
				GameManager.getInstance().setPaused(!GameManager.getInstance().isPaused());
			} else if (e.getCode() == KeyCode.DIGIT1) {
				GameManager.getInstance().setTowerChoice(0);
			} else if (e.getCode() == KeyCode.DIGIT2) {
				GameManager.getInstance().setTowerChoice(1);
			} else if (e.getCode() == KeyCode.DIGIT3) {
				GameManager.getInstance().setTowerChoice(2);
			} else if (e.getCode() == KeyCode.DIGIT4) {
				GameManager.getInstance().setTowerChoice(3);
			} else if (e.getCode() == KeyCode.S) {
				GameManager.getInstance().sellTower();
			} else if (e.getCode() == KeyCode.D) {
				GameManager.getInstance().upgradeTower();
			}
			e.consume();
		});
		
		
		Timeline gameTick = new Timeline();
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);
		gameTick.play();
		
//		root.getChildren().addAll(canvas, back, upgrade, next, sell);
		root.getChildren().addAll(canvas, sell, next, toMenuButton, pauseButton, resumeButton, upgrade);
		
		
	}


	public Button getResumeButton() {
		return resumeButton;
	}


	public Button getToMenuButton() {
		return toMenuButton;
	}
}
