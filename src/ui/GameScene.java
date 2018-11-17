package ui;


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
	public GameScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane) getRoot();
		Canvas canvas = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Button back = new Button("Back to menu");
		back.setOnAction(e -> {
			GameManager.getInstance().setRunning(false);
			Main.setScene(Main.mainMenu);
			Main.mainMenu.resume();
		});
		
		next = new Button("Next Wave");
		next.setFont(new Font("KenVector Future Regular", 20));
		next.setLayoutX(1400);
		next.setLayoutY(700);
		next.setOnAction(e -> {
			GameManager.getInstance().requestNextWave();
		});
		
		Button sell = new Button("upgrade");
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
			GameManager.getInstance().upgradeTower();
		});
		
		sell.setLayoutX(1200);
		sell.setLayoutY(700);
//		
		Button upgrade = new Button("upgrade");
		upgrade.setFont(new Font("KenVector Future Regular", 20));
		upgrade.setStyle("-fx-background-image: url('ui/button/button_sell.png');");
		upgrade.setPrefWidth(190);
		upgrade.setPrefHeight(49);
		upgrade.setPadding(Insets.EMPTY);
		
		upgrade.setOnMousePressed(e -> {
			upgrade.setStyle("-fx-background-image: url('ui/button/button_sell_hover.png');");
			upgrade.setPrefHeight(45);
		});
		upgrade.setOnMouseReleased(e -> {
			upgrade.setStyle("-fx-background-image: url('ui/button/button_sell.png');");
			upgrade.setPrefHeight(49);
		});
		
		upgrade.setOnAction(e -> {
			GameManager.getInstance().upgradeTower();
		});
		
		upgrade.setLayoutX(1200);
		upgrade.setLayoutY(760);
//		
//		Button sell = new Button("Sell");
//		sell.setOnAction(e -> {
//			GameManager.getInstance().sellTower();
//		});
//		sell.setLayoutX(1400);
//		sell.setLayoutY(760);
		
		
		
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
			
		});
		
		
		Timeline gameTick = new Timeline();
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);
		gameTick.play();
		
//		root.getChildren().addAll(canvas, back, upgrade, next, sell);
		root.getChildren().addAll(canvas, back, next, sell, upgrade);
		
		
	}
}
