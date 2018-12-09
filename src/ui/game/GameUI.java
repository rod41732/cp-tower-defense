package ui.game;

import constants.Images;
import constants.Numbers;
import constants.Other;
import controller.SuperManager;
import controller.game.GameManager;
import controller.game.MonsterSpawner;
import exceptions.FullyUpgradedException;
import exceptions.PathBlockedException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Tile;
import model.Tower;
import ui.component.IconText;
import util.BFSAlgo;
import util.cpp;
public class GameUI {
	
	private static GameUI instance = new GameUI();
	
	
	private TowerInfoPanel towerInfoPanel = new TowerInfoPanel("Tower");
	private TowerInfoPanel upgradeInfoPanel = new TowerInfoPanel("Upgraded");
	private IconText levelPanel, moneyPanel, livePanel, debug;
	
	
	private cpp.pii lastPos = new cpp.pii(-1, -1);
	private cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];
	private boolean isError = false;
	private BFSAlgo bfs = new BFSAlgo();
	private Tower[] previewTowers = new Tower[9];
	public GameUI() {
		levelPanel = new IconText(Images.attackIcon, "Level 0", Other.thinButtonFont);
		moneyPanel = new IconText(Images.coinIcon, "$ " + GameManager.getInstance().getMoney(), Other.thinButtonFont);
		livePanel = new IconText(Images.liveIcon, ""+ GameManager.getInstance().getLives(), Other.thinButtonFont);
//		debug = new IconText(Images.liveIcon, "", Other.thinButtonFont);
		for (int i=0; i<9; i++) {
			previewTowers[i] = GameManager.getInstance().createTower(i, 0, 0);			
		}
	}
	
	
	public void addinfo(Pane pane) {
//		pane.getChildren().addAll(levelPanel, moneyPanel, livePanel, debug);
		pane.getChildren().addAll(levelPanel, moneyPanel, livePanel);
	}
	
	public void mountPanel(Pane pane) {
		pane.getChildren().addAll(towerInfoPanel, upgradeInfoPanel);
	}
	
	public void render(GraphicsContext gc) {
		GameManager gm = GameManager.getInstance();
		Tower selected = gm.getSelectedTower();
		Tower preview;			
		try {
			preview = previewTowers[gm.getTowerChoice()];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			preview = null;
		}
			
		
//		debug.setText(gm.getMousePos().toString());
		levelPanel.setText("Level " + MonsterSpawner.getInstace().getLevel());
		moneyPanel.setText("$ " + gm.getMoney());
		livePanel.setText("Live" + gm.getLives());
		if (selected != null)
			updateTowerInfo(selected, true);
		else if (preview != null)
			updateTowerInfo(preview, false);
		else 
			updateTowerInfo(null, false);
		
		
	
		cpp.pii tilePos = gm.getSelectedPosition();
		int choice = SuperManager.getInstance().getTowerChoiceProp().get();
		if (choice != -1) {	// placing tower
			try {
				preview.setX(tilePos.first+0.5);
				preview.setY(tilePos.second+0.5);
				if (preview.getX() < Numbers.COLUMNS && preview.getY() < Numbers.ROWS) {
					preview.render(gc, true);							
				}
				cpp.pii start = gm.getStartTilePos(), end = gm.getEndTilePos();
				
				if (!tilePos.equals(lastPos)) {
					lastPos.first = tilePos.first;
					lastPos.second = tilePos.second;
					if (gm.isPlaceable(tilePos.first, tilePos.second)) {
						path = bfs.BFS(end.first, end.second, start.first, start.second, tilePos);
						isError = false;
					}
					else {
						path = gm.getPath(); // no need to calculate
						isError = true;
					}
				}
				if (path != null && !isError) {
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
			drawGrid(gc);
		}
		else if (SuperManager.getInstance().getShouldDisplayPathProp().get()){
			PathRenderer.render(gm.getPath(), gm.getStartTilePos(), gm.getEndTilePos(), gc);
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
	
	public void drawGrid(GraphicsContext gc) {
		gc.save();
		gc.setStroke(Color.color(1, 1, 1, 0.3));
		gc.setLineWidth(2);
		gc.setGlobalBlendMode(BlendMode.DIFFERENCE);
		double w = Numbers.COLUMNS*Numbers.TILE_SIZE,
				h = Numbers.ROWS*Numbers.TILE_SIZE;
		int tz = Numbers.TILE_SIZE;
		for (int i=1; i<Numbers.ROWS; i++) {
			gc.beginPath();
			gc.moveTo(0, i*tz);
			gc.lineTo(w, i*tz);
			gc.closePath();
			gc.stroke();
		}
		for (int i=1; i<Numbers.COLUMNS; i++) {
			gc.beginPath();
			gc.moveTo(i*tz, 0);
			gc.lineTo(i*tz, h);
			gc.closePath();
			gc.stroke();
		}
		
		gc.restore();
	}


	public static GameUI getInstance() {
		return instance;
	}
	
}
