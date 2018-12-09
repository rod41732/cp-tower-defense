package constants;

import java.util.ArrayList;
import java.util.HashMap;

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

	public static Image normalSoldier;
	public static Image fastSoldier;
	public static Image normalPlane;
	public static Image fastPlane;
	public static Image armoredPlane;
	public static Image heavyPlane;
	public static Image normalTank;
	public static Image fastTank;
	public static Image armoredTank;
	public static Image heavyTank;
	public static Image normalCar;
	public static Image fastCar;
	public static Image armoredCar;
	public static Image heavyCar;

	public static Image normalCarDead;
	public static Image normalPlaneDead;
	public static Image heavyPlaneDead;
	public static Image normalTankDead;
	public static Image fastTankDead;
	public static Image armoredTankDead;
	public static Image heavyTankDead;

	
	
	
	public static Image normalTower;
	public static Image groundAttackTower;
	public static Image AirAttackTower;
	public static Image bombTower;
	public static Image fireTower;
	public static Image iceTower;

	public static HashMap<String, Image[]> towerImages;
	public static Image heavySoldier;
	public static Image armoredSoldier ;
	
	
	public static Image normalBullet;
	public static Image piercingBullet;
	public static Image missileBullet;
	public static Image fireBullet;
	public static Image iceBullet;
	public static Image airBullet;
	public static Image groundBullet;
	public static Image armorBreakerBullet;

	
	
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

	
	public static Image bgMapSelect;
	public static Image[] mapPreviews = new Image[6];
	public static Image[] mapBgs = new Image[6];
	
	public static Image mainMenuBg;
	public static Image frame;
	public static Image logo;
	public static Image loadingBar;
	public static Image loadingText;
	


	public static Image normalTowerFlash;
	public static Image crater;
	public static Image tankTrack;
	public static Image planeShadow;
	public static Image towerFocus;

	public static Image towerBase;

	
	public static final Image[] explosion = new Image[16];
	public static final Image[] deathExplosion = new Image[16];
	public static final Image[] flame = new Image[16];
	public static final Image[] smoke = new Image[9];
	public static final Image[] aura = new Image[16];
	public static final Image[] boom = new Image[16];
	public static final Image[] spark = new Image[8];
	public static final Image[] blood = new Image[12];
	
	
	public static final Image[][] arrows = new Image[4][4];

	public static final Image[] loading = new Image[5];
	public static final Image vignette;

	static {
		for (int i=1; i<=5; i++) {
			loading[i-1] = new Image("ui/loading/" + i + ".png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, false, true);
		}
		vignette = new Image("ui/loading/vignette.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, false, true);
		logo = new Image("ui/logo.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, true, true);
		loadingBar = new Image("ui/loading_bar.png", 1040, 47, true, true);
		loadingText = new Image("ui/loading_text.png", 520, 47, true, true);
	}
	
	public static void loadResource(){
		bush1 = new Image("bush/bush1.png", 64, 64, true, true);
		
		tileNormal1 = new Image("tile/tile1.png", 64, 64, true, true);
		tileNormal2 = new Image("tile/tile2.png", 64, 64, true, true);
		tileUnplaceable = new Image("tile/tileUnplaceable.png", 64, 64, true, true);
		tileUnwalkable = new Image("tile/tileUnwalkable.png", 64, 64, true, true);
		tileBoth = new Image("tile/tileBoth.png", 64, 64, true, true);
		
		getProgress().set(0.1);

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
		
		normalCarDead = new Image("monster/dead/car.png", 80, 80, true, true);
		normalPlaneDead = new Image("monster/dead/plane.png", 80, 80, true, true);
		heavyPlaneDead = new Image("monster/dead/plane_heavy.png", 80, 80, true, true);
		normalTankDead = new Image("monster/dead/tank.png", 64, 64, true, true);
		fastTankDead = new Image("monster/dead/tank_fast.png", 64, 64, true, true);
		armoredTankDead = new Image("monster/dead/tank_armored.png", 64, 64, true, true);
		heavyTankDead = new Image("monster/dead/tank_heavy.png", 64, 64, true, true);
		
		getProgress().set(0.3);	
		String[] towerTypes = {"Air", "ArmorBreaker", "Buff",
				"Bomb", "Fire", "Ground", "Ice", "Missile", "Default"};
		towerImages = new HashMap<>();
		for (String typ: towerTypes) {
			Image[] imgs = new Image[5];
			for (int i=1; i<=5; i++) { // name start at 1
				imgs[i-1] = new Image(String.format("tower/%s/%d.png", typ.toLowerCase(), i), 64, 64, true, true);
			}
			towerImages.put(typ, imgs);
		}
		
		
		towerBase = new Image("tower/base.png", 64, 64, true, true);
		
		
		getProgress().set(0.4);
		normalBullet = new Image("projectile/normalBullet.png", 30, 30, true, true);
		piercingBullet = new Image("projectile/piercingBullet.png", 48, 48, true, true);
		missileBullet = new Image("projectile/missileBullet.png", 30, 30, true, true);
		fireBullet = new Image("projectile/fireBullet.png", 30, 30, true, true);
		iceBullet = new Image("projectile/iceBullet.png", 30, 30, true, true);
		airBullet = new Image("projectile/airBullet.png", 30, 30, true, true);
		groundBullet = new Image("projectile/groundBullet.png", 30, 30, true, true);
		armorBreakerBullet = new Image("projectile/armorBreakerBullet.png", 30, 30, true, true);
		
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
		frame = new Image("ui/frame.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, true, true);
		
		normalTowerFlash = new Image("particle/flare.png", 32, 32, true, true);
		crater = new Image("particle/crater.png", 64, 64, true, true);
		tankTrack = new Image("particle/tankTrack.png", 7, 40, true, true);
		planeShadow = new Image("particle/plane_shadow.png", 64, 64, true, true);
		
		bgMapSelect = new Image("ui/bg_mapselect.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, true, true);
		
		
		double mapW = Numbers.COLUMNS*Numbers.TILE_SIZE, mapH = Numbers.ROWS*Numbers.TILE_SIZE;
		for (int i=0; i<4; i++) {
			mapBgs[i] = new Image("map/"+i+".png", mapW, mapH, true, true);
			mapPreviews[i] = new Image("map/"+i+".png", mapW/5, mapH/5, true, true);
		}
		
		
		
		for (int i=0; i<16; i++)
			explosion[i] = new Image("animation/explosion/"+i+".png", 96, 96, true, true);
		for (int i=0; i<16; i++)
			deathExplosion[i] = new Image("animation/death_explosion/"+i+".png", 128, 128, true, true);

		
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
		
		for (int i=0; i<12; i++)
			blood[i] = new Image("animation/blood/"+i+".png", 64, 64, true, true);
		
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
