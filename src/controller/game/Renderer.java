package controller.game;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.TileStack;
import model.Tower;
import ui.PauseMenu;
import ui.RichTextBox;
import ui.SnackBar;
import ui.TowerMenu;
import util.cpp;

public class Renderer {


	private GameManager gm;
	private GraphicsContext otherGC, tileGC, overlayGC;
	private RichTextBox infoBox;
	private Timeline renderLoop;
	
	public Renderer(GameManager gm) {
		this.gm = gm;
		ArrayList<Image> imgs= new ArrayList<>();
		ArrayList<String> texts = new ArrayList<>();
		imgs.add(Images.cooldownIcon);
		imgs.add(Images.coinIcon);
		imgs.add(Images.liveIcon);
		texts.add("Level 99999");
		texts.add("$" + 999999);
		texts.add("lives" + 999999);
		infoBox = new RichTextBox(imgs, texts, 20, 20);
		
		
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
//		System.out.println("rendering");
		otherGC.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		otherGC.setGlobalAlpha(1);
		for (TileStack[] col: gm.placedTiles) 
			for (TileStack ts: col) {
//				System.out.println("r1");
				ts.render(otherGC, tileGC);
			}
		
		for (Monster m: gm.monsters) m.render(otherGC);
		for (Projectile p: gm.projectiles) p.render(otherGC);
		for (Particle p: gm.particles) p.render(otherGC);
		
		if (SuperManager.getInstance().getTowerChoiceProp().get() == -1) {
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
		else {
			int choice = SuperManager.getInstance().getTowerChoiceProp().get();
			Tower floatingTower = GameManager.getInstance().towerManager.createTower(choice, gm.tilePos.first, gm.tilePos.second);
			if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
				floatingTower.render(otherGC, true);							
			}
		}
		
		
		
		otherGC.setFill(Color.MAGENTA);
		otherGC.setStroke(Color.BLACK);
		infoBox.getTexts().set(0, "level" + (int)(Math.random()*4));
		infoBox.getTexts().set(1, "$ " + gm.money);
		infoBox.getTexts().set(2, gm.lives + " lives\n tile" + gm.mousePos.first*Numbers.TILE_SIZE + ". " + gm.mousePos.second*Numbers.TILE_SIZE);
		infoBox.render(otherGC);
		
		otherGC.setFont(Font.font("Consolas", 20));;
		TowerMenu.render(otherGC);
		PauseMenu.render(overlayGC);
		SnackBar.render(overlayGC);
	}
}
