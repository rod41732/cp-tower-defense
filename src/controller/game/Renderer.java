package controller.game;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Entity;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.TileStack;
import model.Tower;
import ui.PauseMenu;
import ui.SnackBar;
import ui.game.GameUI;
import ui.game.TowerInfoPanel;
import util.GameUtil;
import util.cpp;

public class Renderer {


	private GameManager gm;
	private GraphicsContext gc;
	private Timeline renderLoop;
	
	public Renderer(GameManager gm) {
		this.gm = gm;	
		
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
			render(this.gc);
		}));
		renderLoop.setCycleCount(Timeline.INDEFINITE);
		
	}
	
	public void setGC(GraphicsContext gc) {
		this.gc = gc;
	}
	

	public boolean isPlaceable(int x, int y) {
		return  gm.towerManager.boundCheck(x, y) && gm.placedTiles[x][y].isPlaceable();
	}
	
	public boolean isWalkable(int x, int y) {
		return gm.towerManager.boundCheck(x, y) && gm.placedTiles[x][y].isWalkable();
	}
	

	
	public void render(GraphicsContext gc) {
		gc.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gc.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gc.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gc.setFill(Color.color(0, 0, 0, 0));
		gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
//		for (TileStack[] col: gm.placedTiles) 
//			for (TileStack ts: col) {
//				ts.render(gc, gc);
//			}
//		
//		for (int i=gm.monsters.size()-1; i>=0; i--) {
//			Monster m = gm.monsters.get(i);
//			m.render(gc);
//		}
//		for (Projectile p: gm.projectiles) p.render(gc);
//		for (Particle p: gm.particles) p.render(gc);
		gm.renderables.sort(new GameUtil.ZIndexComparator());
		for (Entity ent: gm.renderables) {
			ent.render(gc);
		}
		
		if (SuperManager.getInstance().getTowerChoiceProp().get() == -1 ) {
			if (SuperManager.getInstance().getShouldDisplayPathProp().get()) {
				if (gm.path != null) {
					gc.setFill(new Color(0, 0, 0, 0.5)); // just dim
					// need to copy
					cpp.pii pos = new cpp.pii(gm.startTilePos.first, gm.startTilePos.second);
					while (pos != null && !pos.equals(gm.endTilePos)) {
						gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
								Numbers.TILE_SIZE, Numbers.TILE_SIZE);
						pos = gm.path[pos.first][pos.second];
					}
					if (pos != null) {
						gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
								Numbers.TILE_SIZE, Numbers.TILE_SIZE);					
					}
				}							
			}
		}
		else {
			int choice = SuperManager.getInstance().getTowerChoiceProp().get();
			Tower floatingTower = GameManager.getInstance().createTower(choice, gm.tilePos.first, gm.tilePos.second);
			if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
				floatingTower.render(gc, true);							
			}
		}
		

		gc.setFont(Font.font("Consolas", 20));;
		GameUI.render(gc);
		PauseMenu.render();
		SnackBar.render(gc);
	}
}
