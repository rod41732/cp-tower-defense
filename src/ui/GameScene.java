package ui;


import constants.Numbers;
import controller.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Main;

public class GameScene extends Scene {
	private ButtonManager buttonManager;
	private Timeline gameTick;
	public GameScene() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane) getRoot();
		Canvas canvas = new Canvas(Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		root.getChildren().add(canvas);
		buttonManager = new ButtonManager(root);
		
		
		canvas.setOnMouseClicked(e -> {
			System.out.println("c1");
		});
//		canvas2
		gameTick = new Timeline();
		KeyFrame render = new KeyFrame(Duration.seconds(1./60), e -> {
			if (!GameManager.getInstance().isPaused())
				GameManager.getInstance().update();
			GameManager.getInstance().render(canvas.getGraphicsContext2D());					
		});
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);
//		gameTick.play();

		setOnMouseMoved(e -> {
			GameManager.getInstance().updateMousePos(e.getX(), e.getY());
		});
		
		setOnMouseClicked(e -> {
			PauseMenu.handleMouseClick(e);
			GameManager.getInstance().handleClick(e);								
		});
		
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.G) {
				GameManager.getInstance().requestNextWave();			
			} else if (e.getCode() == KeyCode.DIGIT1) {
				GameManager.getInstance().setSelectedTile(null);
				Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(0).setSelected(
						!Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(0).isSelected());
			} else if (e.getCode() == KeyCode.DIGIT2) {
				GameManager.getInstance().setSelectedTile(null);
				Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(1).setSelected(
						!Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(1).isSelected());
			} else if (e.getCode() == KeyCode.DIGIT3) {
				GameManager.getInstance().setSelectedTile(null);
				Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(2).setSelected(
						!Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(2).isSelected());
			} else if (e.getCode() == KeyCode.DIGIT4) {
				GameManager.getInstance().setSelectedTile(null);
				Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(3).setSelected(
						!Main.getGameScene().getButtonManager().getToggleGroup().getToggles().get(3).isSelected());
			} else if (e.getCode() == KeyCode.S) {
				GameManager.getInstance().sellTower();
			} else if (e.getCode() == KeyCode.D) {
				GameManager.getInstance().upgradeTower();
			}
			e.consume(); // prevent 'ding' sound 
		});
	}
	
	public void onLeave() {
		gameTick.pause();
	}
	
	public void onJoin() {
		gameTick.play();
		buttonManager.onGameResume();
	}

	public ButtonManager getButtonManager() {
		return buttonManager;
	}

}
