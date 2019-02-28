package it.reply.challenge.fantabosco.hashcode;

import java.util.ArrayList;
import java.util.List;

import it.reply.challenge.fantabosco.hashcode.model.Photo;
import it.reply.challenge.fantabosco.hashcode.model.Slide;
import utils.FileUtils;

public class Main {

	// Constants
	private static final String DATASET_A = "a_example.txt";
	private static final String DATASET_B = "b_lovely_landscapes.txt";
	private static final String DATASET_C = "c_memorable_moments.txt";
	private static final String DATASET_D = "d_pet_pictures.txt";
	private static final String DATASET_E = "e_shiny_selfies.txt";

	// Model
	private static List<Photo> model;
	private static List<Slide> solution;

	public static void main(String[] args) {

		// Reader
		String dataset = DATASET_A;
		List<String> file = FileUtils.readFile("challenge/" + dataset);
		
		// Parser
		int index = 0;
		int numPhoto = Integer.parseInt(file.remove(0));
		model = new ArrayList<>(numPhoto);
		for(String line : file) {
			String[] values = line.split(" ");
			//H 3 cat beach sun
			Photo p = new Photo();
			p.isHorizontal = "H".equals(values[0]);
			int numTags = Integer.parseInt(values[1]);
			p.tags = new ArrayList<>(numTags);
			for(int i = 2; i < numTags; i++) {
				p.tags.add(values[i]);
			}
			p.index = index;
			index++;
		}
		
		
		// Solver
		solution = new ArrayList<>();
		//TODO
		
		
		// Serializer & validator
		StringBuilder solution = new StringBuilder();
		//TODO
		
		
		// Writer
		FileUtils.writeFile(dataset, solution.toString());
	}
	
	
}
