package ui.component;


import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IconText extends HBox {
	
	private ImageView image;
	private Text text;
	
	public IconText(Image image, String text) {
		super(8);
		this.image = new ImageView(image);
		this.text = new Text(text);
		getChildren().addAll(this.image, this.text);
		setAlignment(Pos.CENTER_LEFT);
	}

	public IconText(Image image, String text, Font font) {
		this(image, text);
		this.text.setFont(font);
	}

	
	public void setImage(Image image) {
		this.image.setImage(image);
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	
}
