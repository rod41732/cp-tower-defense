package ui;

import java.util.ArrayList;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class RichTextBox {
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
	private boolean isAlignRight = false;
	private boolean isShowing = true;
	
	@SuppressWarnings("restriction")
	public RichTextBox(ArrayList<Image> images, ArrayList<String> texts, double x, double y) {
		this.x = x;
		this.y = y;
		this.images = images;
		this.texts = texts;
		calculateLayout();
	}
	
	public void calculateLayout() {

		if (images.size() == 0 || texts.size() == 0) return;
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		
		imgW.clear();
		imgH.clear();
		textW.clear();
		textH.clear();
		
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
		if (!isShowing) return;
		if (images.size() == 0 || texts.size() == 0) return;
		DropShadow shadow = new DropShadow();
	    shadow.setOffsetY(1.0);
	    shadow.setOffsetX(1.0);
	    shadow.setColor(Color.color(0, 0, 0, 0.5));
	    gc.setEffect(shadow);
		if (!isAlignRight) {
			int n = Math.min(imgH.size(), textW.size());
			double w = maxImgWidth+maxTextWidth+2*PADDING+SPACING, h =imgH.get(n-1)+2*PADDING; 
			gc.setTextBaseline(VPos.CENTER);
			for (int i=0; i<n; i++) {
				Image current = images.get(i);
				gc.drawImage(current, x + PADDING, y + PADDING + imgH.get(i)-current.getHeight());
				gc.setFont(i == 0 ? titleFont : textFont);
				gc.setFill(Color.RED);
				gc.fillText(texts.get(i), x + maxImgWidth+PADDING+SPACING, y + imgH.get(i)+PADDING);
			}			
			gc.setTextBaseline(VPos.BOTTOM);
		}
		else {
			int n = imgH.size();
			double w = maxImgWidth+maxTextWidth+2*PADDING+SPACING, h =imgH.get(n-1)+2*PADDING; 

			gc.setTextBaseline(VPos.CENTER);
			for (int i=0; i<n; i++) {
				Image current = images.get(i);
				gc.drawImage(current, x + w - PADDING - current.getWidth(), y + PADDING + imgH.get(i)-current.getHeight());
				gc.setFont(i == 0 ? titleFont : textFont);
				gc.setFill(Color.RED);
				gc.fillText(texts.get(i), x + w - maxImgWidth-PADDING-SPACING - textW.get(i), y + imgH.get(i) - current.getHeight()/2 +PADDING);
			}
			gc.setTextBaseline(VPos.BOTTOM);
		}
		gc.setEffect(null);
	}

	public boolean isAlignRight() {
		return isAlignRight;
	}

	public void setAlignRight(boolean isAlignRight) {
		this.isAlignRight = isAlignRight;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	public ArrayList<String> getTexts() {
		return texts;
	}

	public void setTexts(ArrayList<String> texts) {
		this.texts = texts;
	}

	public static double getPadding() {
		return PADDING;
	}

	public static double getSpacing() {
		return SPACING;
	}

	public static Font getTitlefont() {
		return titleFont;
	}

	public static Font getTextfont() {
		return textFont;
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}	
}
