package it.reply.challenge.fantabosco.hashcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.reply.challenge.fantabosco.hashcode.model.Photo;
import it.reply.challenge.fantabosco.hashcode.model.Slide;

public class GiovaSolver {

	public static List<Slide> solve(List<Photo> model) {


		
		
		return null;
	}
	
	private static int score(Slide s1, Slide s2) {
		Set<String> tags1 = getTags(s1.photo1, s1.photo2);
		Set<String> tags2 = getTags(s2.photo1, s2.photo2);
		
		List<String> commons = tags1.stream().filter(tags2::contains).collect(Collectors.toList());

		/*
		 * Fattore di interesse: il minimo fra:
		 *  - Il numero di tag in comune
		 *  - Il numero di tag unici
		 */
		int commonTags = commons.size();
		
		int totTags = commons.size();
		Set<String> tags1Copy = new HashSet<>(tags1);
		tags1Copy.removeAll(tags2);
		int only1 = tags1Copy.size();
	
		tags2.removeAll(tags1);
		int only2 = tags2.size();

		
		return 0;
	}
	
	private static int scoreSlideshow(List<Slide> solution) {
		if(solution.size() <= 1) {
			return 0;
		}
		int score = 0;
		for(int i = 0; i<solution.size() - 1; i++) {
			score += score(solution.get(i), solution.get(i + 1));
		}
		return score;
	}

	private static Set<String> getTags(Photo p1, Photo p2) {
		Set<String> tags = new HashSet<>();
		tags.addAll(p1.tags);
		if(p2 != null) {
			tags.addAll(p2.tags);
		}
		return tags;
	}
	
}
