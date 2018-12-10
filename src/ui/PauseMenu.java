package ui;

import constants.Images;
import constants.Numbers;
import constants.Other;
import controller.SuperManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class PauseMenu {
	private static String[] TEXTS = {"Paused", "Game Over", "You Win"};
	
	private static boolean isShown = false;
	private static GraphicsContext gc;
	
	static {
		SuperManager.getInstance().getIsGamePausedProp().addListener((obs, old, nw) -> {
			boolean pause = nw.booleanValue();
			isShown = pause;
		});
	}
	public static void render() {
		gc.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_WIDTH);
		if (isShown) {
			gc.setFill(Color.color(0, 0, 0, 0.4));
			gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_WIDTH);
			gc.drawImage(Images.pauseMenuPanel, 650, 300);
			gc.setFill(Color.color(0.55, 0.27, 0.10, 0.8));
			gc.setFont(Other.loadFontWithSize(36));
			gc.setTextAlign(TextAlignment.CENTER);
			
			gc.fillText(TEXTS[SuperManager.getInstance().getGameStateProp().get()], 800, 360);
			gc.setTextAlign(TextAlignment.LEFT);
		}
	}
	
	public static void setTargetGC(GraphicsContext gc) {
		PauseMenu.gc = gc;
	}
	
	public static void handleMouseClick(MouseEvent e) {
		// blocks input
		if (isShown)
			e.consume();
	}

	
}
