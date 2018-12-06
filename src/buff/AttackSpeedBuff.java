package buff;

import model.Entity;
import model.Tower;

public class AttackSpeedBuff  extends Buff {

	private double mutliplier;
	
	public AttackSpeedBuff(double duration, double multiplier) {
		super(20, duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Tower) {
			((Tower) e).addAttackSpeedMultiplier(mutliplier);
		}
	}
}
