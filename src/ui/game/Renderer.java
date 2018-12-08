package ui.game;


import java.util.ArrayList;

import constants.Numbers;
import controller.SuperManager;
import controller.game.GameManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Entity;
import sharedobject.SharedObject;
import ui.PauseMenu;
import ui.SnackBar;

public class Renderer {
	private static Renderer instance = new Renderer();

	private GameManager gm;
	private GraphicsContext gc;
	private Timeline renderLoop;
	private int renderTick = 0;
	
	public Renderer() {
		System.out.println("[debug] initialized main renderer");
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			if (nw.booleanValue()) {
				System.out.println("render resume");
				renderLoop.play();
			}
			else {
				System.out.println("render pause");
				renderLoop.pause();
			}
		});
		renderLoop = new Timeline(new KeyFrame(Duration.seconds(1./60), e -> {
			render();
		}));
		renderLoop.setCycleCount(Timeline.INDEFINITE);
		
	}
	
	public void setGC(GraphicsContext gc) {
		this.gc = gc;
	}
	

	public boolean isPlaceable(int x, int y) {
		return  gm.boundCheck(x, y) && gm.getPlacedTiles()[x][y].isPlaceable();
	}
	
	public boolean isWalkable(int x, int y) {
		return gm.boundCheck(x, y) && gm.getPlacedTiles()[x][y].isWalkable();
	}
	

	
	public void render() {
		renderTick++;
		gc.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		
		ArrayList<Entity> arr = SharedObject.getInstance().getRenderables(); 
		for (int i=0; i<arr.size(); i++) {
			arr.get(i).render(gc);
		}
		gc.setFont(Font.font("Consolas", 20));;
		GameUI.getInstance().render(gc);
		PauseMenu.render();
		SnackBar.render(gc);
	}

	public static Renderer getInstance() {
		return instance;
	}
	
	
	public int getRenderTick() {
		return renderTick;
	}
	
}
