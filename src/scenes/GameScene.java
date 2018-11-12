package scenes;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameScene extends Scene {

	public GameScene() {
		super(new Pane(), 1600, 900);
		Pane root = (Pane) getRoot();
		Canvas canvas = new Canvas(1600, 900);
		
		// duration is in millis (if set to 1/60) => it will not run LUL
		KeyFrame render = new KeyFrame(new Duration(100), e ->  {
			System.out.println("renderThread");
			//			GameManager.getInstance().render(canvas.getGraphicsContext2D());
		});
		
		Timeline renderTick = new Timeline();
		renderTick.getKeyFrames().add(render);
		renderTick.setCycleCount(Timeline.INDEFINITE);
		renderTick.play();
		
		
	}
}
