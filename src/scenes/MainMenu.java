package scenes;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Main;

public class MainMenu extends Scene {
	
	private Timeline menuTick;
	
	public MainMenu() {
		super(new Pane(), 1600, 900);
		Pane root = (Pane) getRoot();
		
		Button btn = new Button("Hello");
		
		KeyFrame tick = new KeyFrame(new Duration(1000), e-> {
			System.out.println("Hello From MainMenu");
		}); // run some stupid command every tick;
		
//		menuTick = new Timeline();
//		menuTick.getKeyFrames().add(tick);
//		menuTick.setCycleCount(Timeline.INDEFINITE);
//		menuTick.play();
		
		btn.setOnAction(e -> {
			Main.setScene(Main.settings);
			menuTick.pause(); // to make it playable, it should be resumable from other clas
		});
	
		root.getChildren().addAll(btn);
	}
	
	public void resume() {
		menuTick.play();
	}
}
