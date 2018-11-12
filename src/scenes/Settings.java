package scenes;

import constants.Numbers;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import main.Main;

public class Settings extends Scene {

	public Settings() {
		super(new Pane(), Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		Pane root = (Pane) getRoot();
		Button back = new Button("Back to main menu");
		back.setOnAction(e -> {
			Main.setScene(Main.mainMenu);
			Main.mainMenu.resume();
		});
		
		root.getChildren().addAll(back);
		
	}
}
