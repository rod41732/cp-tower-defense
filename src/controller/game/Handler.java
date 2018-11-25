package controller.game;

import constants.Numbers;
import controller.SuperManager;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import util.cpp;
import util.cpp.pff;

public class Handler {
	private GameManager gm;
	public Handler(GameManager gm) {
		this.gm = gm;
	}
	public void updateMousePos(GameManager gm, double x, double y) {
		x -= Numbers.LEFT_OFFSET;
		y -= Numbers.TOP_OFFSET;
		gm.mousePos.first = x/Numbers.TILE_SIZE;
		gm.mousePos.second = y/Numbers.TILE_SIZE;
		// don't want to create new object
		gm.tilePos.first = (int)gm.mousePos.first;
		gm.tilePos.second = (int)gm.mousePos.second;
	}
	boolean shouldHandle(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		return 0 <= x && x <= Numbers.COLUMNS*Numbers.TILE_SIZE &&
				0 <= y && y <= Numbers.ROWS*Numbers.TILE_SIZE ;
	}
	public void handleClick(MouseEvent e) {
		if (e.isConsumed()) return;
		if (!shouldHandle(e)) return ;
		double x = e.getX()-Numbers.LEFT_OFFSET;
		double y = e.getY()-Numbers.TOP_OFFSET;
		if (e.getButton() == MouseButton.PRIMARY) {
			gm.towerManager.handleTileClick((int)x/Numbers.TILE_SIZE, (int)y/Numbers.TILE_SIZE);			
		}
		else if (e.getButton() == MouseButton.SECONDARY) {
			System.out.println("Spawn monster at" + new pff(x/Numbers.TILE_SIZE, x/Numbers.TILE_SIZE));
			gm.updater.spawnMonster(x/Numbers.TILE_SIZE, y/Numbers.TILE_SIZE);
		}
		else {
			gm.towerManager.sellTower();
		}
		return ;
	}
	public void setTowerChoice(int towerChoice) {
		SuperManager.getInstance().getTowerChoiceProp().set(towerChoice);
		return;
	}
}
