package utils;

import java.util.ArrayList;
import java.util.List;

import it.reply.challenge.fantabosco.hashcode.model.Photo;
import it.reply.challenge.fantabosco.hashcode.model.Slide;

public class GreedySolution {

	// greedy
	public static void solve(List<Photo> model, List<Slide> solution) {
		Slide lastVertical = new Slide();
		for (Photo p : model) {
			if (p.isHorizontal) {
				Slide s = new Slide();
				s.photo1 = p;
				solution.add(s);
			} else {
				if (lastVertical.photo1 == null) {
					lastVertical.photo1 = p;
				} else if (lastVertical.photo2 == null) {
					lastVertical.photo2 = p;
					solution.add(lastVertical);
					lastVertical = new Slide();
				}
			}
		}
	}
	
	// almeno un tag uguale rispetto alla slide precedente
	public static void solve1(List<Photo> model, List<Slide> solution) {
		int solutionSize = 0;
		int iteration = 0;
		int remainingPhotos = model.size();
		Slide lastVertical = new Slide();
		do {
			for (Photo p : model) {
				if (p.isUsedInSlideshow) {
					continue;
				}
				boolean pToBeUsed = false;
				if (solutionSize > 0) {
					List<String> previousSlideTags = new ArrayList<>();
					if (solution.get(solutionSize - 1).photo1 != null) {
						previousSlideTags.addAll(solution.get(solutionSize - 1).photo1.tags);
					}
					if (solution.get(solutionSize - 1).photo2 != null) {
						previousSlideTags.addAll(solution.get(solutionSize - 1).photo2.tags);
					}
					for (String tag : p.tags) {
						if (previousSlideTags.contains(tag)) {
							pToBeUsed = true;
							break;
						}
					}
				}
				if (solutionSize == 0 || pToBeUsed) {
					if (p.isHorizontal) {
						Slide s = new Slide();
						s.photo1 = p;
						p.isUsedInSlideshow = true;
						solution.add(s);
						solutionSize++;
						remainingPhotos--;
					} else {
						if (lastVertical.photo1 == null) {
							lastVertical.photo1 = p;
							p.isUsedInSlideshow = true;
							remainingPhotos--;
						} else if (lastVertical.photo2 == null) {
							lastVertical.photo2 = p;
							p.isUsedInSlideshow = true;
							solution.add(lastVertical);
							solutionSize++;
							remainingPhotos--;
							lastVertical = new Slide();
						}
					}
				}
			}
			iteration++;
		} while (remainingPhotos > 0 && iteration <= 10);
		
		// aggiungo tutte le foto non utilizzate
		for (Photo p : model) {
			if (p.isUsedInSlideshow) {
				continue;
			}
			if (p.isHorizontal) {
				Slide s = new Slide();
				s.photo1 = p;
				solution.add(s);
			} else {
				if (lastVertical.photo1 == null) {
					lastVertical.photo1 = p;
				} else if (lastVertical.photo2 == null) {
					lastVertical.photo2 = p;
					solution.add(lastVertical);
					lastVertical = new Slide();
				}
			}
		}
	}

}
