package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Solution {

	public ArrayList<String> readInputData(String inputFile) {
		List<String> allLines = null;
		try {
			allLines = Files.readAllLines(Paths.get(inputFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (ArrayList<String>) allLines;
	}

	public HashMap<String, Integer> parseInputData(ArrayList<String> dataLines) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 4; i < dataLines.size(); i++) {
			String line = dataLines.get(i);
			String key = line.substring(0, line.indexOf(":"));
			int value = Integer.parseInt(line.substring(line.indexOf(":") + 2, line.length()));
			map.put(key, value);
		}
		return map;
	}

	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hashMap) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hashMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		HashMap<String, Integer> tempHashMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			tempHashMap.put(aa.getKey(), aa.getValue());
		}

		return tempHashMap;
	}

	public int[] divideGoddies(int n, HashMap<String, Integer> map) {
		int startIndex = 0;
		int endIndex = n - 1;
		ArrayList<Integer> values = new ArrayList<Integer>(map.values());
		int minimum = values.get(endIndex) - values.get(startIndex);
		int i = startIndex;
		while (i < values.size() - 1 && endIndex + i < values.size()) {
			int difference = values.get(endIndex + i) - values.get(startIndex + i);
			if (minimum > difference) {
				minimum = difference;
				endIndex = endIndex + i;
				startIndex = startIndex + i;
			}
			i++;
		}
		return new int[] { startIndex, endIndex };
	}

	public static void main(String[] args) throws IOException {
		String inputFile = "D:\\git-repo\\DataStructure\\src\\common\\sample_input.txt";
		String outputFile = "D:\\git-repo\\DataStructure\\src\\common\\output_sample.txt";
		BufferedWriter writer = new BufferedWriter(
				new FileWriter(outputFile));
		Solution obj = new Solution();
		ArrayList<String> inputData = obj.readInputData(inputFile);
		HashMap<String, Integer> parseInputData = obj.parseInputData(inputData);
		HashMap<String, Integer> sortByValue = sortByValue(parseInputData);

		String str = inputData.get(0);
		int noOfEmployee = Integer.parseInt(str.substring(str.indexOf(":") + 2, str.length()));
		int[] divideGoddies = obj.divideGoddies(noOfEmployee, sortByValue);

		Object[] array = sortByValue.keySet().toArray();
		writer.write("The goodies selected for distribution are: \n");

		for (int i = divideGoddies[0]; i <= divideGoddies[1]; i++) {
			writer.write(array[i] + ": " + sortByValue.get(array[i]) + "\n");
		}
		int x = sortByValue.get(array[divideGoddies[1]]) - sortByValue.get(array[divideGoddies[0]]);
		writer.write("And the difference between the chosen goodie with highest price and the lowest price is " + x);
		writer.close();

	}
}
