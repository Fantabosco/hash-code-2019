package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	private FileUtils() {
	}
	
	public static List<String> readFile(String fileName) {
		List<String> output = new ArrayList<>();
		BufferedReader br = null;
		InputStream is = null;
		try {
			is = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
			if(is == null) {
				throw new IllegalArgumentException("File not found" + fileName);
			}
			br = new BufferedReader(new InputStreamReader(is));

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				output.add(sCurrentLine);
			}
		} catch (NullPointerException e) {
			System.err.println("File not found in classpath: " + fileName);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return output;
	}
	
	public static void writeFile(String fileName, String solution) {
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			String outputFile = fileName.replace(".txt", ".out.txt");
			fileWriter = new FileWriter(outputFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.append(solution);
			System.out.println("Soluzion wrote to: " + outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
