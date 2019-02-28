package it.reply.challenge.fantabosco.hashcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import utils.FileUtils;

public class Main {

	private static final String DATASET_A = "a_example.in";
	private static final String DATASET_B = "b_small.in";
	private static final String DATASET_C = "c_medium.in";
	private static final String DATASET_D = "d_big.in";

	public static void main(String[] args) {

		// Reader
		String dataset = DATASET_A;
		List<String> file = FileUtils.readFile("challenge/" + dataset);
		
		
		// Parser
		//TODO
		
		
		// Solver
		//TODO
		
		
		// Serializer & validator
		StringBuilder solution = new StringBuilder();
		//TODO
		
		
		// Writer
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			String outputFile = dataset.replace(".in", ".out");
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
