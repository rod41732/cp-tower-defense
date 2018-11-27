package ui.game;

import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import constants.Other;
import controller.SuperManager;
import controller.game.GameManager;
import controller.game.MonsterSpawner;
import exceptions.FullyUpgradedException;
import exceptions.PathBlockedException;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Tile;
import model.Tower;
import ui.component.IconText;
import util.Algorithm2;
import util.cpp;
public class GameUI {
	
	// TODO: use buttons to check click (custom image buttons)
	private static double LEFT = 1344;
	private static TowerInfoPanel towerInfoPanel = new TowerInfoPanel();
	private static TowerInfoPanel upgradeInfoPanel = new TowerInfoPanel();
	
	private static IconText levelPanel, moneyPanel, livePanel, debug;
	
	
	
	private static cpp.pii lastPos = new cpp.pii(-1, -1);
	private static cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];
	private static boolean isError = false;
	static {
		levelPanel = new IconText(Images.attackIcon, "Level 9999", Other.normalButtonFont);
		moneyPanel = new IconText(Images.coinIcon, "Money " + GameManager.getInstance().getMoney(), Other.normalButtonFont);
		livePanel = new IconText(Images.liveIcon, "Live " + GameManager.getInstance().getLives(), Other.normalButtonFont);
		debug = new IconText(Images.liveIcon, "", Other.normalButtonFont);
	}
	
	
	public static void addinfo(Pane pane) {
		pane.getChildren().addAll(levelPanel, moneyPanel, livePanel, debug);
	}
	
	public static void mountPanel(Pane pane) {
		pane.getChildren().addAll(towerInfoPanel, upgradeInfoPanel);
	}
	
	public static void render(GraphicsContext otherGC) {
		
		Tile t = GameManager.getInstance().getSelectedTile();
		Tile t2 = GameManager.getInstance().createTower(GameManager.getInstance().getTowerChoice(), 999, 999);
			
		
		debug.setText(GameManager.getInstance().getMousePos().toString());
		moneyPanel.setText("Money " + GameManager.getInstance().getMoney());
		livePanel.setText("Live" + GameManager.getInstance().getLives());
		if (t != null && t instanceof Tower)
			updateTowerInfo(t, true);
		else if (t2 != null)
			updateTowerInfo(t2, false);

		
		
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

	
	public static void updateTowerInfo(Tile t, boolean showUpgrade) {

		Tower tw = (Tower)t;
		towerInfoPanel.setTexts(tw.toString(), tw.getAttack() + "DPS", tw.getAttackCooldown() + " ms",  tw.getRange() + "tiles", tw.getDescription());
		
		if (!showUpgrade) {
			upgradeInfoPanel.setTexts(tw.toString(), "--", "--", "--", "Fully Upgraded");
		}
		else {
			try {
				if (!tw.canUpgrade()) throw new FullyUpgradedException();
				upgradeInfoPanel.setTexts(tw.toString(), tw.getUpgradedAttack() + "DPS", tw.getUpgradedAttackCooldown() + " ms"
						,  tw.getUpgradedRange() + "tiles", tw.getUpgradedDescription());
			}
			catch (FullyUpgradedException e) {
				upgradeInfoPanel.setTexts(tw.toString(), "--", "--", "--", "Fully Upgraded");
			}			
		}
	}
	
	
}
