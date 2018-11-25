package controller.game;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import controller.SuperManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tile;
import model.TileStack;
import model.Tower;
import ui.PauseMenu;
import ui.RichTextBox;
import ui.SnackBar;
import ui.TowerMenu;
import util.cpp;

public class Renderer {


	private GameManager game;
	private GraphicsContext otherGC, tileGC, overlayGC;
	private RichTextBox infoBox;
	private Thread renderLoop;
	private boolean shouldRender;
	
	public Renderer(GameManager game) {
		this.game = game;
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
			boolean inGame = nw.booleanValue();
			shouldRender = inGame;
			System.err.printf("[Renderer] state changed -> %s", nw);
		});
		
		
		renderLoop = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000/60);
					render();
				}
				catch (InterruptedException e) {
					System.out.println("rendered interrupted");
				}
			}
		});
	}
	
	public void setGC(GraphicsContext otherGC, GraphicsContext tileGC, GraphicsContext overlayGC) {
		this.otherGC = otherGC;
		this.tileGC = tileGC;
		this.overlayGC = overlayGC;
	}
	
	public boolean boundCheck(int x, int y) {
		return 0 <= x && x < Numbers.COLUMNS && 0 <= y && y < Numbers.ROWS;
	}
	public boolean isPlaceable(int x, int y) {
		return  boundCheck(x, y) && game.placedTiles[x][y].isPlaceable();
	}
	
	public boolean isWalkable(int x, int y) {
		return boundCheck(x, y) && game.placedTiles[x][y].isWalkable();
	}
	

	
	public void render() {
		otherGC.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		otherGC.setGlobalAlpha(1);
		for (TileStack[] col: game.placedTiles)
			for (TileStack ts: col)
				ts.render(otherGC, tileGC);
		for (Monster m: game.monsters) m.render(otherGC);
		for (Projectile p: game.projectiles) p.render(otherGC);
		for (Particle p: game.particles) p.render(otherGC);
		
		if (SuperManager.getInstance().getTowerChoiceProp().get() == -1) {
			if (game.path != null) {
				tileGC.setFill(new Color(0, 0, 0, 0.5)); // just dim
				// need to copy
				cpp.pii pos = new cpp.pii(game.startTilePos.first, game.startTilePos.second);
				while (pos != null && !pos.equals(game.endTilePos)) {
					tileGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
							Numbers.TILE_SIZE, Numbers.TILE_SIZE);
					pos = game.path[pos.first][pos.second];
				}
				if (pos != null) {
					tileGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
							Numbers.TILE_SIZE, Numbers.TILE_SIZE);					
				}
			}			
		}
		else {
			int choice = SuperManager.getInstance().getTowerChoiceProp().get();
			Tower floatingTower = GameManager.getInstance().createTower(choice, game.tilePos.first, game.tilePos.second);
			if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
				floatingTower.render(otherGC, true);							
			}
		}
		
		
		
		otherGC.setFill(Color.MAGENTA);
		otherGC.setStroke(Color.BLACK);
		infoBox.getTexts().set(0, "level" + (int)(Math.random()*4));
		infoBox.getTexts().set(1, "$ " + game.money);
		infoBox.getTexts().set(2, game.lives + " lives\n tile" + game.mousePos.first*Numbers.TILE_SIZE + ". " + game.mousePos.second*Numbers.TILE_SIZE);
		infoBox.render(otherGC);
		
		otherGC.setFont(Font.font("Consolas", 20));;
		TowerMenu.render(otherGC);
		PauseMenu.render(overlayGC);
		SnackBar.render(overlayGC);
	}
}
