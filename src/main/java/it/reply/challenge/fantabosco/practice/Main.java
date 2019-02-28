package it.reply.challenge.fantabosco.practice;

import java.util.ArrayList;
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
		List<String> file = FileUtils.readFile("practice/" + dataset);

		// Parser
		int R, C, L, H;
		String[] firstLineValues = file.remove(0).split(" ");
		R = Integer.valueOf(firstLineValues[0]);
		C = Integer.valueOf(firstLineValues[1]);
		L = Integer.valueOf(firstLineValues[2]);
		H = Integer.valueOf(firstLineValues[3]);

		boolean[][] matrix = new boolean[R][C];
		int r = 0;
		for (String line : file) {
			int c = 0;
			for (char cell : line.toCharArray()) {
				boolean hasMushroom = false;
				switch (cell) {
				case 'M':
					hasMushroom = true;
					break;
				case 'T':
					break;
				default:
					System.err.println("Unexpected cell value: " + cell);
					System.exit(0);
				}
				matrix[r][c] = hasMushroom;
				c++;
			}
			r++;
		}

		// Solver
		List<List<Integer>> slices = new ArrayList<>();
		//TODO Cut slices
		// goal: maximize the total number of cells in all slices
		// constraint: 
		//	mushroom >= L
		//  tomato >= L
		//	cells <= H
		
		// Percorri la matrice
		// Ipotizza una fetta, e comincia a espandere in verticale e orizzontale, o entrambi
		//	
		// Quanto hai raggiunto i vincoli, salva la fetta passa alla cella successiva
		// Quando non puoi espanderti e non hai raggiunto i vincoli, passa alla cella successiva
		// Ulteriore passaggio: puoi espandere ancora senza superare H? se puoi prendi
		for(int i = 0; i< matrix.length; i++) {
			for(int j = 0; j< matrix[i].length; j++) {
				int oriz = 1;
				int vert = 1;
				while((oriz-j)*(vert-i) < H) {
					// Check if T >= L, and M >= L
					if(!checkValidIngr(i, j, oriz, vert, matrix)) {
						break;
					}
					
					// Find out the best direction to expand
					int scoreOriz = checkScore(i, j, oriz+1, vert, L, H, matrix, slices);
					int scoreVert = checkScore(i, j, oriz, vert+1, L, H, matrix, slices);
					
				
					if(scoreOriz < scoreVert) {
						oriz++;
					} else {
						vert++;
					}
					
					if(scoreOriz <= 0 && scoreVert <= 0) {
						break;
					}
				}
			}
		}
		
		// Validator
		//TODO For every slice:
		//	mushroom >= L
		//  tomato >= L
		//	cells <= H
		//TODO for every slice: no overlap with others
		
		// Serializer
		

	}

	private static boolean checkValidIngr(int i, int j, int oriz, int vert, boolean[][] matrix) {
		// TODO Auto-generated method stub
		return false;
	}

	private static int checkScore(int i, int j, int oriz, int vert, int L, int h, boolean[][] matrix, List<List<Integer>> slices) {
		//euristica: espandi verso il costo minimo
		// 	costo (ipotizzando di espandersi)
		// 		valorizza l'avvicinamento di T a L
		// 		valorizza l'avvicinamento di M a L
		// 		penalizza l'avvicinamento delle celle totali a H
		// return <=0 se ho raggiunto gli obiettivi
		return 0;
	}
}
