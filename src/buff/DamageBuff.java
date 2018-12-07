package buff;

import model.Entity;
import model.Tower;

public class DamageBuff  extends Buff {

	public static final int ID = 21;
	private double mutliplier;
	
	public DamageBuff(double duration, double multiplier) {
		super(ID, duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Tower) {
			((Tower) e).addAttackMultiplier(mutliplier);
		}
	}
}
