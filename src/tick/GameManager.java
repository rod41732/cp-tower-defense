package tick;


import javafx.scene.canvas.GraphicsContext;

public class GameManager {
	
	private static GameManager instance = new GameManager();
	public GameManager() {
		// init some
	}
	
	public void render(GraphicsContext gc) {
		gc.fill();
		System.out.println("Rendering");
	}

	public static GameManager getInstance() {
		return instance;
	}

	public static void setInstace(GameManager instace) {
		GameManager.instance = instace;
	}
	
	
	
}
