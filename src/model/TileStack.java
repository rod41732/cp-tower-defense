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

	public void render(GraphicsContext gc) {
		for (Tile t: layers)
			t.render(gc);
	}
	
	// return top selectable tile
	public Tile select() {
		Tile top = layers.lastElement();
		return top.isSelectable() ? top : null;
	}
	
	public void push(Tile t) {
		System.out.println("push" + t);
		layers.addElement(t);
	}
	
	public void pop() {
		layers.removeElementAt(layers.size()-1);
	}
	
	public Tile top() {
		return layers.get(layers.size()-1);
	}
	
	public boolean isPlaceable() {
		for (Tile t: layers)
			if (!t.isPlaceable()) return false;
		return true;
	}
	
	public boolean isWalkable() {
		for (Tile t: layers)
			if (!t.isPlaceable()) return false;
		return true;
	}

}
