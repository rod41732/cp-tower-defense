package scenes;


import constants.Numbers;
import controller.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Main;

public class GameScene extends Scene {

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
		// duration is in millis (if set to 1/60) => it will not run LUL
		KeyFrame render = new KeyFrame(Duration.seconds(1./60), e ->  {
//			System.out.println("renderThread");
			// if playing
			//  updateState();
			if (GameManager.getInstance().isRunning()) {
				GameManager.getInstance().render(canvas.getGraphicsContext2D());				
			}
		});
		setOnMouseMoved(e -> {
			GameManager.getInstance().updateSelection(e.getX(), e.getY());
		});
		
		setOnMouseClicked(e -> {
			GameManager gi = GameManager.getInstance();
			gi.buildTower(gi.getSelX(), gi.getSelY());
		});
		
		setOnKeyPressed(e -> {
			GameManager.getInstance().addMoney(1000);
		});
		
		Timeline gameTick = new Timeline();
		gameTick.getKeyFrames().add(render);
		gameTick.setCycleCount(Timeline.INDEFINITE);
		gameTick.play();
		
		root.getChildren().addAll(canvas, back);
		
		
	}
}
