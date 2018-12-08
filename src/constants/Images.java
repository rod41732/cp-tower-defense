package constants;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;

public class Images {
	private static DoubleProperty progress = new SimpleDoubleProperty(0);
	
	public static Image bush1;
	public static Image tileNormal1;
	public static Image tileNormal2;
	public static Image tileUnplaceable;
	public static Image tileUnwalkable;
	public static Image tileBoth;

	public static Image bear;
	public static Image elephant;
	public static Image moose;
	public static Image tank;
	public static Image normalSoldier;
	public static Image fastSoldier ;
	public static Image armoredSoldier ;
	public static Image heavySoldier;
	public static Image normalPlane;
	public static Image fastPlane ;
	public static Image armoredPlane ;
	public static Image heavyPlane;
	public static Image normalTank;
	public static Image fastTank ;
	public static Image armoredTank ;
	public static Image heavyTank;
	public static Image normalCar;
	public static Image fastCar ;
	public static Image armoredCar ;
	public static Image heavyCar;

	public static Image normalTower;
	public static Image groundAttackTower;
	public static Image AirAttackTower;
	public static Image bombTower;
	public static Image fireTower;
	public static Image iceTower;
	public static Image normalBullet;
	public static Image piercingBullet;
	public static Image bomb;
	public static Image fireBullet;
	public static Image iceBullet;

	public static Image attackIcon;
	public static Image bombIcon;
	public static Image cooldownIcon;
	public static Image targetIcon;
	public static Image liveIcon;
	public static Image coinIcon;
	public static Image infoIcon;

	public static Image buttonSell;
	public static Image buttonSellDisabled;
	public static Image buttonSellHover;
	public static Image buttonSellPressed;
	public static Image buttonUpgrade;
	public static Image buttonUpgradeDisabled;
	public static Image buttonUpgradeHover;
	public static Image buttonUpgradePressed;
	public static Image buttonNext;
	public static Image buttonNextDisabled;
	public static Image buttonNextHover;
	public static Image buttonNextPressed;
	public static Image buttonPause;
	public static Image buttonPauseDisabled;
	public static Image buttonPauseHover;
	public static Image buttonPausePressed;

	public static Image pauseMenuPanel;
	public static Image towerButton;
	public static Image towerButtonPressed;
	public static Image towerInfoPanel;
	public static Image towerInfoPanelLocked;

	public static Image map0Preview;
	public static Image map1Preview;
	
	public static Image mainMenuBg;
	public static Image frame;
	public static Image pane;


	public static Image normalTowerFlash;
	public static Image crater;
	public static Image tankTrack;
	public static Image planeShadow;

	public static Image towerFocus;
	
	public static final Image[] explosion = new Image[16];
	public static final Image[] flame = new Image[16];
	public static final Image[] smoke = new Image[9];
	public static final Image[] aura = new Image[16];
	public static final Image[] boom = new Image[16];
	public static final Image[] spark = new Image[8];
	
	
	public static final Image[][] arrows = new Image[4][4];

	public static final Image[] loading = new Image[5];
	public static final Image vignette;

