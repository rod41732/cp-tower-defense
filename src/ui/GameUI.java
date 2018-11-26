package ui;

import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import constants.Other;
import controller.SuperManager;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import exceptions.PathBlockedException;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Tile;
import model.Tower;
import util.Algorithm2;
import util.cpp;
public class GameUI {
	
	// TODO: use buttons to check click (custom image buttons)
	private static double LEFT = 1344;
	private static RichTextBox towerInfoPanel = new RichTextBox(new ArrayList<>(), LEFT, 320);
	private static RichTextBox upgradeInfo = new RichTextBox(new ArrayList<>(), LEFT, 515);
	
	private static cpp.pii lastPos = new cpp.pii(-1, -1);
	private static cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];
	private static boolean isError = false;
	public static void render(GraphicsContext otherGC, GraphicsContext overlayGC) {
		
		Tile t = GameManager.getInstance().getSelectedTile();
		Tile t2 = GameManager.getInstance().createTower(GameManager.getInstance().getTowerChoice(), 999, 999);
				
		
		
		if (t != null && t instanceof Tower)
			renderTowerInfo(overlayGC, t, true);
		else if (t2 != null)
			renderTowerInfo(overlayGC, t2, false);
		
		overlayGC.setTextBaseline(VPos.BOTTOM);
		overlayGC.setFont(Other.normalButtonFont);
		overlayGC.drawImage(Images.attackIcon, 4, 4);
		overlayGC.fillText("Level 1", 50, 40);
		overlayGC.drawImage(Images.coinIcon, 224, 4);
		overlayGC.fillText("$ " + GameManager.getInstance().getMoney(), 260, 40);
		overlayGC.drawImage(Images.liveIcon, 444, 4);
		overlayGC.fillText("" + GameManager.getInstance().getLives() , 480, 40);
		
		
		
		GameManager gm = GameManager.getInstance();
		cpp.pii tilePos = gm.getSelectedPosition();
		int choice = SuperManager.getInstance().getTowerChoiceProp().get();
		if (choice != -1) {	
			try {
				Tower floatingTower = gm.createTower(choice, tilePos.first, tilePos.second);
				if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
					floatingTower.render(otherGC, true);							
				}
				cpp.pii start = gm.getStartTilePos(), end = gm.getEndTilePos();
				
				if (!tilePos.equals(lastPos)) {
					lastPos.first = tilePos.first;
					lastPos.second = tilePos.second;
					if (gm.isPlaceable(tilePos.first, tilePos.second)) {
						path = Algorithm2.BFS(end.first, end.second, start.first, start.second, tilePos);
						isError = false;
					}
					else {
						path = Algorithm2.BFS(end.first, end.second, start.first, start.second, new cpp.pii(-1, -1));	
						isError = true;
					}
				}
				
				if (path != null) {
					otherGC.setFill(new Color(0, 0, 0, 0.4)); // just dim
					// need to copy
					cpp.pii pos = new cpp.pii(start.first, start.second);
					while (pos != null && !pos.equals(end)) {
						otherGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
								Numbers.TILE_SIZE, Numbers.TILE_SIZE);
						pos = path[pos.first][pos.second];
					}
					if (pos != null) // blocked path
					otherGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
							Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				}
				if (isError) {
					otherGC.setFill(Color.color(1, 0, 0, 0.7));
					otherGC.fillRect(tilePos.first*Numbers.TILE_SIZE, tilePos.second*Numbers.TILE_SIZE,
							Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				}
			}
			catch (PathBlockedException e) {
				isError = true;
				otherGC.setFill(Color.color(1, 0, 0, 0.7));
				otherGC.fillRect(tilePos.first*Numbers.TILE_SIZE, tilePos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);
			}
		}

	}

	
	public static void renderTowerInfo(GraphicsContext gc, Tile t, boolean showUpgrade) {


		gc.drawImage(Images.towerInfoPanel, LEFT, 320);
		ArrayList<String> texts = new ArrayList<>();
		Tower tw = (Tower)t;
		texts.add(""+tw.toString());
		texts.add(""+tw.getAttack() + " DPS");
		texts.add(""+tw.getRange() + " Tile");
		texts.add(""+tw.getAttackCooldown() + " ms");
		texts.add(""+tw.getDescription());
		towerInfoPanel.setTexts(texts);
		
		towerInfoPanel.render(gc);
		
 		texts.clear();
		texts.add(""+tw.toString());

		gc.drawImage(Images.towerInfoPanel, LEFT, 515 );
		try {
			if (!showUpgrade) throw new FullyUpgradedException(); // TODO: hacky
			if (!tw.canUpgrade()) throw new FullyUpgradedException();
			texts.add(""+tw.getUpgradedAttack() + " DPS");
			texts.add(""+tw.getUpgradedRange() + " Tile");
			texts.add(""+tw.getUpgradedAttackCooldown() + " ms");
			texts.add(""+tw.getUpgradedDescription() + " ms");	
			upgradeInfo.setTexts(texts);
			upgradeInfo.render(gc);
		}
		catch (FullyUpgradedException e) {
			
		}
	}
	
	
}
