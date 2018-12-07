package buff;

import model.Entity;
import model.Tower;

public class RangeBuff  extends Buff {

	public static final int ID = 22;
	private double mutliplier;
	
	public RangeBuff(double duration, double multiplier) {
		super(ID, duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Tower) {
			((Tower) e).addRangeMultiplier(mutliplier);
		}
	}
}
