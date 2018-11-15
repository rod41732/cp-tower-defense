package constants;

import javafx.scene.image.Image;

public class Images {
	public static final Image bush1 = new Image("bush/bush1.png", 64, 64, true, true);
//	public static final Image bush1 = new Image("bush/bush1.png", 64, 64, true, true);
	public static final Image tile1 = new Image("tile/tile1.png", 64, 64, true, true);
	public static final Image tile2 = new Image("tile/tile2.png", 64, 64, true, true);
	
	public static final Image tower1 = new Image("tower/tower1.png", 64, 64, true, true);
	public static final Image tower2 = new Image("tower/missile1.png", 64, 64, true, true);
	public static final Image tower3 = new Image("tower/flame.png", 64, 64, true, true);
	public static final Image bear = new Image("monster/bear.png", 64, 64, true, true);
	public static final Image bullet1 = new Image("projectile/bullet1.png", 30, 30, true, true);
	public static final Image bullet2 = new Image("projectile/bullet2.png", 30, 30, true, true);
	public static final Image bullet3 = new Image("projectile/bullet3.png", 30, 30, true, true);
	
	public static final Image buttonSell = new Image("ui/button/button_sell.png", 192, 60, true, true);
	public static final Image buttonSellDisabled = new Image("ui/button/button_sell_disabled.png", 192, 60, true, true);
	public static final Image buttonSellHover = new Image("ui/button/button_sell_hover.png", 192, 60, true, true);
	public static final Image buttonUpgrade = new Image("ui/button/button_upgrade.png", 192, 60, true, true);
	public static final Image buttonUpgradeDisabled = new Image("ui/button/button_upgrade_disabled.png", 192, 60, true, true);
	public static final Image buttonUpgradeHover = new Image("ui/button/button_upgrade_hover.png", 192, 60, true, true);
	
	public static final Image[] explosion = new Image[16];
	public static final Image[] flame = new Image[16];
	static {
		for (int i=0; i<16; i++)
			explosion[i] = new Image("animation/explosion/"+i+".png", 96, 96, true, true);
		for (int i=0; i<16; i++)
			flame[i] = new Image("animation/flame/"+i+".png", 96, 96, true, true);
	}
}
