package it.reply.challenge.fantabosco.hashcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import it.reply.challenge.fantabosco.hashcode.model.Photo;
import it.reply.challenge.fantabosco.hashcode.model.Slide;

public class GiovaSolver {

	private GiovaSolver() {
	}

	private static final int MAX_CYCLES = 30;

	public static List<Slide> solve(List<Photo> model) {

		List<Slide> slideShow1 = heuristic1(new ArrayList<>(model));
		int score1 = scoreSlideshow(slideShow1);
		System.out.println("heuristic1 Solution score: " + score1);

		List<Slide> slideShow2 = heuristic2(new ArrayList<>(model));
		int score2 = scoreSlideshow(slideShow2);
		System.out.println("heuristic2 Solution score: " + score2);

		List<Slide> slideShow3 = heuristic3(slideShow2);
		int score3 = scoreSlideshow(slideShow3);
		System.out.println("heuristic3 Solution score: " + score3);

		return score1 > score2 ? slideShow1 : slideShow2;
	}
	
	private static final Random random = new Random();

	/**
	 * Riordina uno slideShow cercando di migliorare il puntaggio della sequenza di slide
	 */
	private static List<Slide> heuristic3(List<Slide> slideShow) {
		// Insertion sort
		List<Slide> solution = new LinkedList<>();
		solution.add(slideShow.get(0));
		solution.add(slideShow.get(1));
		for(int i=2; i<slideShow.size(); i++) {
			int cycle = 0;
			
			// Min value is this Slide put at the end
			int maxScore = score(solution.get(solution.size() - 1), slideShow.get(i));
			int maxScoreJ = solution.size();
			
			int randomStart = random.nextInt(solution.size());
			for(int j=0; j < solution.size() && cycle < MAX_CYCLES; j++) {
				// Pick a random start
				int jr = randomStart + j;
				if(jr >= solution.size() - 2) {
					jr = 0;
					randomStart = -j;
				}
				// Current score
				int p1 = score(solution.get(jr), solution.get(jr + 1));
				
				// New scores
				int pNew = score(solution.get(jr), slideShow.get(i));
				
				// Is the new score better that current score and best score?
				if(pNew > p1 && pNew > maxScore) {
					maxScore = pNew;
					maxScoreJ = jr;
				}
				cycle ++;
			}
			// Split the list and insert this slide
			List<Slide> list1 = solution.subList(0, maxScoreJ);
			List<Slide> list2 = solution.subList(maxScoreJ, solution.size());
			solution = new LinkedList<>();
			solution.addAll(list1);
			solution.add(slideShow.get(i));
			solution.addAll(list2);
		}
		return solution;
	}

	/**
	 * Associa le foto verticali massimizzando il punteggio rispetto alla slide precedente
	 */
	private static List<Slide> heuristic2(List<Photo> model) {
		List<Slide> slideShow = new ArrayList<>();
		Iterator<Photo> iter = model.iterator();
		Slide sPrev = null;
		while (iter.hasNext()) {
			Photo pNew = iter.next();
			if (pNew.isUsedInSlideshow) {
				continue;
			}
			Slide sNew = new Slide();
			sNew.photo1 = pNew;

			// For V, find best match
			if (!sNew.photo1.isHorizontal) {
				int numDeltaMax = 0;
				Photo pMaxTags = null;
				Iterator<Photo> iter2 = model.iterator();
				int cycle = 0;
				while (iter2.hasNext() && cycle < MAX_CYCLES) {
					Photo pNew2 = iter2.next();
					if (!pNew2.isHorizontal && !pNew2.isUsedInSlideshow) {
						cycle++;
						Slide sTmp = new Slide();
						sTmp.photo1=pNew;
						sTmp.photo2=pNew2;
						int delta = score(sTmp, sPrev);
						if (delta >= numDeltaMax) {
							numDeltaMax = delta;
							pMaxTags = pNew2;
						}
					}
				}
				
				if (pMaxTags != null) {
					sNew.photo2 = pMaxTags;
					pMaxTags.isUsedInSlideshow = true;
				} else {
					// Cannot add this vertical photo
					continue;
				}
			}
			
			// Now you can add the slide
			slideShow.add(sNew);
			pNew.isUsedInSlideshow = true;
			sPrev = sNew;
		}
		return slideShow;
	}

	/**
	 * Associa alla prima foto possibile
	 */
	private static List<Slide> heuristic1(List<Photo> model) {
		List<Slide> slideShow = new ArrayList<>();
		Iterator<Photo> iter = model.iterator();
		while (iter.hasNext()) {
			Slide sLast = null;
			if (!slideShow.isEmpty()) {
				sLast = slideShow.get(slideShow.size() - 1);
			}
			Photo pNew = iter.next();
			if (sLast != null && !sLast.photo1.isHorizontal && sLast.photo2 == null) {
				if (!pNew.isHorizontal) {
					// Add this photo
					sLast.photo2 = pNew;
					iter.remove();
				} else {
					// Discard this photo
				}
			} else {
				// New slide
				Slide sNew = new Slide();
				sNew.photo1 = pNew;
				slideShow.add(sNew);
				iter.remove();
			}
		}
		return slideShow;
	}

	private static int score(Slide s1, Slide s2) {
		if(s2 == null) {
			return 0;
		}
		Set<String> tags1 = getTags(s1.photo1, s1.photo2);
		Set<String> tags2 = getTags(s2.photo1, s2.photo2);

		/*
		 * Fattore di interesse: il minimo fra: - Il numero di tag in comune - Il numero
		 * di tag unici
		 */
		List<String> commons = tags1.stream().filter(tags2::contains).collect(Collectors.toList());
		int totTags = commons.size();
		Set<String> tags1Copy = new HashSet<>(tags1);
		tags1Copy.removeAll(tags2);
		int only1 = tags1Copy.size();

		tags2.removeAll(tags1);
		int only2 = tags2.size();

		int min = only1 < only2 ? only1 : only2;
		min = min < totTags ? min : totTags;
		return min;
	}

	private static int scoreSlideshow(List<Slide> solution) {
		if (solution.size() <= 1) {
			return 0;
		}
		int score = 0;
		for (int i = 0; i < solution.size() - 1; i++) {
			score += score(solution.get(i), solution.get(i + 1));
		}
		return score;
	}

	private static Set<String> getTags(Photo p1, Photo p2) {
		Set<String> tags = new HashSet<>();
		tags.addAll(p1.tags);
		if (p2 != null) {
			tags.addAll(p2.tags);
		}
		return tags;
	}

}
