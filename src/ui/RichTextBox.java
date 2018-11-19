package ui;

import java.util.ArrayList;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RichTextBox {
	// TODO: finish rich text box 
	private static final double PADDING = 8.0;
	private static final double SPACING = 10.0;
	private static final Font titleFont = Font.font("KenVector Future Regular", 20);
	private static final Font textFont = Font.font("KenVector Future Regular", 20);
	
	
	private ArrayList<Image> images = new ArrayList<>();
	private ArrayList<String> texts = new ArrayList<>();
	private double x, y;
	private double maxTextWidth, maxImgWidth;
	private ArrayList<Double> imgW = new ArrayList<>();
	private ArrayList<Double> imgH = new ArrayList<>();
	private ArrayList<Double> textW = new ArrayList<>();
	private ArrayList<Double> textH = new ArrayList<>();
	
	public RichTextBox(ArrayList<Image> images, ArrayList<String> texts, double x, double y) {
		this.x = x;
		this.y = y;
		this.images = images;
		this.texts = texts;
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		
		for (Image img: images) {
			if (img != null) {
				imgW.add(img.getWidth());
				imgH.add(img.getHeight());
			}
			else {
				imgW.add(0.0);
				imgH.add(0.0);
			}
		}
		textW.add(0.+fontLoader.computeStringWidth(texts.get(0), titleFont));
		
		for (int i=1; i<texts.size(); i++) {
			textW.add(0.+fontLoader.computeStringWidth(texts.get(i), textFont));
		}
		
		int n = imgH.size();
		// some quick sum shit
		maxImgWidth = 0;
		maxTextWidth = 0;
		for (int i=0; i<n ;i++) {
			maxImgWidth = Math.max(maxImgWidth, imgW.get(i));
			maxTextWidth = Math.max(maxTextWidth, textW.get(i));
		}
		
		for (int i=1; i<n; i++) {
			imgH.set(i, imgH.get(i-1)+imgH.get(i)+SPACING);
		}
	}
	
	// 5Head calculation
	public void render(GraphicsContext gc) {
		int n = imgH.size();
		gc.setFill(Color.LIGHTBLUE);
		gc.fillRect(x, y, maxImgWidth+maxTextWidth+2*PADDING+SPACING,
				imgH.get(n-1)+2*PADDING);
		for (int i=0; i<n; i++) {
			Image current = images.get(i);
			gc.drawImage(current, x + PADDING, y + PADDING + imgH.get(i)-current.getHeight());
			gc.setFont(i == 0 ? titleFont : textFont);
			gc.setFill(Color.RED);
			gc.fillText(texts.get(i), x + maxImgWidth+PADDING+SPACING, y + imgH.get(i)+PADDING);
		}
		
	}
		
}
