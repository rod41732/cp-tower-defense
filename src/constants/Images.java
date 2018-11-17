package constants;

import javafx.scene.image.Image;

public class Images {
	public static final Image bush1 = new Image("bush/bush1.png", 64, 64, true, true);
	public static final Image tile1 = new Image("tile/tile1.png", 64, 64, true, true);
	public static final Image tile2 = new Image("tile/tile2.png", 64, 64, true, true);
	
	public static final Image normalTower = new Image("tower/tower1.png", 64, 64, true, true);
	public static final Image bombTower = new Image("tower/missile1.png", 64, 64, true, true);
	public static final Image fireTower = new Image("tower/flame.png", 64, 64, true, true);
	public static final Image iceTower = new Image("tower/ice.png", 64, 64, true, true);
	public static final Image bear = new Image("monster/bear.png", 64, 64, true, true);
	public static final Image normalBullet = new Image("projectile/normalBullet.png", 30, 30, true, true);
	public static final Image bomb = new Image("projectile/bomb.png", 30, 30, true, true);
	public static final Image fireBullet = new Image("projectile/fireBullet.png", 30, 30, true, true);
	public static final Image iceBullet = new Image("projectile/iceBullet.png", 30, 30, true, true);
	
	public static final Image buttonSell = new Image("ui/button/button_sell.png", 190, 49, true, true);
	public static final Image buttonSellDisabled = new Image("ui/button/button_sell_disabled.png", 190, 45, true, true);
	public static final Image buttonSellHover = new Image("ui/button/button_sell_hover.png", 190, 45, true, true);
	public static final Image buttonUpgrade = new Image("ui/button/button_upgrade.png", 190, 49, true, true);
	public static final Image buttonUpgradeDisabled = new Image("ui/button/button_upgrade_disabled.png", 190, 49, true, true);
	public static final Image buttonUpgradeHover = new Image("ui/button/button_upgrade_hover.png", 190, 49, true, true);
	
	public static final Image[] explosion = new Image[16];
	public static final Image[] flame = new Image[16];
	
	static {
		for (int i=0; i<16; i++)
			explosion[i] = new Image("animation/explosion/"+i+".png", 96, 96, true, true);
		for (int i=0; i<16; i++)
			flame[i] = new Image("animation/flame/"+i+".png", 96, 96, true, true);
	}
}
