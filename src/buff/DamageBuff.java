package buff;

import model.Entity;
import model.Tower;

public class DamageBuff  extends Buff {

	private double mutliplier;
	
	public DamageBuff(double duration, double multiplier) {
		super(21, duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Tower) {
			((Tower) e).addAttackMultiplier(mutliplier);
		}
	}
}
