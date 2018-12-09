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
		
		
		// adjust volumes
		hitExplosion.setVolume(2);
		gunQuiet.setVolume(0.05);
		missileLaunch.setVolume(0.1);
		
	}
	
//	public static AudioClip
	
	
	
	
	private static AudioClip loadSound(String path) {
		return new AudioClip(ClassLoader.getSystemResource("audio/" + path).toString());
	}
	
}
