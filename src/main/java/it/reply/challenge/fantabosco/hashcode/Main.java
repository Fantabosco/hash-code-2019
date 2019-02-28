package it.reply.challenge.fantabosco.hashcode;

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
		//TODO
		
	}

}
