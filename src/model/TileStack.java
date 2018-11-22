package model;

import java.util.Vector;

import javafx.scene.canvas.GraphicsContext;

public class TileStack {

	private Vector<Tile> layers = new Vector<>();
	
	
	public TileStack() {
		
	}
	
	public TileStack(Tile baseTile) {
		layers.add(baseTile);
	}

	public void render(GraphicsContext otherGC, GraphicsContext tileGC) { 
		for (Tile t: layers) {
			if (t instanceof Tower) t.render(otherGC);
			else t.render(tileGC);
		}
	}
	
	// return top selectable tile
	public Tile select() {
		Tile top = layers.lastElement();
		return top.isSelectable() ? top : null;
	}
	
	public void push(Tile t) {
		layers.addElement(t);
	}
	
	public void pop() {
		layers.removeElementAt(layers.size()-1);
	}
	
	public Tile top() {
		return layers.get(layers.size()-1);
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
