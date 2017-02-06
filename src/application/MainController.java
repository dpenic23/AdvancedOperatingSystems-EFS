package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import application.crypto.CryptoManager;
import application.file.FileManager;
import application.file.Pair;
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

	private CryptoManager cryptoManager = new CryptoManager();
	private FileManager fileManager = new FileManager();

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
		viewFile(textFieldAESInput.getText().trim());
	}

	public void viewFileAESOutput(ActionEvent event) {
		viewFile(textFieldAESOutput.getText().trim());
	}

	public void viewFileAESKey(ActionEvent event) {
		viewFile(textFieldAESKey.getText().trim());
	}

	public void generateSymmetricKey(ActionEvent event) {
		String key = Hex.encodeHexString(cryptoManager.generateSymmetricKey().getEncoded());
		String filePath = textFieldAESKey.getText().trim();

		List<Pair> properties = new ArrayList<>();

		properties.add(new Pair("Description", "Secret Key"));
		properties.add(new Pair("Method", "AES"));
		properties.add(new Pair("Secret Key", key));

		try {
			fileManager.writePropertiesToFile(properties, filePath);
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		}

	}

	public void encryptFileAES(ActionEvent event) {
		String inputFilePath = textFieldAESInput.getText().trim();

		try {
			List<Pair> properties = fileManager.readPropertiesFromFile(inputFilePath);
			for (Pair pair : properties) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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
	 * @param filePath
	 *            Absolute path of the file.
	 */
	private void viewFile(String filePath) {
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
			showAlert(Alert.AlertType.ERROR, "Selected file cannot be opened!");
		}
	}

	private void showAlert(Alert.AlertType alertType, String text) {
		Alert alert = new Alert(alertType);
		// alert.setTitle("Information Dialog");
		alert.setHeaderText(text);
		alert.show();
	}

}
