package buff;

import model.Entity;
import model.IExpirable;

public abstract class Buff implements IExpirable {
	protected double duration;
	protected double age = 0;
	protected String name, description;
	
	public Buff(String name, String description, double duration) {
		this.name = name;
		this.description = description;
		this.duration = duration;
	}
	
	public void age() {
		age += 1000./16;
	}
	
	public boolean isExpired() {
		return age >= duration;
	}
	
	public abstract void applyTo(Entity e);
	
}