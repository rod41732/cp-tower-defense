package ui;


import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Button {

	protected Image button, hover, disabled;
	protected double x, y, w, h;
	protected EventHandler<MouseEvent> handler;
	
	protected boolean isEnabled = false;
	protected boolean isHovered = false;
	protected boolean isShown = false;
	

	public Button(Image button, Image hover, Image disabled, double x, double y) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.disabled = disabled;
		this.hover = hover;
		this.w = button.getWidth();
		this.h = button.getHeight();
	}
	

	public void render(GraphicsContext gc) {
		if (!isShown) return ;
		if (!isEnabled) {
			gc.drawImage(disabled, x, y);
		}
		else {
			if (isHovered) {
				gc.drawImage(hover, x, y);
			}else {
				gc.drawImage(button, x, y);				
			}
		}
	}

	private boolean inBound(double x, double y) {
		return this.x < x && x < this.x+w &&
				this.y < y && y < this.y+h;
	}

	public void handleHover(MouseEvent e) {
		isHovered = inBound(e.getX(), e.getY()) && !e.isConsumed();
	}	
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}


	public void setShown(boolean isShown) {
		this.isShown = isShown;
	}


	public void setHandler(EventHandler<MouseEvent> handler) {
		this.handler = handler;
	}

	public void handleClick(MouseEvent e) {
		if (isEnabled && inBound(e.getX(), e.getY()) && !e.isConsumed()) {
			this.handler.handle(e);			
		}
	}

}
