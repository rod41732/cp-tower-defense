package ui;

import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;

public class PauseMenu {
	
	private static boolean isShown = false;
	
	public static void render(GraphicsContext gc) {
		if (isShown) {
			gc.setFill(Color.color(0, 0, 0, 0.4));
			gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_WIDTH);
			gc.drawImage(Images.pauseMenuPanel, 650, 300);
			gc.setFill(Color.color(0.55, 0.27, 0.10, 0.8));
			gc.setFont(Font.font("KenVector Future Regular", 44));
			gc.fillText("Paused", 700, 360);
		}
	}
	
	public static void show() {
		isShown = true;
	}
	
	public static void handleMouseClick(MouseEvent e) {
		// blocks input
		if (isShown)
			e.consume();
	}
	
	public static void hide() {
		isShown = false;
	}
	
	public static void fadeIn() {
	}
	
	public static void fadeOut() {
		
	}
	
	
}
