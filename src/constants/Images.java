package constants;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;

public class Images {
	private static DoubleProperty progress = new SimpleDoubleProperty(0);

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

	public static Image buttonOrange;
	public static Image buttonOrangeDisabled;
	public static Image buttonOrangeHover;
	public static Image buttonOrangePressed;
	public static Image buttonGreen;
	public static Image buttonGreenDisabled;
	public static Image buttonGreenHover;
	public static Image buttonGreenPressed;
	public static Image buttonPurple;
	public static Image buttonPurpleDisabled;
	public static Image buttonPurpleHover;
	public static Image buttonPurplePressed;
	public static Image buttonYellow;
	public static Image buttonYellowDisabled;
	public static Image buttonYellowHover;
	public static Image buttonYellowPressed;
	public static Image buttonGray;
	public static Image buttonGrayDisabled;
	public static Image buttonGrayHover;
	public static Image buttonGrayPressed;

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
	public static Image mainButtonBg;
	


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
		
		// set preserve ratio to false for button
		buttonOrange = new Image("ui/button/button_orange.png", 190, 49, false, true);
		buttonOrangeDisabled = new Image("ui/button/button_orange_disabled.png", 190, 49, false, true);
		buttonOrangeHover = new Image("ui/button/button_orange_hover.png", 190, 49, false, true);
		buttonOrangePressed = new Image("ui/button/button_orange_pressed.png", 190, 49, false, true);
		buttonGreen = new Image("ui/button/button_green.png", 190, 49, false, true);
		buttonGreenDisabled = new Image("ui/button/button_green_disabled.png", 190, 49, false, true);
		buttonGreenHover = new Image("ui/button/button_green_hover.png", 190, 49, false, true);
		buttonGreenPressed = new Image("ui/button/button_green_pressed.png", 190, 49, false, true);
		buttonPurple = new Image("ui/button/button_purple.png", 190, 49, false, true);
		buttonPurpleDisabled = new Image("ui/button/button_purple_disabled.png", 190, 49, false, true);
	
		
		
		buttonPurpleHover = new Image("ui/button/button_purple_hover.png", 190, 49, false, true);
		buttonPurplePressed = new Image("ui/button/button_purple_pressed.png", 190, 49, false, true);
		buttonYellow = new Image("ui/button/button_yellow.png", 190, 49, false, true);
		buttonYellowDisabled = new Image("ui/button/button_yellow_disabled.png", 190, 49, false, true);
		buttonYellowHover = new Image("ui/button/button_yellow_hover.png", 190, 49, false, true);
		buttonYellowPressed = new Image("ui/button/button_yellow_pressed.png", 190, 49, false, true);
		buttonGray = new Image("ui/button/button_gray.png", 190, 49, false, true);
		buttonGrayDisabled = new Image("ui/button/button_gray_disabled.png", 190, 49, false, true);
		buttonGrayHover = new Image("ui/button/button_gray_hover.png", 190, 49, false, true);
		buttonGrayPressed = new Image("ui/button/button_gray_pressed.png", 190, 49, false, true);
		pauseMenuPanel = new Image("ui/pause_menu_panel.png", 300, 300, false, true);
		towerButton = new Image("ui/tower_button.png", 80, 128, false, true);
		towerButtonPressed = new Image("ui/tower_button_pressed.png", 80, 128, false, true);

	
		towerFocus = new Image("ui/tower_focus.png", 64, 64, true, true);
		frame = new Image("ui/frame.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, true, true);
		
		normalTowerFlash = new Image("particle/flare.png", 32, 32, true, true);
		crater = new Image("particle/crater.png", 64, 64, true, true);
		tankTrack = new Image("particle/tankTrack.png", 7, 40, true, true);
		planeShadow = new Image("particle/plane_shadow.png", 64, 64, true, true);
		
		bgMapSelect = new Image("ui/bg_mapselect.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT, true, true);
		mainButtonBg = new Image("ui/bg_main_button.png", 317, 240, true, true);
		
		double mapW = Numbers.COLUMNS*Numbers.TILE_SIZE, mapH = Numbers.ROWS*Numbers.TILE_SIZE;
		for (int i=0; i<4; i++) {
			mapBgs[i] = new Image("map/"+i+".png", mapW, mapH, true, true);
			mapPreviews[i] = new Image("map/"+i+".png", mapW/3.5, mapH/3.5, true, true);
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
