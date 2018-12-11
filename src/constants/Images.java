package constants;

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
	public static Image appIcon;
	public static Image loadingBar;
	public static Image loadingText;
	public static Image mainButtonBg;
	


	public static Image normalTowerFlash;
	public static Image crater;
	public static Image tankTrack;
	public static Image planeShadow;
	public static Image towerFocus;
	public static Image shield;
	
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
			loading[i-1] = loadImage("ui/loading/" + i + ".png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		}
		vignette = loadImage("ui/loading/vignette.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		logo = loadImage("ui/logo.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		loadingBar = loadImage("ui/loading_bar.png", 1040, 47);
		loadingText = loadImage("ui/loading_text.png", 520, 47);
	}
	
	public static void loadResource(){

		
		double mapW = Numbers.COLUMNS*Numbers.TILE_SIZE, mapH = Numbers.ROWS*Numbers.TILE_SIZE;
		for (int i=0; i<4; i++) {
			mapBgs[i] = loadImage("map/"+i+".png", mapW, mapH);
			mapPreviews[i] = loadImage("map/"+i+".png", mapW/3.5, mapH/3.5);
		}
		getProgress().set(0.1);
		
		
		towerBase = loadImage("tower/base.png", 64, 64);
		String[] towerTypes = {"Air", "ArmorBreaker", "Buff",
				"Bomb", "Fire", "Ground", "Ice", "Missile", "Default"};
		towerImages = new HashMap<>();
		for (String typ: towerTypes) {
			Image[] imgs = new Image[5];
			for (int i=1; i<=5; i++) { // name start at 1
				imgs[i-1] = loadImage(String.format("tower/%s/%d.png", typ.toLowerCase(), i), 64, 64);
			}
			towerImages.put(typ, imgs);
		}
		
		getProgress().set(0.2);
		
		normalSoldier = loadImage("monster/soldier_normal.png", 48, 48);
		fastSoldier =  loadImage("monster/soldier_fast.png", 48, 48);
		armoredSoldier =  loadImage("monster/soldier_armored.png", 48, 48);
		heavySoldier = loadImage("monster/soldier_heavy.png", 48, 48);
		normalPlane = loadImage("monster/plane_normal.png", 64, 64);
		fastPlane =  loadImage("monster/plane_fast.png", 64, 64);
		armoredPlane =  loadImage("monster/plane_armored.png", 64, 64);
		heavyPlane = loadImage("monster/plane_heavy.png", 64, 64);
		normalTank = loadImage("monster/tank_normal.png", 64, 64);
		fastTank =  loadImage("monster/tank_fast.png", 64, 64);
		armoredTank =  loadImage("monster/tank_armored.png", 64, 64);
		heavyTank = loadImage("monster/tank_heavy.png", 64, 64);		
		normalCar = loadImage("monster/car_normal.png", 64, 64);
		fastCar =  loadImage("monster/car_fast.png", 64, 64);
		armoredCar =  loadImage("monster/car_armored.png", 64, 64);
		heavyCar = loadImage("monster/car_heavy.png", 64, 64);
		
		getProgress().set(0.3);	
		
		normalCarDead = loadImage("monster/dead/car.png", 64, 64);
		normalPlaneDead = loadImage("monster/dead/plane.png", 80, 80);
		heavyPlaneDead = loadImage("monster/dead/plane_heavy.png", 80, 80);
		normalTankDead = loadImage("monster/dead/tank.png", 64, 64);
		fastTankDead = loadImage("monster/dead/tank_fast.png", 64, 64);
		armoredTankDead = loadImage("monster/dead/tank_armored.png", 64, 64);
		heavyTankDead = loadImage("monster/dead/tank_heavy.png", 64, 64);
		
		
		
		normalBullet = loadImage("projectile/normalBullet.png", 30, 30);
		missileBullet = loadImage("projectile/missileBullet.png", 30, 30);
		fireBullet = loadImage("projectile/fireBullet.png", 30, 30);
		iceBullet = loadImage("projectile/iceBullet.png", 30, 30);
		airBullet = loadImage("projectile/airBullet.png", 30, 30);
		groundBullet = loadImage("projectile/groundBullet.png", 30, 30);
		armorBreakerBullet = loadImage("projectile/armorBreakerBullet.png", 30, 30);
		
		getProgress().set(0.4);
		
		
		normalTowerFlash = loadImage("particle/flare.png", 32, 32);
		crater = loadImage("particle/crater.png", 64, 64);
		tankTrack = loadImage("particle/tankTrack.png", 7, 40);
		planeShadow = loadImage("particle/plane_shadow.png", 64, 64);
		shield = loadImage("particle/shield.png", 128, 128);
		
	
		
		attackIcon = loadImage("icon/attack.png", 32, 32);
		bombIcon = loadImage("icon/bomb.png", 32, 32);
		cooldownIcon = loadImage("icon/cooldown.png", 32, 32);
		targetIcon = loadImage("icon/target.png", 32, 32);
		
		liveIcon = loadImage("icon/live.png", 32, 32);
		coinIcon = loadImage("icon/coin.png", 32, 32);
		infoIcon = loadImage("icon/info.png", 32, 32);
		appIcon = loadImage("icon/app_icon.png", 16, 16);
		
		
		
		getProgress().set(0.5);
		
		
		
		
		buttonOrange = loadImage("ui/button/button_orange.png", 190, 49);
		buttonOrangeDisabled = loadImage("ui/button/button_orange_disabled.png", 190, 49);
		buttonOrangeHover = loadImage("ui/button/button_orange_hover.png", 190, 49);
		buttonOrangePressed = loadImage("ui/button/button_orange_pressed.png", 190, 49);
		buttonGreen = loadImage("ui/button/button_green.png", 190, 49);
		buttonGreenDisabled = loadImage("ui/button/button_green_disabled.png", 190, 49);
		buttonGreenHover = loadImage("ui/button/button_green_hover.png", 190, 49);
		buttonGreenPressed = loadImage("ui/button/button_green_pressed.png", 190, 49);
		buttonPurple = loadImage("ui/button/button_purple.png", 190, 49);
		buttonPurpleDisabled = loadImage("ui/button/button_purple_disabled.png", 190, 49);
		buttonPurpleHover = loadImage("ui/button/button_purple_hover.png", 190, 49);
		buttonPurplePressed = loadImage("ui/button/button_purple_pressed.png", 190, 49);
		buttonYellow = loadImage("ui/button/button_yellow.png", 190, 49);
		buttonYellowDisabled = loadImage("ui/button/button_yellow_disabled.png", 190, 49);
		buttonYellowHover = loadImage("ui/button/button_yellow_hover.png", 190, 49);
		buttonYellowPressed = loadImage("ui/button/button_yellow_pressed.png", 190, 49);
		
		getProgress().set(0.6);
		
		buttonGray = loadImage("ui/button/button_gray.png", 190, 49);
		buttonGrayDisabled = loadImage("ui/button/button_gray_disabled.png", 190, 49);
		buttonGrayHover = loadImage("ui/button/button_gray_hover.png", 190, 49);
		buttonGrayPressed = loadImage("ui/button/button_gray_pressed.png", 190, 49);
		pauseMenuPanel = loadImage("ui/pause_menu_panel.png", 300, 300);
		towerButton = loadImage("ui/tower_button.png", 80, 128);
		towerButtonPressed = loadImage("ui/tower_button_pressed.png", 80, 128);
		bgMapSelect = loadImage("ui/bg_mapselect.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		mainButtonBg = loadImage("ui/bg_main_button.png", 317, 240);
		towerFocus = loadImage("ui/tower_focus.png", 64, 64);
		frame = loadImage("ui/frame.png", Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		
		
		
		for (int i=0; i<16; i++) explosion[i] = loadImage("animation/explosion/"+i+".png", 96, 96);
		for (int i=0; i<16; i++) deathExplosion[i] = loadImage("animation/death_explosion/"+i+".png", 128, 128);
		
		getProgress().set(0.7);
		
		for (int i=0; i<16; i++) flame[i] = loadImage("animation/flame/"+i+".png", 96, 96);
		for (int i=0; i<9; i++) smoke[i] = loadImage("animation/smoke/"+i+".png", 96, 96);
		for (int i=0; i<16; i++) aura[i] = loadImage("animation/aura/"+i+".png", 64, 128);
		
		getProgress().set(0.8);
		
		for (int i=0; i<16; i++) boom[i] = loadImage("animation/boom/"+i+".png", 256, 256);
		for (int i=0; i<12; i++) blood[i] = loadImage("animation/blood/"+i+".png", 64, 64);
		
		getProgress().set(0.9);
		
		for (int i=0; i<8; i++) spark[i] = loadImage("animation/hitspark/"+i+".png", 32, 32);
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++)
				if (i != j) {
					arrows[i][j] = loadImage(String.format("ui/arrow/%d%d.png", i, j), 64, 64);
				}
		}
		
		
		
		getProgress().set(1.0);

	}

	public static DoubleProperty getProgress() {
		return progress;
    }	
    
    public static Image loadImage(String path, double width, double height){
        return new Image(ClassLoader.getSystemResource(path).toString(), width, height, true, true);
    }
	
}
