package sharedobject;

import java.util.ArrayList;

import model.Entity;
import util.GameUtil.ZIndexComparator;

public class SharedObject {
	private static SharedObject instance = new SharedObject();
	
	private ArrayList<Entity> renderables;
	private ZIndexComparator comp;
	public SharedObject() {
		renderables = new ArrayList<>();
		comp = new ZIndexComparator();
	}
	
	public void addRenderables(Entity ...entities) {
		for (Entity e: entities) {
			this.renderables.add(e);
		}
		this.renderables.sort(this.comp);
	}
	
	public void removeRenderables(Entity ...entities) {
		for (Entity e: entities) {
			this.renderables.remove(e);
		}
		this.renderables.sort(this.comp);
	}
	
	public ArrayList<Entity> getRenderables(){
		return this.renderables;
	}
	
	public void clear() {
		this.renderables.clear();
	}
	
	public static SharedObject getInstance() {
		return instance;
	}
	
}
