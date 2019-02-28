package it.reply.challenge.fantabosco.hashcode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.FileUtils;

public class Main {

	// Constants
	private static final String DATASET_A = "a_example.in";
	private static final String DATASET_B = "b_small.in";
	private static final String DATASET_C = "c_medium.in";
	private static final String DATASET_D = "d_big.in";
	
	// Model
	private static Map<String,String> model = new HashMap<>();
	
	

	public static void main(String[] args) {

		// Reader
		String dataset = DATASET_A;
		List<String> file = FileUtils.readFile("challenge/" + dataset);
		for(String line : file) {
			
		}
		
		// Parser
		//TODO
		
		
		// Solver
		//TODO
		
		
		// Serializer & validator
		StringBuilder solution = new StringBuilder();
		//TODO
		
		
		// Writer
		FileUtils.writeFile(dataset, solution.toString());
	}
	
	
}
