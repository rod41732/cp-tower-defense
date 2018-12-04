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
	
	private static GameUI instance = new GameUI();
	// TODO: use buttons to check click (custom image buttons)
	private static double LEFT = 1344;
	private TowerInfoPanel towerInfoPanel = new TowerInfoPanel();
	private TowerInfoPanel upgradeInfoPanel = new TowerInfoPanel();
	private IconText levelPanel, moneyPanel, livePanel, debug;
	
	
	
	private cpp.pii lastPos = new cpp.pii(-1, -1);
	private cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];
	private boolean isError = false;
	public GameUI() {
		levelPanel = new IconText(Images.attackIcon, "Level 9999", Other.normalButtonFont);
		moneyPanel = new IconText(Images.coinIcon, "Money " + GameManager.getInstance().getMoney(), Other.normalButtonFont);
		livePanel = new IconText(Images.liveIcon, "Live " + GameManager.getInstance().getLives(), Other.normalButtonFont);
		debug = new IconText(Images.liveIcon, "", Other.normalButtonFont);
	}
	
	
	public void addinfo(Pane pane) {
		pane.getChildren().addAll(levelPanel, moneyPanel, livePanel, debug);
	}
	
	public void mountPanel(Pane pane) {
		pane.getChildren().addAll(towerInfoPanel, upgradeInfoPanel);
	}
	
	public void render(GraphicsContext gc) {

		
		Tile t = GameManager.getInstance().getSelectedTile();
		Tile t2 = GameManager.getInstance().createTower(GameManager.getInstance().getTowerChoice(), 999, 999);
			
		
		debug.setText(GameManager.getInstance().getMousePos().toString());
		moneyPanel.setText("Money " + GameManager.getInstance().getMoney());
		livePanel.setText("Live" + GameManager.getInstance().getLives());
		if (t != null && t instanceof Tower)
			updateTowerInfo(t, true);
		else if (t2 != null)
			updateTowerInfo(t2, false);
		else 
			updateTowerInfo(null, false);
		
		
		GameManager gm = GameManager.getInstance();
		cpp.pii tilePos = gm.getSelectedPosition();
		int choice = SuperManager.getInstance().getTowerChoiceProp().get();
		if (choice != -1) {	
			try {
				Tower floatingTower = gm.createTower(choice, tilePos.first, tilePos.second);
				if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
					floatingTower.render(gc, true);							
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
					PathRenderer.render(path, gm.getStartTilePos(), gm.getEndTilePos(), gc);
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

	
	public void updateTowerInfo(Tile t, boolean showUpgrade) {

		Tower tw = (Tower)t;
		if (tw == null) {
			towerInfoPanel.setTexts("--", "--", "--", "--", "--");
			upgradeInfoPanel.setTexts("--", "--", "--", "--", "--");
			return;
		}
		towerInfoPanel.setTexts(tw.toString(), tw.getAttack() + "DPS", tw.getAttackCooldown() + " ms",  tw.getRange() + "tiles", tw.getDescription());			
		if (!showUpgrade) {
			upgradeInfoPanel.setTexts(tw.toString(), "--", "--", "--", "--");
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


	public static GameUI getInstance() {
		return instance;
	}
	
	
	
	
}
