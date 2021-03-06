package controller.game;

import constants.Numbers;
import controller.SuperManager;
import javafx.beans.property.IntegerProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Tower;

public class Handler {
	private GameManager gm;
	public Handler(GameManager gm) {
		this.gm = gm;
	}
	public void updateMousePos(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		gm.mousePos.first = x/Numbers.TILE_SIZE;
		gm.mousePos.second = y/Numbers.TILE_SIZE;
		// don't want to create new object
		gm.getTilePos().first = (int)gm.mousePos.first;
		gm.getTilePos().second = (int)gm.mousePos.second;
	}
	public boolean shouldHandle(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		return 0 <= x && x <= Numbers.COLUMNS*Numbers.TILE_SIZE &&
				0 <= y && y <= Numbers.ROWS*Numbers.TILE_SIZE ;
	}
	public void handleClick(MouseEvent e) {
		if (e.isConsumed()) return;
		if (!shouldHandle(e)) return ;
		double x = e.getX();
		double y = e.getY();
		if (e.getButton() == MouseButton.PRIMARY) {
			handleTileClick((int)x/Numbers.TILE_SIZE, (int)y/Numbers.TILE_SIZE);			
		}
		else if (e.getButton() == MouseButton.SECONDARY) {
			SuperManager.getInstance().getTowerChoiceProp().set(-1);
		}
		return ;
	}
	
	public void handleTileClick(int x, int y) {
		if (gm.getTowerChoice() == -1) {
			gm.setSelectedTile((Tower) gm.getPlacedTiles()[x][y].select());
		}
		else {
			gm.towerManager.placeAt(x, y);	
		}
	}
	public void handleKeyPress(KeyEvent e) {
		if (!SuperManager.getInstance().getIsGamePausedProp().get()) {
			e.consume();
			IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
			switch (e.getCode()) {
			case G:
				gm.requestNextWave();			
				break;
			case S:
				gm.sellTower();
				break;
			case D:
				gm.upgradeTower();
				break;
			case Z: 
				gm.addMoney(1000); 
				break;
			case DIGIT1:
				prop.set(prop.get() == 0 ? -1 : 0);
				break;
			case DIGIT2:
				prop.set(prop.get() == 1 ? -1 : 1);
				break;
			case DIGIT3:
				prop.set(prop.get() == 2 ? -1 : 2);
				break;
			case DIGIT4:
				prop.set(prop.get() == 3 ? -1 : 3);
				break;
			case DIGIT5:
				prop.set(prop.get() == 4 ? -1 : 4);
				break;
			case DIGIT6:
				prop.set(prop.get() == 5 ? -1 : 5);
				break;
			case DIGIT7:
				prop.set(prop.get() == 6 ? -1 : 6);
				break;
			case DIGIT8:
				prop.set(prop.get() == 7 ? -1 : 7);
				break;
			case DIGIT9:
				prop.set(prop.get() == 8 ? -1 : 8);
				break;
			default:
				break;
			}
		} 
	}
	
	public void setTowerChoice(int towerChoice) {
		SuperManager.getInstance().getTowerChoiceProp().set(towerChoice);
		return;
	}
}
