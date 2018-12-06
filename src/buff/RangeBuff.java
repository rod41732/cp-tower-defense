package buff;

import model.Entity;
import model.Tower;

public class RangeBuff  extends Buff {

	private double mutliplier;
	
	public RangeBuff(double duration, double multiplier) {
		super("Cursed ", "Damage taken is amplified", duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Tower) {
			((Tower) e).addRangeMultiplier(mutliplier);
		}
	}
}