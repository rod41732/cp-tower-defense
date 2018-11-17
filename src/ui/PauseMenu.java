package ui;

import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.Main;

public class PauseMenu {
	
	private static boolean isShown = false;
	
	public static void render(GraphicsContext gc) {
		if (isShown) {
			gc.setFill(Color.color(0, 0, 0, 0.4));
			gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_WIDTH);			
		}
	}
	
	public static void show() {
		isShown = true;
		Main.getGameScene().getResumeButton().setVisible(true);
		Main.getGameScene().getToMenuButton().setVisible(true);
	}
	
	public static void handleMouseClick(MouseEvent e) {
		// blocks input
		if (isShown)
			e.consume();
	}
	
	public static void hide() {
		isShown = false;
		Main.getGameScene().getResumeButton().setVisible(false);
		Main.getGameScene().getToMenuButton().setVisible(false);
	}
	
	public static void fadeIn() {
	}
	
	public static void fadeOut() {
		
	}
	
	
}
