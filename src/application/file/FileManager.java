package application.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	private static final String BEGIN_CRYPTO_DATA = "---BEGIN OS2 CRYPTO DATA---";
	private static final String END_CRYPTO_DATA = "---END OS2 CRYPTO DATA---";

	public FileManager() {
	}

	public String readFile(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public List<Pair> readPropertiesFromFile(String filePath) throws IOException {
		List<Pair> properties = new ArrayList<>();

		List<String> lines = Files.readAllLines(Paths.get(filePath));
		int lineIndex = 0;

		// Find the beginning of the relevant data
		while (!lines.get(lineIndex).trim().equals(BEGIN_CRYPTO_DATA)) {
			lineIndex++;
		}

		// Skip the first line
		lineIndex++;

		// Read the data between begin and end marks
		while (!lines.get(lineIndex).trim().equals(END_CRYPTO_DATA)) {

			// Read the key and remove ':' from the last position
			String key = lines.get(lineIndex).trim();
			key = key.substring(0, key.length() - 1);

			Pair pair = new Pair(key);

			lineIndex++;

			// Read the values, it could be in more lines
			while (!lines.get(lineIndex).trim().isEmpty()) {
				pair.addValue(lines.get(lineIndex).trim());
				lineIndex++;
			}

			properties.add(pair);

			lineIndex++;

		}

		return properties;
	}

	public void writePropertiesToFile(List<Pair> properties, String filePath) throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append(BEGIN_CRYPTO_DATA + "\n");

		for (Pair pair : properties) {
			// Key
			sb.append(pair.getKey());
			sb.append(":\n");

			// Values
			for (String value : pair.getValues()) {
				sb.append("    ");
				sb.append(value);
				sb.append("\n");
			}

			// New line
			sb.append("\n");
		}

		sb.append(END_CRYPTO_DATA);

		File file = new File(filePath);
		file.createNewFile();

		try (FileOutputStream fos = new FileOutputStream(file, false)) {
			fos.write(sb.toString().getBytes());
		} catch (FileNotFoundException e) {
			// ignorable, file will be created if it doesn't exist
		}
	}

}
