package ui;

import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Tile;
import model.Tower;
import util.cpp;
public class TowerMenu {
	
	// TODO: use buttons to check click (custom image buttons)
	private static double LEFT = 1344;
	private static double TOP = 0;
	private static int COL = 3;
	private static int ROW = 3;
	private static Image[] images = {Images.bombTower, Images.normalTower, Images.fireTower, Images.iceTower};
	private static RichTextBox towerInfoPanel = new RichTextBox(new ArrayList<>(), new ArrayList<>(), LEFT, 400);
	
	public static void render(GraphicsContext gc) {
		
		gc.setFill(Color.MAGENTA);
		Tile t = GameManager.getInstance().getSelectedTile();
		Tile t2 = GameManager.getInstance().createTower(GameManager.getInstance().getTowerChoice(), 999, 999);
				
		if (t != null && t instanceof Tower)
			renderTowerInfo(gc, t);
		else if (t2 != null)
			renderTowerInfo(gc, t2);
		
	}

	
	public static void renderTowerInfo(GraphicsContext gc, Tile t) {
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
	}
}
