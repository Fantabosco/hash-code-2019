package utils;

import java.util.List;

import it.reply.challenge.fantabosco.hashcode.model.Photo;
import it.reply.challenge.fantabosco.hashcode.model.Slide;

public class GreedySolution {

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

}
