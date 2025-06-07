package image_processing;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainBody extends AnchorPane {
	private ImageView leftIV;
	private ImageView rightIV;
	private ProcessingChoice choice;
	private Image leftImg;
	private Image rightImg;
	private Button fileBtn = new Button("Wybierz plik");
	private Button saveBtn = new Button("Zapisz plik");
	FileChooser fileChooser = new FileChooser();
	File chosenImg;
	private VBox leftVBox;
	private VBox rightVBox;
	private VBox BtnVbox;

	public MainBody(ProcessingChoice choice) {
		this.choice = choice;

		leftIV = new ImageView();
		rightIV = new ImageView();
		leftVBox = new VBox(leftIV);
		leftVBox.setAlignment(Pos.CENTER);
		rightVBox = new VBox(rightIV);
		rightVBox.setAlignment(Pos.CENTER);

		saveBtn.disableProperty().bind(App.BtnsDisabled);

		BtnVbox = new VBox(fileBtn, saveBtn);
		BtnVbox.setAlignment(Pos.CENTER);
		BtnVbox.setSpacing(5.0d);

		fileChooser.setTitle("Wybierz obraz");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		choice.getChildren().add(BtnVbox);
		fileBtn.setOnAction(event -> {
			chosenImg = fileChooser.showOpenDialog(fileBtn.getScene().getWindow());
			if (chosenImg != null && chosenImg.exists() && chosenImg.getName().endsWith(".jpg")) { // ||
				// chosenImg.getName().endsWith(".jpeg")
				Toast.show((Stage) fileBtn.getScene().getWindow(), "Pomyślnie załadowano plik", 2000);
				leftImg = new Image(chosenImg.toURI().toString(), 400.0d, 600.0d, true, true);
				leftIV.setImage(leftImg);
				rightImg = new Image(chosenImg.toURI().toString(), 400.0d, 600.0d, true, true);
				rightIV.setImage(rightImg);
				App.BtnsDisabled.setValue(false);
			} else if (chosenImg != null && !chosenImg.getName().endsWith(".jpg")) {
				Toast.show((Stage) fileBtn.getScene().getWindow(), "Niedozwolony format pliku", 2000);
				leftImg = null;
				rightImg = null;
				leftIV.setImage(null);
				rightIV.setImage(null);
				App.BtnsDisabled.setValue(true);
			} else {
				Toast.show((Stage) fileBtn.getScene().getWindow(), "Nie udało się załadować pliku", 2000);
				leftImg = null;
				rightImg = null;
				leftIV.setImage(null);
				rightIV.setImage(null);
				App.BtnsDisabled.setValue(true);
			}
		});

		AnchorPane.setLeftAnchor(leftVBox, 20.0d);
		AnchorPane.setTopAnchor(leftVBox, 0.0d);
		AnchorPane.setBottomAnchor(leftVBox, 0.0d);

		AnchorPane.setRightAnchor(rightVBox, 20.0d);
		AnchorPane.setTopAnchor(rightVBox, 0.0d);
		AnchorPane.setBottomAnchor(rightVBox, 0.0d);

		AnchorPane.setLeftAnchor(choice, 20.0d);
		AnchorPane.setRightAnchor(choice, 20.0d);
		AnchorPane.setTopAnchor(choice, 0.0d);
		AnchorPane.setBottomAnchor(choice, 0.0d);

		this.getChildren().add(this.leftVBox);
		this.getChildren().add(this.choice);
		this.getChildren().add(this.rightVBox);
	}
}
