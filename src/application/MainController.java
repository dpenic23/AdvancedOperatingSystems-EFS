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
	private ComboBox<Integer> comboBoxAESKeySize;

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

	@FXML
	private TextField textFieldSHAInput;

	@FXML
	private TextField textFieldSHAOutput;

	@FXML
	private TextField textFieldSHAHash;

	@FXML
	private TextField textFieldEnvelopeInput;

	@FXML
	private TextField textFieldEnvelopePublicKey;

	@FXML
	private TextField textFieldEnvelopeEnvelope;

	@FXML
	private TextField textFieldEnvelopePrivateKey;

	@FXML
	private TextField textFieldEnvelopeOutput;

	@FXML
	private TextField textFieldSignatureInput;

	@FXML
	private TextField textFieldSignaturePrivateKey;

	@FXML
	private TextField textFieldSignatureSignature;

	@FXML
	private TextField textFieldSignaturePublicKey;

	@FXML
	private TextField textFieldStampInput;

	@FXML
	private TextField textFieldStampPublicKeyB;

	@FXML
	private TextField textFieldStampPrivateKeyA;

	@FXML
	private TextField textFieldStampEnvelope;

	@FXML
	private TextField textFieldStampStamp;

	@FXML
	private TextField textFieldStampPublicKeyA;

	@FXML
	private TextField textFieldStampPrivateKeyB;

	@FXML
	private TextField textFieldStampOutput;

	private CryptoManager cryptoManager = new CryptoManager();

	public void chooseFileAESInput(ActionEvent event) {
		chooseFile(textFieldAESInput);
	}

	public void viewFileAESInput(ActionEvent event) {
		viewFile(textFieldAESInput.getText().trim());
	}

	public void chooseFileAESOutput(ActionEvent event) {
		chooseFile(textFieldAESOutput);
	}

	public void viewFileAESOutput(ActionEvent event) {
		viewFile(textFieldAESOutput.getText().trim());
	}

	public void chooseFileAESKey(ActionEvent event) {
		chooseFile(textFieldAESKey);
	}

	public void viewFileAESKey(ActionEvent event) {
		viewFile(textFieldAESKey.getText().trim());
	}

	public void chooseFileRSAInput(ActionEvent event) {
		chooseFile(textFieldRSAInput);
	}

	public void viewFileRSAInput(ActionEvent event) {
		viewFile(textFieldRSAInput.getText().trim());
	}

	public void chooseFileRSAOutput(ActionEvent event) {
		chooseFile(textFieldRSAOutput);
	}

	public void viewFileRSAOutput(ActionEvent event) {
		viewFile(textFieldRSAOutput.getText().trim());
	}

	public void chooseFileRSAPublicKey(ActionEvent event) {
		chooseFile(textFieldRSAPublicKey);
	}

	public void viewFileRSAPublicKey(ActionEvent event) {
		viewFile(textFieldRSAPublicKey.getText().trim());
	}

	public void chooseFileRSAPrivateKey(ActionEvent event) {
		chooseFile(textFieldRSAPrivateKey);
	}

	public void viewFileRSAPrivateKey(ActionEvent event) {
		viewFile(textFieldRSAPrivateKey.getText().trim());
	}

	public void chooseFileSHAInput(ActionEvent event) {
		chooseFile(textFieldSHAInput);
	}

	public void viewFileSHAInput(ActionEvent event) {
		viewFile(textFieldSHAInput.getText().trim());
	}

	public void chooseFileSHAOutput(ActionEvent event) {
		chooseFile(textFieldSHAOutput);
	}

	public void viewFileSHAOutput(ActionEvent event) {
		viewFile(textFieldSHAOutput.getText().trim());
	}

	public void chooseFileEnvelopeInput(ActionEvent event) {
		chooseFile(textFieldEnvelopeInput);
	}

	public void viewFileEnvelopeInput(ActionEvent event) {
		viewFile(textFieldEnvelopeInput.getText().trim());
	}

	public void chooseFileEnvelopePublicKey(ActionEvent event) {
		chooseFile(textFieldEnvelopePublicKey);
	}

	public void viewFileEnvelopePublicKey(ActionEvent event) {
		viewFile(textFieldEnvelopePublicKey.getText().trim());
	}

	public void chooseFileEnvelopeEnvelope(ActionEvent event) {
		chooseFile(textFieldEnvelopeEnvelope);
	}

	public void viewFileEnvelopeEnvelope(ActionEvent event) {
		viewFile(textFieldEnvelopeEnvelope.getText().trim());
	}

	public void chooseFileEnvelopePrivateKey(ActionEvent event) {
		chooseFile(textFieldEnvelopePrivateKey);
	}

	public void viewFileEnvelopePrivateKey(ActionEvent event) {
		viewFile(textFieldEnvelopePrivateKey.getText().trim());
	}

	public void chooseFileEnvelopeOutput(ActionEvent event) {
		chooseFile(textFieldEnvelopeOutput);
	}

	public void viewFileEnvelopeOutput(ActionEvent event) {
		viewFile(textFieldEnvelopeOutput.getText().trim());
	}

	public void chooseFileSignatureInput(ActionEvent event) {
		chooseFile(textFieldSignatureInput);
	}

	public void viewFileSignatureInput(ActionEvent event) {
		viewFile(textFieldSignatureInput.getText().trim());
	}

	public void chooseFileSignaturePrivateKey(ActionEvent event) {
		chooseFile(textFieldSignaturePrivateKey);
	}

	public void viewFileSignaturePrivateKey(ActionEvent event) {
		viewFile(textFieldSignaturePrivateKey.getText().trim());
	}

	public void chooseFileSignatureSignature(ActionEvent event) {
		chooseFile(textFieldSignatureSignature);
	}

	public void viewFileSignatureSignature(ActionEvent event) {
		viewFile(textFieldSignatureSignature.getText().trim());
	}

	public void chooseFileSignaturePublicKey(ActionEvent event) {
		chooseFile(textFieldSignaturePublicKey);
	}

	public void viewFileSignaturePublicKey(ActionEvent event) {
		viewFile(textFieldSignaturePublicKey.getText().trim());
	}

	public void chooseFileStampInput(ActionEvent event) {
		chooseFile(textFieldStampInput);
	}

	public void viewFileStampInput(ActionEvent event) {
		viewFile(textFieldStampInput.getText().trim());
	}

	public void chooseFileStampPublicKeyB(ActionEvent event) {
		chooseFile(textFieldStampPublicKeyB);
	}

	public void viewFileStampPublicKeyB(ActionEvent event) {
		viewFile(textFieldStampPublicKeyB.getText().trim());
	}

	public void chooseFileStampPrivateKeyA(ActionEvent event) {
		chooseFile(textFieldStampPrivateKeyA);
	}

	public void viewFileStampPrivateKeyA(ActionEvent event) {
		viewFile(textFieldStampPrivateKeyA.getText().trim());
	}

	public void chooseFileStampEnvelope(ActionEvent event) {
		chooseFile(textFieldStampEnvelope);
	}

	public void viewFileStampEnvelope(ActionEvent event) {
		viewFile(textFieldStampEnvelope.getText().trim());
	}

	public void chooseFileStampStamp(ActionEvent event) {
		chooseFile(textFieldStampStamp);
	}

	public void viewFileStampStamp(ActionEvent event) {
		viewFile(textFieldStampStamp.getText().trim());
	}

	public void chooseFileStampPublicKeyA(ActionEvent event) {
		chooseFile(textFieldStampPublicKeyA);
	}

	public void viewFileStampPublicKeyA(ActionEvent event) {
		viewFile(textFieldStampPublicKeyA.getText().trim());
	}

	public void chooseFileStampPrivateKeyB(ActionEvent event) {
		chooseFile(textFieldStampPrivateKeyB);
	}

	public void viewFileStampPrivateKeyB(ActionEvent event) {
		viewFile(textFieldStampPrivateKeyB.getText().trim());
	}

	public void chooseFileStampOutput(ActionEvent event) {
		chooseFile(textFieldStampOutput);
	}

	public void viewFileStampOutput(ActionEvent event) {
		viewFile(textFieldStampOutput.getText().trim());
	}

	public void generateSymmetricKey(ActionEvent event) {
		String keyFilePath = textFieldAESKey.getText().trim();
		int keySize = comboBoxAESKeySize.getValue();

		try {
			cryptoManager.generateSymmetricKey(keyFilePath, keySize);
			showAlert(Alert.AlertType.INFORMATION, "Key successfully generated!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, e.getMessage());
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
			showAlert(Alert.AlertType.ERROR, e.getMessage());
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
			showAlert(Alert.AlertType.ERROR, e.getMessage());
		}
	}

	public void generateAsymmetricKeys(ActionEvent event) {
		String publicKeyFilePath = textFieldRSAPublicKey.getText().trim();
		String privateKeyFilePath = textFieldRSAPrivateKey.getText().trim();
		int keySize = comboBoxRSAKeySize.getValue();

		try {
			cryptoManager.generateAsymmetricKeys(publicKeyFilePath, privateKeyFilePath, keySize);
			showAlert(Alert.AlertType.INFORMATION, "Public and private keys successfully generated!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, e.getMessage());
		}
	}

	public void encryptFileRSA(ActionEvent event) {
		String inputFilePath = textFieldRSAInput.getText().trim();
		String outputFilePath = textFieldRSAOutput.getText().trim();
		String keyFilePath = textFieldRSAPublicKey.getText().trim();

		try {
			cryptoManager.encryptFileAsymmetric(inputFilePath, outputFilePath, keyFilePath);
			showAlert(Alert.AlertType.INFORMATION, "File successfully encrypted!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, "Error occured during the file encryption!");
		}
	}

	public void decryptFileRSA(ActionEvent event) {
		String inputFilePath = textFieldRSAInput.getText().trim();
		String outputFilePath = textFieldRSAOutput.getText().trim();
		String keyFilePath = textFieldRSAPrivateKey.getText().trim();

		try {
			cryptoManager.decryptFileAsymmetric(inputFilePath, outputFilePath, keyFilePath);
			showAlert(Alert.AlertType.INFORMATION, "File successfully decrypted!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, "Error occured during the file decryption!");
		}
	}

	public void calculateHash(ActionEvent event) {
		String inputFilePath = textFieldSHAInput.getText().trim();
		String outputFilePath = textFieldSHAOutput.getText().trim();

		try {
			String hash = cryptoManager.calculateHash(inputFilePath, outputFilePath);
			textFieldSHAHash.setText(hash);
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		}
	}

	public void createEnvelope(ActionEvent event) {
		String inputFilePath = textFieldEnvelopeInput.getText().trim();
		String keyFilePath = textFieldEnvelopePublicKey.getText().trim();
		String envelopeFilePath = textFieldEnvelopeEnvelope.getText().trim();

		try {
			cryptoManager.createEnvelope(inputFilePath, keyFilePath, envelopeFilePath);
			showAlert(Alert.AlertType.INFORMATION, "Digital envelope successfully created!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, e.getMessage());
		}
	}

	public void openEnvelope(ActionEvent event) {
		String envelopeFilePath = textFieldEnvelopeEnvelope.getText().trim();
		String keyFilePath = textFieldEnvelopePrivateKey.getText().trim();
		String outputFilePath = textFieldEnvelopeOutput.getText().trim();

		try {
			cryptoManager.openEnvelope(envelopeFilePath, keyFilePath, outputFilePath);
			showAlert(Alert.AlertType.INFORMATION, "Digital envelope successfully opened!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, e.getMessage());
		}
	}

	public void createSignature(ActionEvent event) {
		String inputFilePath = textFieldSignatureInput.getText().trim();
		String keyFilePath = textFieldSignaturePrivateKey.getText().trim();
		String signatureFilePath = textFieldSignatureSignature.getText().trim();

		try {
			cryptoManager.createSignature(inputFilePath, keyFilePath, signatureFilePath);
			showAlert(Alert.AlertType.INFORMATION, "Digital signature successfully created!");
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, e.getMessage());
		}
	}

	public void verifySignature(ActionEvent event) {
		String inputFilePath = textFieldSignatureInput.getText().trim();
		String signatureFilePath = textFieldSignatureSignature.getText().trim();
		String keyFilePath = textFieldSignaturePublicKey.getText().trim();

		try {
			boolean isMatch = cryptoManager.verifySignature(inputFilePath, signatureFilePath, keyFilePath);
			String message = "Calculated values " + (isMatch ? "do" : "do not") + " match!";
			showAlert(Alert.AlertType.INFORMATION, message);
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "IO error occured!");
		} catch (CryptoException e) {
			showAlert(Alert.AlertType.ERROR, e.getMessage());
		}
	}

	public void createStamp(ActionEvent event) {
		String inputFilePath = textFieldStampInput.getText().trim();
		String publicKeyBFilePath = textFieldStampPublicKeyB.getText().trim();
		String privateKeyAFilePath = textFieldStampPrivateKeyA.getText().trim();
		String envelopeFilePath = textFieldStampEnvelope.getText().trim();
		String stampFilePath = textFieldStampStamp.getText().trim();
	}

	public void openStamp(ActionEvent event) {

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
