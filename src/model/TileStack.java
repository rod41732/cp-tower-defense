package model;

import java.util.Vector;

import exceptions.UnplaceableException;

public class TileStack {

	private Vector<Tile> layers = new Vector<>();
	
	
	public TileStack() {
		
	}
	
	public TileStack(Tile baseTile) {
		layers.add(baseTile);
	}


	public Tile select() {
		Tile top = layers.lastElement();
		return top.isSelectable() ? top : null;
	}
	
	public void push(Tile t) throws UnplaceableException {
		if (!this.isPlaceable()) {
			throw new UnplaceableException();
		}
		layers.addElement(t);			
	}
	
	public void pop() {
		layers.removeElementAt(layers.size()-1);
	}
	
	public void clear() {
		layers.clear();
	}
	
	public boolean isPlaceable() {
		for (Tile t: layers)
			if (!t.isPlaceable()) return false;
		return true;
	}
	
	public boolean isWalkable() {
		for (Tile t: layers)
			if (!t.isWalkable()) return false;
		return true;
	}

}
