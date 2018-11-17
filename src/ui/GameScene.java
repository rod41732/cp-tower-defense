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
	private Button nextButton;
	private Button resumeButton, toMenuButton, pauseButton; // for pause menu
	private Button sellButton, upgradeButton;
	
	public GameScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane) getRoot();
		Canvas canvas = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
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
		
		upgradeButton = ButtonMaker.make(1400, 760, Images.buttonUpgrade, Images.buttonUpgradePressed, buttonFont, "Upgrade");
		upgradeButton.setOnAction(e -> {
			GameManager.getInstance().upgradeTower();
		});		
		
		toMenuButton = ButtonMaker.make(700, 480, Images.buttonNext, Images.buttonNextPressed, buttonFont, "Main menu");
		toMenuButton.setVisible(false);
		toMenuButton.setOnAction(e -> {
			GameManager.getInstance().setPaused(true);
			PauseMenu.hide();
			Main.setScene(Main.mainMenu);
			Main.mainMenu.resume();
		});
		
		resumeButton = ButtonMaker.make(700, 400, Images.buttonUpgrade, Images.buttonUpgradePressed, buttonFont, "Resume");
		resumeButton.setVisible(false);
		resumeButton.setOnAction(e -> {
			GameManager.getInstance().setPaused(false);
			resumeButton.setVisible(false);
			PauseMenu.hide();
			
			pauseButton.setVisible(true);
			nextButton.setVisible(true);
			sellButton.setVisible(true);
			upgradeButton.setVisible(true);
		});
		
		pauseButton = ButtonMaker.make(20, 20, Images.buttonPause, Images.buttonPausePressed,
				buttonFont, "Pause");
		pauseButton.setOnAction(e -> {
			PauseMenu.show();
			resumeButton.setVisible(true);
			GameManager.getInstance().setPaused(true);
			
			pauseButton.setVisible(false);
			nextButton.setVisible(false);
			sellButton.setVisible(false);
			upgradeButton.setVisible(false);
		});
		
		root.getChildren().addAll(canvas, sellButton, nextButton,
				toMenuButton, pauseButton, resumeButton, upgradeButton);

		Timeline gameTick = new Timeline();
		KeyFrame render = new KeyFrame(Duration.seconds(1./60), e ->  {
			if (GameManager.getInstance().isRunning()) {
				if (!GameManager.getInstance().isPaused())
					GameManager.getInstance().update();
				GameManager.getInstance().render(canvas.getGraphicsContext2D());				
			}
		});
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);
		gameTick.play();

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
			e.consume(); // prevent 'ding' sound 
		});
		
		
	}


	public Button getNextButton() {
		return nextButton;
	}

	public Button getPauseButton() {
		return pauseButton;
	}

	public Button getSellButton() {
		return sellButton;
	}

	public Button getUpgradeButton() {
		return upgradeButton;
	}

	public Button getResumeButton() {
		return resumeButton;
	}

	public Button getToMenuButton() {
		return toMenuButton;
	}
}
