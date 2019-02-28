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
		int slideBeforeLastVertical = 0;
		Slide lastVertical = new Slide();
		do {
			for (Photo p : model) {
				if (p.isUsedInSlideshow) {
					continue;
				}
				boolean pToBeUsed = false;
				if (solutionSize > 0) {
					Slide last;
					if (!p.isHorizontal && lastVertical.photo1 != null) {
						last = solution.get(slideBeforeLastVertical);
					} else {
						last = solution.get(solutionSize - 1);
					}
					List<String> previousSlideTags = new ArrayList<>();
					if (last.photo1 != null) {
						previousSlideTags.addAll(last.photo1.tags);
					}
					if (last.photo2 != null) {
						previousSlideTags.addAll(last.photo2.tags);
					}
					int equalTags = 0;
					int differentTags = 0;
					for (String tag : p.tags) {
						if (previousSlideTags.contains(tag)) {
							equalTags++;
						} else {
							differentTags++;
						}
					}
					if (equalTags >= ((1000-iteration)/100)
							&& differentTags >= ((1000-iteration)/100)) {
						pToBeUsed = true;
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
							slideBeforeLastVertical = solutionSize - 1;
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
		} while (remainingPhotos > 0 && iteration <= 1000);
		
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
	
	public static void solve2(List<Photo> model, List<Slide> solution) {
		int solutionSize = 0;
		int iteration = 0;
		int remainingPhotos = model.size();
		int slideBeforeLastVertical = 0;
		Slide lastVertical = new Slide();
		do {
			for (Photo p : model) {
				Photo photo = p;
				if (p.isUsedInSlideshow) {
					continue;
				}
				boolean pToBeUsed = true;
				if (solutionSize > 0) {
					Slide last;
					if (!p.isHorizontal && lastVertical.photo1 != null && slideBeforeLastVertical != -1) {
						last = solution.get(slideBeforeLastVertical);
					} else {
						last = solution.get(solutionSize - 1);
					}
					List<String> previousSlideTags = new ArrayList<>();
					if (last.photo1 != null) {
						previousSlideTags.addAll(last.photo1.tags);
					}
					if (last.photo2 != null) {
						previousSlideTags.addAll(last.photo2.tags);
					}
					int equalTags = 0;
					int differentTags = 0;
					for (String tag : p.tags) {
						if (previousSlideTags.contains(tag)) {
							equalTags++;
						} else {
							differentTags++;
						}
					}
					int score = equalTags < differentTags ? equalTags : differentTags;
					// cerco se c'è un'altra foto con score più alto
					for (Photo ph : model) {
						if (ph.isUsedInSlideshow) {
							continue;
						}
						if (!ph.isHorizontal && lastVertical.photo1 != null && slideBeforeLastVertical != -1) {
							last = solution.get(slideBeforeLastVertical);
						} else {
							last = solution.get(solutionSize - 1);
						}
						previousSlideTags = new ArrayList<>();
						if (last.photo1 != null) {
							previousSlideTags.addAll(last.photo1.tags);
						}
						if (last.photo2 != null) {
							previousSlideTags.addAll(last.photo2.tags);
						}
						equalTags = 0;
						differentTags = 0;
						for (String tag : ph.tags) {
							if (previousSlideTags.contains(tag)) {
								equalTags++;
							} else {
								differentTags++;
							}
						}
						int newScore = equalTags < differentTags ? equalTags : differentTags;
						if (newScore > score) {
							photo = ph;
						}
					}
//					if (equalTags >= ((1000-iteration)/100)
//							&& differentTags >= ((1000-iteration)/100)) {
//						pToBeUsed = true;
//					}
				}
				if (solutionSize == 0 || pToBeUsed) {
					if (photo.isHorizontal) {
						Slide s = new Slide();
						s.photo1 = photo;
						photo.isUsedInSlideshow = true;
						solution.add(s);
						solutionSize++;
						remainingPhotos--;
					} else {
						if (lastVertical.photo1 == null) {
							slideBeforeLastVertical = solutionSize - 1;
							lastVertical.photo1 = photo;
							photo.isUsedInSlideshow = true;
							remainingPhotos--;
						} else if (lastVertical.photo2 == null) {
							lastVertical.photo2 = photo;
							photo.isUsedInSlideshow = true;
							solution.add(lastVertical);
							solutionSize++;
							remainingPhotos--;
							lastVertical = new Slide();
						}
					}
				}
			}
			iteration++;
		} while (remainingPhotos > 0 && iteration <= 1000);
		
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
