package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public abstract class UIElement {
	
	protected double x;
	protected double y;
	protected boolean isHovered = false;
	protected boolean isClicked = false;
	
	public UIElement(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(GraphicsContext gc);
	
	public abstract void handleHover(MouseEvent e);
	public abstract void handleClick(MouseEvent e);	
}
