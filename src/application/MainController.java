package application;

import java.io.File;
import java.io.IOException;

import application.crypto.CryptoException;
import application.crypto.CryptoManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class MainController {

	private static final String DEFAULT_DIRECTORY = "./data";
	private static final String DEFAULT_EDITOR = "gedit";

	@FXML
	private TextField textFieldAESInput;

	@FXML
	private TextField textFieldAESOutput;

	@FXML
	private TextField textFieldAESKey;

	@FXML
	private TextField textFieldRSAInput;

	@FXML
	private TextField textFieldRSAOutput;

	@FXML
	private TextField textFieldRSAPublicKey;

	@FXML
	private TextField textFieldRSAPrivateKey;

	@FXML
	private ComboBox<Integer> comboBoxRSAKeySize;

	private CryptoManager cryptoManager = new CryptoManager();

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

	public void chooseFileRSAInput(ActionEvent event) {
		chooseFile(textFieldRSAInput);
	}

	public void chooseFileRSAOutput(ActionEvent event) {
		chooseFile(textFieldRSAOutput);
	}

	public void chooseFileRSAPublicKey(ActionEvent event) {
		chooseFile(textFieldRSAPublicKey);
	}

	public void chooseFileRSAPrivateKey(ActionEvent event) {
		chooseFile(textFieldRSAPrivateKey);
	}

	public void viewFileRSAInput(ActionEvent event) {
		viewFile(textFieldRSAInput.getText().trim());
	}

	public void viewFileRSAOutput(ActionEvent event) {
		viewFile(textFieldRSAOutput.getText().trim());
	}

	public void viewFileRSAPublicKey(ActionEvent event) {
		viewFile(textFieldRSAPublicKey.getText().trim());
	}

	public void viewFileRSAPrivateKey(ActionEvent event) {
		viewFile(textFieldRSAPrivateKey.getText().trim());
	}

	public void generateSymmetricKey(ActionEvent event) {

		String keyFilePath = textFieldAESKey.getText().trim();

		try {
			cryptoManager.generateSymmetricKey(keyFilePath);
			showAlert(Alert.AlertType.INFORMATION, "Key successfully generated!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		}

	}

	public void encryptFileAES(ActionEvent event) {
		String inputFilePath = textFieldAESInput.getText().trim();
		String outputFilePath = textFieldAESOutput.getText().trim();
		String keyFilePath = textFieldAESKey.getText().trim();

		try {
			cryptoManager.encryptFileSymmetric(inputFilePath, outputFilePath, keyFilePath);
			showAlert(Alert.AlertType.INFORMATION, "File successfully encrypted!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, "Error occured during the file encryption!");
		}
	}

	public void decryptFileAES(ActionEvent event) {
		String inputFilePath = textFieldAESInput.getText().trim();
		String outputFilePath = textFieldAESOutput.getText().trim();
		String keyFilePath = textFieldAESKey.getText().trim();

		try {
			cryptoManager.decryptFileSymmetric(inputFilePath, outputFilePath, keyFilePath);
			showAlert(Alert.AlertType.INFORMATION, "File successfully decrypted!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, "Error occured during the file encryption!");
		}
	}

	public void generateAsymmetricKeys(ActionEvent event) {

	}

	public void encryptFileRSA(ActionEvent event) {

	}

	public void decryptFileRSA(ActionEvent event) {

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
		fc.setInitialDirectory(new File(DEFAULT_DIRECTORY));

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
				Runtime.getRuntime().exec(DEFAULT_EDITOR + " " + filePath);
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
