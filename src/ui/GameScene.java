package ui;


import constants.Numbers;
import controller.GameManager;
import controller.MonsterSpawner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Main;

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
		next.setLayoutX(1400);
		next.setLayoutY(700);
		next.setOnAction(e -> {
			GameManager.getInstance().requestNextWave();
		});
		
		Button upgrade = new Button("Upgrade");
		upgrade.setOnAction(e -> {
			GameManager.getInstance().upgradeTower();
		});
		upgrade.setLayoutX(1400);
		upgrade.setLayoutY(730);
		
		Button sell = new Button("Sell");
		sell.setOnAction(e -> {
			GameManager.getInstance().sellTower();
		});
		sell.setLayoutX(1400);
		sell.setLayoutY(760);
		
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
			GameManager.getInstance().handleClick(e);
			TowerMenu.handleClick(e);				
		});
		
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.G) {
				MonsterSpawner.getInstace().play();				
			} else if (e.getCode() == KeyCode.P) {
				GameManager.getInstance().setPaused(!GameManager.getInstance().isPaused());
			}
		});
		
		Timeline gameTick = new Timeline();
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);
		gameTick.play();
		
		root.getChildren().addAll(canvas, back, upgrade, next, sell);
		
		
	}
}
