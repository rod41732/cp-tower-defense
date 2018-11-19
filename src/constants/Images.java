package constants;

import javafx.scene.image.Image;

public class Images {
	// TODO: change name to be more desciptive
	public static Image bush1 = new Image("bush/bush1.png", 64, 64, true, true);
	public static Image tile1 = new Image("tile/tile1.png", 64, 64, true, true);
	public static Image tile2 = new Image("tile/tile2.png", 64, 64, true, true);
	public static Image tileUnplaceable = new Image("tile/tileUnplaceable.png", 64, 64, true, true);
	public static Image tileUnplaceable2 = new Image("tile/tileUnplaceable.png", 256, 256, true, true);
	
	public static Image bear = new Image("monster/bear.png", 64, 64, true, true);
	public static Image elephant = new Image("monster/elephant.png", 64, 64, true, true);
	public static Image moose = new Image("monster/moose.png", 64, 64, true, true);
	public static Image normalTower = new Image("tower/tower1.png", 64, 64, true, true);
	public static Image bombTower = new Image("tower/missile1.png", 64, 64, true, true);
	public static Image fireTower = new Image("tower/flame.png", 64, 64, true, true);
	public static Image iceTower = new Image("tower/ice.png", 64, 64, true, true);
	public static Image normalBullet = new Image("projectile/normalBullet.png", 30, 30, true, true);
	public static Image bomb = new Image("projectile/bomb.png", 30, 30, true, true);
	public static Image fireBullet = new Image("projectile/fireBullet.png", 30, 30, true, true);
	public static Image iceBullet = new Image("projectile/iceBullet.png", 30, 30, true, true);
	
	public static Image attackIcon = new Image("icon/attack.png", 32, 32, true, true);
	public static Image bombIcon = new Image("icon/bomb.png", 32, 32, true, true);
	public static Image cooldownIcon = new Image("icon/cooldown.png", 32, 32, true, true);
	public static Image targetIcon = new Image("icon/target.png", 32, 32, true, true);
	public static Image liveIcon = new Image("icon/live.png", 32, 32, true, true);
	public static Image coinIcon = new Image("icon/coin.png", 32, 32, true, true);
	public static Image infoIcon = new Image("icon/info.png", 32, 32, true, true);
	
	public static Image buttonSell = new Image("ui/button/button_sell.png", 190, 49, true, true);
	public static Image buttonSellDisabled = new Image("ui/button/button_sell_disabled.png", 190, 45, true, true);
	public static Image buttonSellHover = new Image("ui/button/button_sell_hover.png", 190, 45, true, true);
	public static Image buttonSellPressed = new Image("ui/button/button_sell_pressed.png", 190, 45, true, true);
	public static Image buttonUpgrade = new Image("ui/button/button_upgrade.png", 190, 49, true, true);
	public static Image buttonUpgradeDisabled = new Image("ui/button/button_upgrade_disabled.png", 190, 49, true, true);
	public static Image buttonUpgradeHover = new Image("ui/button/button_upgrade_hover.png", 190, 49, true, true);
	public static Image buttonUpgradePressed = new Image("ui/button/button_upgrade_pressed.png", 190, 45, true, true);
	public static Image buttonNext = new Image("ui/button/button_next.png", 190, 49, true, true);
	public static Image buttonNextDisabled = new Image("ui/button/button_next_disabled.png", 190, 49, true, true);
	public static Image buttonNextHover = new Image("ui/button/button_next_hover.png", 190, 49, true, true);
	public static Image buttonNextPressed = new Image("ui/button/button_next_pressed.png", 190, 45, true, true);
	public static Image buttonPause = new Image("ui/button/button_pause.png", 190, 49, true, true);
	public static Image buttonPauseDisabled = new Image("ui/button/button_pause_disabled.png", 190, 49, true, true);
	public static Image buttonPauseHover = new Image("ui/button/button_pause_hover.png", 190, 49, true, true);
	public static Image buttonPausePressed = new Image("ui/button/button_pause_pressed.png", 190, 45, true, true);
	
	public static Image pauseMenuPanel = new Image("ui/pause_menu_panel.png", 300, 300, true, true);
	public static Image towerButton = new Image("ui/tower_button.png", 85, 128, false, true);
	public static Image towerButtonPressed = new Image("ui/tower_button_pressed.png", 85, 128, false, true);
	
	
	
	
	public static final Image[] explosion = new Image[16];
	public static final Image[] flame = new Image[16];
	
	static {
		for (int i=0; i<16; i++)
			explosion[i] = new Image("animation/explosion/"+i+".png", 96, 96, true, true);
		for (int i=0; i<16; i++)
			flame[i] = new Image("animation/flame/"+i+".png", 96, 96, true, true);
	}
}
