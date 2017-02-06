package application.file;

import java.util.ArrayList;
import java.util.List;

public class Pair {

	private String key;
	private List<String> values = new ArrayList<>();

	public Pair(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public List<String> getValues() {
		return values;
	}

	public void addValue(String value) {
		values.add(value);
	}

}
