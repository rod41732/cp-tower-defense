package buff;

import model.Entity;
import model.Tower;

public class AttackSpeedBuff  extends Buff {
	public static final int ID = 20;
	private double mutliplier;
	
	public AttackSpeedBuff(double duration, double multiplier) {
		super(ID, duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Tower) {
			((Tower) e).addAttackSpeedMultiplier(mutliplier);
		}
	}
}
