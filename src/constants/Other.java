package constants;

import javafx.scene.text.Font;

public class Other {
	public static int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	
	public static Font normalButtonFont;
	public static Font thinButtonFont;

	static {
		normalButtonFont = Font.loadFont(ClassLoader.getSystemResource("font/kenvector_future.ttf").toString(), 20);
		thinButtonFont = Font.loadFont(ClassLoader.getSystemResource("font/kenvector_future.ttf").toString(), 20);
	}
}
