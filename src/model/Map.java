package model;

import java.util.HashMap;

import javafx.scene.image.Image;
import util.cpp;
import util.cpp.pii;

public class Map {
	
	private int[][] tiles;
	private HashMap<Integer, Image> tileMap;
	private cpp.pii start, end;
	private Image previewImage;
	
	
	public Map(int[][] tiles, HashMap<Integer, Image> tileMap, pii start, pii end, Image previewImage) {
		this.tiles = tiles;
		this.tileMap = tileMap;
		this.start = start;
		this.end = end;
		this.previewImage = previewImage;
	}

	public int[][] getTiles() {
		return tiles;
	}
	
	public HashMap<Integer, Image> getTileMap() {
		return tileMap;
	}

	public cpp.pii getStart() {
		return start;
	}

	public cpp.pii getEnd() {
		return end;
	}
	
	public Image getPreviewImage() {
		return previewImage;
	}
	
	
}
