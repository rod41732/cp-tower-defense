package buff;

import model.Entity;
import model.Monster;

public class ArmorBuff  extends Buff {

	public static final int ID = 4;
	private double mutliplier;
	
	public ArmorBuff(double duration, double multiplier) {
		super(ID, duration);
		this.mutliplier = multiplier;
	}
	
	@Override
	public void applyTo(Entity e) {
		if (e instanceof Monster) {
			((Monster) e).addDamageTakenMultiplier(-mutliplier);
		}
	}
}
