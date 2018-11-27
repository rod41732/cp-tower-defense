package ui.game;

import constants.Images;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.component.IconText;


public class TowerInfoPanel extends VBox {
	private static final double PADDING = 8.0;
	private static final double SPACING = 10.0;
	private static final Font titleFont = Font.font("KenVector Future Regular", 20);
	private static final Font textFont = Font.font("KenVector Future Regular", 14);
	
	
	
	private Text titleText, descText;
	private IconText attackInfo, cooldownInfo, rangeInfo;
	
	public TowerInfoPanel(String towerName, String attack, String cooldown, String range, String desc) {
		super(8);
		titleText = new Text(towerName);
		titleText.setFont(titleFont);
		attackInfo = new IconText(Images.attackIcon, attack, textFont);
		cooldownInfo = new IconText(Images.cooldownIcon, cooldown, textFont);
		rangeInfo = new IconText(Images.targetIcon, range, textFont);
		descText = new Text(desc);
		descText.setFont(textFont);
		setBackground(new Background(new BackgroundImage(Images.towerInfoPanel, null, null, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		getChildren().addAll(titleText, attackInfo, cooldownInfo, rangeInfo, descText);
	}
	
	public TowerInfoPanel() {
		this("", "", "", "", "");
	}
	
	public void setTexts(String towerName, String attack, String cooldown, String range, String desc) {
		titleText.setText(towerName);
		attackInfo.setText(attack);
		cooldownInfo.setText(cooldown);
		rangeInfo.setText(range);
		descText.setText(desc);
	}
}
