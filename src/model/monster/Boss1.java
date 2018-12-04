package model.monster;

import constants.Images;
import javafx.scene.canvas.GraphicsContext;
import model.Particle;

public class Boss1 extends GroundMonster {

	private Particle aura;
	
	public Boss1( double x, double y, double size, double health, double armor,
			double moveSpeed, int money) {
		super("Boss", Images.tank, x, y, size, health, armor, moveSpeed, money);
		aura = new Particle(Images.aura, x, y, 0, 0, 99999);
	}
	
	public Boss1( double x, double y) {
		this(x, y, 0.5, 1000, 5, 0.2, 100);
	}
	
	@Override
	protected void preUpdate() {
		aura.setX(x);
		aura.setY(y);
//		aura.setRotation(rotation);
	}
	
	
	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		aura.render(gc);
	}
	
	
	

}
