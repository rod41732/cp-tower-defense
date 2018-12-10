package model.monster;

import javafx.scene.image.Image;
import model.Monster;

public abstract class SplittingMonster extends GroundMonster {

	protected boolean wasForceKilled = false;
	protected Class<? extends Monster> childClass;
	
	public SplittingMonster(Image image, double x, double y, double size, double health, double armor,
			double moveSpeed, int money, Class<? extends Monster> childClass) {
		super(image, x, y, size, health, armor, moveSpeed, money);
		this.childClass = childClass;
	}	
	
	@Override
	public void forceKill() {
		super.forceKill();
		wasForceKilled = true;
	}
	
	public abstract void onDeath();
}