	static {
		for (int i=1; i<=5; i++) {
			loading[i-1] = new Image("ui/loading/" + i + ".png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, false, true);
		}
		vignette = new Image("ui/loading/vignette.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, false, true);
	}
	
	public static void loadResource(){
		bush1 = new Image("bush/bush1.png", 64, 64, true, true);
		
		tileNormal1 = new Image("tile/tile1.png", 64, 64, true, true);
		tileNormal2 = new Image("tile/tile2.png", 64, 64, true, true);
		tileUnplaceable = new Image("tile/tileUnplaceable.png", 64, 64, true, true);
		tileUnwalkable = new Image("tile/tileUnwalkable.png", 64, 64, true, true);
		tileBoth = new Image("tile/tileBoth.png", 64, 64, true, true);
		
		getProgress().set(0.1);
		
		bear = new Image("monster/bear.png", 64, 64, true, true);
		elephant = new Image("monster/elephant.png", 64, 64, true, true);
		moose = new Image("monster/moose.png", 64, 64, true, true);
		tank = new Image("monster/tank.png", 96, 96, true, true);
		
		normalSoldier = new Image("monster/soldier_normal.png", 48, 48, true, true);
		fastSoldier =  new Image("monster/soldier_fast.png", 48, 48, true, true);
		armoredSoldier =  new Image("monster/soldier_armored.png", 48, 48, true, true);
		heavySoldier = new Image("monster/soldier_heavy.png", 48, 48, true, true);
		
		
		
		normalPlane = new Image("monster/plane_normal.png", 64, 64, true, true);
		fastPlane =  new Image("monster/plane_fast.png", 64, 64, true, true);
		armoredPlane =  new Image("monster/plane_armored.png", 64, 64, true, true);
		heavyPlane = new Image("monster/plane_heavy.png", 64, 64, true, true);
		
		getProgress().set(0.2);
		
		normalTank = new Image("monster/tank_normal.png", 64, 64, true, true);
		fastTank =  new Image("monster/tank_fast.png", 64, 64, true, true);
		armoredTank =  new Image("monster/tank_armored.png", 64, 64, true, true);
		heavyTank = new Image("monster/tank_heavy.png", 64, 64, true, true);
		
		normalCar = new Image("monster/car_normal.png", 80, 80, true, true);
		fastCar =  new Image("monster/car_fast.png", 80, 80, true, true);
		armoredCar =  new Image("monster/car_armored.png", 80, 80, true, true);
		heavyCar = new Image("monster/car_heavy.png", 80, 80, true, true);
		
		
		getProgress().set(0.3);
		normalTower = new Image("tower/tower1.png", 64, 64, true, true);
		groundAttackTower = new Image("tower/tower2.png", 64, 64, true, true);
		AirAttackTower = new Image("tower/tower3.png", 64, 64, true, true);
		bombTower = new Image("tower/missile1.png", 64, 64, true, true);
		fireTower = new Image("tower/flame.png", 64, 64, true, true);
		iceTower = new Image("tower/ice.png", 64, 64, true, true);
		
		getProgress().set(0.4);
		normalBullet = new Image("projectile/normalBullet.png", 30, 30, true, true);
		piercingBullet = new Image("projectile/piercingBullet.png", 48, 48, true, true);
		bomb = new Image("projectile/bomb.png", 30, 30, true, true);
		fireBullet = new Image("projectile/fireBullet.png", 30, 30, true, true);
		iceBullet = new Image("projectile/iceBullet.png", 30, 30, true, true);
		attackIcon = new Image("icon/attack.png", 32, 32, true, true);
		bombIcon = new Image("icon/bomb.png", 32, 32, true, true);
		cooldownIcon = new Image("icon/cooldown.png", 32, 32, true, true);
		targetIcon = new Image("icon/target.png", 32, 32, true, true);
		
		liveIcon = new Image("icon/live.png", 32, 32, true, true);
		coinIcon = new Image("icon/coin.png", 32, 32, true, true);
		infoIcon = new Image("icon/info.png", 32, 32, true, true);
		
		
		getProgress().set(0.5);
		
		buttonSell = new Image("ui/button/button_sell.png", 190, 49, true, true);
		buttonSellDisabled = new Image("ui/button/button_sell_disabled.png", 190, 45, true, true);
		buttonSellHover = new Image("ui/button/button_sell_hover.png", 190, 45, true, true);
		buttonSellPressed = new Image("ui/button/button_sell_pressed.png", 190, 45, true, true);
		buttonUpgrade = new Image("ui/button/button_upgrade.png", 190, 49, true, true);
		buttonUpgradeDisabled = new Image("ui/button/button_upgrade_disabled.png", 190, 49, true, true);
		buttonUpgradeHover = new Image("ui/button/button_upgrade_hover.png", 190, 49, true, true);
		buttonUpgradePressed = new Image("ui/button/button_upgrade_pressed.png", 190, 45, true, true);
		buttonNext = new Image("ui/button/button_next.png", 190, 49, true, true);
		buttonNextDisabled = new Image("ui/button/button_next_disabled.png", 190, 49, true, true);
	
		
		
		buttonNextHover = new Image("ui/button/button_next_hover.png", 190, 49, true, true);
		buttonNextPressed = new Image("ui/button/button_next_pressed.png", 190, 45, true, true);
		buttonPause = new Image("ui/button/button_pause.png", 190, 49, true, true);
		buttonPauseDisabled = new Image("ui/button/button_pause_disabled.png", 190, 49, true, true);
		buttonPauseHover = new Image("ui/button/button_pause_hover.png", 190, 49, true, true);
		buttonPausePressed = new Image("ui/button/button_pause_pressed.png", 190, 45, true, true);
		pauseMenuPanel = new Image("ui/pause_menu_panel.png", 300, 300, true, true);
		towerButton = new Image("ui/tower_button.png", 80, 128, false, true);
		towerButtonPressed = new Image("ui/tower_button_pressed.png", 80, 128, false, true);
		towerInfoPanel = new Image("ui/tower_info_panel.png", 256, 192, true, true);
		towerInfoPanelLocked = new Image("ui/tower_info_panel_locked.png", 256, 192, true, true);
		
	
		towerFocus = new Image("ui/tower_focus.png", 64, 64, true, true);
		
		
		normalTowerFlash = new Image("particle/flare.png", 32, 32, true, true);
		crater = new Image("particle/crater.png", 64, 64, true, true);
		tankTrack = new Image("particle/tankTrack.png", 7, 40, true, true);
		planeShadow = new Image("particle/plane_shadow.png", 64, 64, true, true);
		map0Preview = new Image("map/map0.png", 335, 207, false, true);
		map1Preview = new Image("map/map1.png", 335, 207, false, true);

		
		for (int i=0; i<16; i++)
			explosion[i] = new Image("animation/explosion/"+i+".png", 96, 96, true, true);

		
		getProgress().set(0.6);
		
		
		for (int i=0; i<16; i++)
			flame[i] = new Image("animation/flame/"+i+".png", 96, 96, true, true);
		getProgress().set(0.7);
		for (int i=0; i<9; i++)
			smoke[i] = new Image("animation/smoke/"+i+".png", 96, 96, true, true);
		
		for (int i=0; i<16; i++)
			aura[i] = new Image("animation/aura/"+i+".png", 128, 128, true, true);

		getProgress().set(0.8);
		
		for (int i=0; i<16; i++)
			boom[i] = new Image("animation/boom/"+i+".png", 256, 256, true, true);
		
		for (int i=0; i<8; i++)
				spark[i] = new Image("animation/hitspark/"+i+".png", 32, 32, true, true);
		getProgress().set(0.9);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++)
				if (i != j) {
					arrows[i][j] = new Image(String.format("ui/arrow/%d%d.png", i, j), 64, 64, true, true);
				}
		}
		
		getProgress().set(1.0);

	}

	public static DoubleProperty getProgress() {
		return progress;
	}	
	
}
