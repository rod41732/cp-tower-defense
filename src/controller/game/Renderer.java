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
	private GraphicsContext otherGC, tileGC, overlayGC;
	private Timeline renderLoop;
	
	public Renderer(GameManager gm) {
		this.gm = gm;	
		
		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			if (nw.booleanValue()) {
				renderLoop.play();
			}
			else {
				renderLoop.pause();
			}
		});
		renderLoop = new Timeline(new KeyFrame(Duration.seconds(1./60), e -> {
			render();
		}));
		renderLoop.setCycleCount(Timeline.INDEFINITE);
		
	}
	
	public void setGC(GraphicsContext otherGC, GraphicsContext tileGC, GraphicsContext overlayGC) {
		this.otherGC = otherGC;
		this.tileGC = tileGC;
		this.overlayGC = overlayGC;
	}
	

	public boolean isPlaceable(int x, int y) {
		return  gm.towerManager.boundCheck(x, y) && gm.placedTiles[x][y].isPlaceable();
	}
	
	public boolean isWalkable(int x, int y) {
		return gm.towerManager.boundCheck(x, y) && gm.placedTiles[x][y].isWalkable();
	}
	

	
	public void render() {
		otherGC.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		tileGC.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		overlayGC.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		otherGC.setFill(Color.color(0, 0, 0, 0));
		otherGC.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
//		for (TileStack[] col: gm.placedTiles) 
//			for (TileStack ts: col) {
//				ts.render(otherGC, tileGC);
//			}
//		
//		for (int i=gm.monsters.size()-1; i>=0; i--) {
//			Monster m = gm.monsters.get(i);
//			m.render(otherGC);
//		}
//		for (Projectile p: gm.projectiles) p.render(otherGC);
//		for (Particle p: gm.particles) p.render(otherGC);
		gm.renderables.sort(new GameUtil.ZIndexComparator());
		for (Entity ent: gm.renderables) {
			ent.render(otherGC);
		}
		
		if (SuperManager.getInstance().getTowerChoiceProp().get() == -1 ) {
			if (SuperManager.getInstance().getShouldDisplayPathProp().get()) {
				if (gm.path != null) {
					tileGC.setFill(new Color(0, 0, 0, 0.5)); // just dim
					// need to copy
					cpp.pii pos = new cpp.pii(gm.startTilePos.first, gm.startTilePos.second);
					while (pos != null && !pos.equals(gm.endTilePos)) {
						tileGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
								Numbers.TILE_SIZE, Numbers.TILE_SIZE);
						pos = gm.path[pos.first][pos.second];
					}
					if (pos != null) {
						tileGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
								Numbers.TILE_SIZE, Numbers.TILE_SIZE);					
					}
				}							
			}
		}
		else {
			int choice = SuperManager.getInstance().getTowerChoiceProp().get();
			Tower floatingTower = GameManager.getInstance().createTower(choice, gm.tilePos.first, gm.tilePos.second);
			if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
				floatingTower.render(otherGC, true);							
			}
		}
		

		otherGC.setFont(Font.font("Consolas", 20));;
		GameUI.render(otherGC);
		PauseMenu.render(overlayGC);
		SnackBar.render(overlayGC);
	}
}
