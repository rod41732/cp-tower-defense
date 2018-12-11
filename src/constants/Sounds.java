package constants;

import javafx.scene.media.AudioClip;

public class Sounds {
	
	public static AudioClip click;
	public static AudioClip hitExplosion;
	public static AudioClip carExplosion;
	public static AudioClip planeExplosion;
	public static AudioClip tankExplosion;
	public static AudioClip bigExplosion;
	public static AudioClip fire;
	public static AudioClip gunLoud;
	public static AudioClip gunQuiet;
	public static AudioClip genericShoot;
	public static AudioClip missileLaunch;
	public static AudioClip mainMenuMusic;
	public static AudioClip inGameMusic;
	
	
	public static AudioClip win;
	
	public static void loadResources() {
		click = loadSound("button_click.mp3");
		hitExplosion = loadSound("explosion_small.mp3");
		carExplosion = loadSound("explosion_medium.mp3");
		planeExplosion = loadSound("explosion_medium.mp3");
		tankExplosion = loadSound("explosion_big.mp3");
		bigExplosion = loadSound("explosion_big.mp3");
		fire = loadSound("fire.mp3");
		gunLoud = loadSound("gun_big.mp3");
		gunQuiet = loadSound("gun_small.mp3");
		missileLaunch = loadSound("missile_shoot.mp3");
		genericShoot = loadSound("generic_shoot.mp3");
		win = loadSound("you_win.mp3");
		mainMenuMusic = loadSound("menu_bgm.mp3");
		inGameMusic = loadSound("ig_bgm.mp3");
		
		// adjust volumes
		hitExplosion.setVolume(1.5);
		tankExplosion.setVolume(0.6);
		planeExplosion.setVolume(0.6);
		carExplosion.setVolume(0.6);
		gunQuiet.setVolume(0.2);
		missileLaunch.setVolume(0.25);
		genericShoot.setVolume(0.25);
		fire.setVolume(0.2);
		
	}
	
//	public static AudioClip
	
	
	
	
	private static AudioClip loadSound(String path) {
		return new AudioClip(ClassLoader.getSystemResource("audio/" + path).toString());
	}
	
}
