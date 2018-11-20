package buff;

import model.Entity;
import model.Monster;

public class DamageTakenDebuff  extends Buff {

	private double mutliplier;
	
	public DamageTakenDebuff(double duration, double multiplier) {
		super("Cursed ", "Damage taken is amplified", duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Monster) {
			((Monster) e).addDamageTakenMultiplier(mutliplier);
		}
	}
}
