package application;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class MainController {

	@FXML
	private TextField textFieldAESInput;

	@FXML
	private TextField textFieldAESOutput;

	@FXML
	private TextField textFieldAESKey;

	public void chooseFileAESInput(ActionEvent event) {
		chooseFile(textFieldAESInput);
	}

	public void chooseFileAESOutput(ActionEvent event) {
		chooseFile(textFieldAESOutput);
	}

	public void chooseFileAESKey(ActionEvent event) {
		chooseFile(textFieldAESKey);
	}

	public void viewFileAESInput(ActionEvent event) {
		viewFile(textFieldAESInput);
	}

	public void viewFileAESOutput(ActionEvent event) {
		viewFile(textFieldAESOutput);
	}

	public void viewFileAESKey(ActionEvent event) {
		viewFile(textFieldAESKey);
	}

	/**
	 * Open the file chooser and show the selected file in the specified text
	 * field. If there is no selected file, show nothing.
	 * 
	 * @param textField
	 *            Text field where the absolute path of the selected file will
	 *            be shown.
	 */
	private void chooseFile(TextField textField) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("./data"));

		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			textField.setText(selectedFile.getAbsolutePath());
		}
	}

	/**
	 * Takes the absolute path from the specified text field and tries to open
	 * it with the text editor depending on the operating system.
	 * 
	 * @param textField
	 *            Text field with the absolute path.
	 */
	private void viewFile(TextField textField) {
		String filePath = textField.getText().trim();
		File file = new File(filePath);

		boolean error = false;

		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			error = true;
		}

		try {
			if (!error) {
				Runtime.getRuntime().exec("gedit " + filePath);
			}
		} catch (IOException e) {
			error = true;
		}

		if (error) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			// alert.setTitle("Information Dialog");
			alert.setHeaderText("Selected file cannot be opened!");
			alert.show();
		}
	}

}
