package ui.game;

import constants.Images;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ui.component.IconText;


public class TowerInfoPanel extends VBox {
	private static final Font titleFont = Font.font("KenVector Future Regular", 20);
	private static final Font textFont = Font.font("KenVector Future Regular", 11);
	
	
	
	private Label titleText, descText;
	private IconText attackInfo, cooldownInfo, rangeInfo;
	
	public TowerInfoPanel(String towerName, String attack, String cooldown, String range, String desc) {
		super(8);
		titleText = new Label(towerName);
		titleText.setMaxWidth(256);
		titleText.setFont(titleFont);
		attackInfo = new IconText(Images.attackIcon, attack, textFont);
		cooldownInfo = new IconText(Images.cooldownIcon, cooldown, textFont);
		rangeInfo = new IconText(Images.targetIcon, range, textFont);
		descText = new Label(desc);
		descText.setMaxWidth(256);
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
