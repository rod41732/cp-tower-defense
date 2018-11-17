package ui;

import constants.Images;
import controller.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Button extends UIElement {

	protected Image button, hover, image;
	protected double w, h;
	
	public Button(Image button, Image hover,double x, double y) {
		super(x, y);
		this.image = button;
		this.button = button;
		this.hover = hover;
		this.w = button.getWidth();
		this.h = button.getHeight();
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.drawImage(button, x, y);
	}

	private boolean inBound(double x, double y) {
		return this.x < x && x < this.x+w &&
				this.y < y && y < this.y+h;
	}
	
	@Override
	public void handleHover(MouseEvent e) {
		if (inBound(e.getX(), e.getY())) {
			e.consume();
			image = hover;
		}
		else {
			image = button;
		}
	}

	@Override
	public void handleClick(MouseEvent e) {
		GameManager.getInstance().update();
	}

}
