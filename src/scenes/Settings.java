package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import main.Main;

public class Settings extends Scene {

	public Settings() {
		super(new Pane(), 1600, 900);
		Pane root = (Pane) getRoot();
		Button back = new Button("Back to main menu");
		back.setOnAction(e -> {
			Main.setScene(Main.mainMenu);
		});
		
		root.getChildren().addAll(back);
		
	}
}
