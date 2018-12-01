package controller.game;

import constants.Numbers;
import controller.SuperManager;
import exceptions.NotEnoughMoneyException;
import exceptions.PathBlockedException;
import exceptions.UnplaceableException;
import javafx.beans.property.IntegerProperty;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Tower;
import ui.SnackBar;
import util.Algorithm;

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
		gm.tilePos.first = (int)gm.mousePos.first;
		gm.tilePos.second = (int)gm.mousePos.second;
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
		else {
			gm.sellTower();
		}
		return ;
	}
	
	public void handleTileClick(int x, int y) {
		
		try {
			Tower t = gm.createTower(gm.getTowerChoice(), x, y);
			gm.handler.setTowerChoice(-1);
			gm.selectedTile = gm.placedTiles[x][y].top();
			// no tower => muse select
			if (t == null) {
				gm.selectedTile = gm.placedTiles[x][y].select();
				return;
			}
	
				// selected => try build
			if (!gm.selectedTile.isPlaceable()) {
				throw new UnplaceableException();
			}
			
			if (t.getPrice() > gm.money) {
				throw new NotEnoughMoneyException();
			}				
			
		
			gm.placedTiles[x][y].push(t);
			Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
			gm.towers.add(t);
			gm.renderables.add(t);
			gm.money -= t.getPrice();
			gm.selectedTile = t;
		}
		catch (PathBlockedException e) {
			gm.selectedTile = null;
			gm.placedTiles[x][y].pop();
			SnackBar.play("path blocked");
		}
		catch (NotEnoughMoneyException e) {
			SnackBar.play("not enough money");
		}
		catch (UnplaceableException e) {
			SnackBar.play("can't place there");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				gm.path = Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
			}
			catch(PathBlockedException e) {
			}
		}
		return ;
	}
	public void handleKeyPress(KeyEvent e) {
		if (!SuperManager.getInstance().getIsGamePausedProp().get()) {
			e.consume();
			IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
			switch (e.getCode()) {
			case G:
				GameManager.getInstance().requestNextWave();			
				break;
			case S:
				GameManager.getInstance().sellTower();
				break;
			case D:
				GameManager.getInstance().upgradeTower();
				break;
			case Z: 
				GameManager.getInstance().addMoney(1000); 
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
