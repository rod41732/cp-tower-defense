package buff;

import model.Entity;
import model.IExpirable;

public abstract class Buff implements IExpirable {

	protected double duration;
	protected double age = 0;
	protected int id;
	// TODO: add some id to buff and use that to check instead of getClass()
	public Buff(int id, double duration) {
		this.id = id;
		this.duration = duration;
	}
	
	public void age() {
		age += 1000./60;
	}
	
	public boolean isExpired() {
		return age >= duration;
	}
	
	
	public int getId() {
		return id;
	}

	public abstract void applyTo(Entity e);
	
}
