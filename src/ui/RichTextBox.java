package ui;

import java.util.ArrayList;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import constants.Images;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class RichTextBox {
	private static final double PADDING = 8.0;
	private static final double SPACING = 10.0;
	private static final Font titleFont = Font.font("KenVector Future Regular", 20);
	private static final Font textFont = Font.font("KenVector Future Regular", 14);
	private static ArrayList<Image> imgs = new ArrayList<>();
	static {
		imgs.add(Images.attackIcon);
		imgs.add(Images.targetIcon);
		imgs.add(Images.cooldownIcon);
	}
	private ArrayList<String> texts = new ArrayList<>();
	private double x, y;
	private boolean isShowing = true;
	
	@SuppressWarnings("restriction")
	public RichTextBox(ArrayList<String> texts, double x, double y) {
		this.x = x;
		this.y = y;
		this.texts = texts;
		}

	public void render(GraphicsContext gc) {
	
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.color(61./256, 52./256, 36./256, 0.7));
		gc.setFont(titleFont);
		gc.fillText(texts.get(0), x+16, y+10);
		gc.setFont(textFont);
		gc.fillText(texts.get(1), x+40, y+38);
		gc.fillText(texts.get(2), x+40, y+62);
		gc.fillText(texts.get(3), x+40, y+86);
		gc.fillText(texts.get(4), x+16, y+112);
		gc.drawImage(imgs.get(0), x+8, y+32);
		gc.drawImage(imgs.get(1), x+8, y+56);
		gc.drawImage(imgs.get(2), x+8, y+80);
	}


	public void setTexts(ArrayList<String> texts) {
		this.texts = texts;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}	
}
