package ui;

import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import controller.SuperManager;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import exceptions.PathBlockedException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import main.Main;
import model.Tile;
import model.TileStack;
import model.Tower;
import util.Algorithm;
import util.Algorithm2;
import util.cpp;
public class TowerMenu {
	
	// TODO: use buttons to check click (custom image buttons)
	private static double LEFT = 1344;
	private static double TOP = 0;
	private static int COL = 3;
	private static int ROW = 3;
	private static Image[] images = {Images.bombTower, Images.normalTower, Images.fireTower, Images.iceTower};
	private static RichTextBox towerInfoPanel = new RichTextBox(new ArrayList<>(), new ArrayList<>(), LEFT, 320);
	private static RichTextBox upgradeInfo = new RichTextBox(new ArrayList<>(), new ArrayList<>(), LEFT, 515);
	
	private static cpp.pii lastPos = new cpp.pii(-1, -1);
	private static cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];
	private static boolean isError = false;
	public static void render(GraphicsContext gc) {
		
		gc.setFill(Color.MAGENTA);
		Tile t = GameManager.getInstance().getSelectedTile();
		Tile t2 = GameManager.getInstance().createTower(GameManager.getInstance().getTowerChoice(), 999, 999);
				
		if (t != null && t instanceof Tower)
			renderTowerInfo(gc, t, true);
		else if (t2 != null)
			renderTowerInfo(gc, t2, false);
		
		
		GameManager gi = GameManager.getInstance();
		cpp.pii tilePos = gi.getSelectedPosition();
		int choice = SuperManager.getInstance().getTowerChoiceProp().get();
		if (choice != -1) {	
			try {
				Tower floatingTower = gi.createTower(choice, tilePos.first, tilePos.second);
				if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
					floatingTower.render(gc, true);							
				}
				cpp.pii start = gi.getStartTilePos(), end = gi.getEndTilePos();
				
				if (!tilePos.equals(lastPos)) {
					lastPos.first = tilePos.first;
					lastPos.second = tilePos.second;
					if (gi.isPlaceable(tilePos.first, tilePos.second)) {
						path = Algorithm2.BFS(end.first, end.second, start.first, start.second, tilePos);
						isError = false;
					}
					else {
						path = Algorithm2.BFS(end.first, end.second, start.first, start.second, new cpp.pii(-1, -1));	
						isError = true;
					}
				}
				
				if (path != null) {
					gc.setFill(new Color(0, 0, 0, 0.4)); // just dim
					// need to copy
					cpp.pii pos = new cpp.pii(start.first, start.second);
					while (pos != null && !pos.equals(end)) {
						gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
								Numbers.TILE_SIZE, Numbers.TILE_SIZE);
						pos = path[pos.first][pos.second];
					}
					if (pos != null) // blocked path
					gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
							Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				}
				if (isError) {
					gc.setFill(Color.color(1, 0, 0, 0.7));
					gc.fillRect(tilePos.first*Numbers.TILE_SIZE, tilePos.second*Numbers.TILE_SIZE,
							Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				}
			}
			catch (PathBlockedException e) {
				isError = true;
				gc.setFill(Color.color(1, 0, 0, 0.7));
				gc.fillRect(tilePos.first*Numbers.TILE_SIZE, tilePos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);
			}
		}

	}

	
	public static void renderTowerInfo(GraphicsContext gc, Tile t, boolean showUpgrade) {
		double top = 400;
		double left = LEFT;
		
		ArrayList<Image> imgs = new ArrayList<>();
		imgs.add(Images.bombIcon);
		imgs.add(Images.attackIcon);
		imgs.add(Images.targetIcon);
		imgs.add(Images.cooldownIcon);
		ArrayList<String> texts = new ArrayList<>();
		Tower tw = (Tower)t;
		texts.add(""+tw.toString());
		texts.add(""+tw.getAttack() + " DPS");
		texts.add(""+tw.getRange() + " Tile");
		texts.add(""+tw.getAttackCooldown() + " ms");
		
		towerInfoPanel.setImages(imgs);
		towerInfoPanel.setTexts(texts);
		towerInfoPanel.calculateLayout();
		
		towerInfoPanel.render(gc);
		
 		imgs.clear();
		imgs.add(Images.bombIcon);
		imgs.add(Images.attackIcon);
		imgs.add(Images.targetIcon);
		imgs.add(Images.cooldownIcon);
		texts.clear();
		texts.add(""+tw.toString());
		try {
			if (!showUpgrade) throw new FullyUpgradedException(); // TODO: hacky
			if (!tw.canUpgrade()) throw new FullyUpgradedException();
			texts.add(""+tw.getUpgradedAttack() + " DPS");
			texts.add(""+tw.getUpgradedRange() + " Tile");
			texts.add(""+tw.getUpgradedAttackCooldown() + " ms");			
			upgradeInfo.setImages(imgs);
			upgradeInfo.setTexts(texts);
			upgradeInfo.calculateLayout();
			upgradeInfo.render(gc);
		}
		catch (FullyUpgradedException e) {
			gc.setFill(Color.BLACK);
			gc.fillRect(LEFT, 515, 200, 200);
		}
		
		
	}
}
