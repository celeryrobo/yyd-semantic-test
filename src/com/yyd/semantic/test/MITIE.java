package com.yyd.semantic.test;

import edu.mit.ll.mitie.SDPair;
import edu.mit.ll.mitie.StringVector;
import edu.mit.ll.mitie.TextCategorizer;
import edu.mit.ll.mitie.TotalWordFeatureExtractor;

public class MITIE {
	private TextCategorizer textCategorizer;
	private TotalWordFeatureExtractor fe;

	public MITIE() {
		String path = "C:/Users/hongxinzhao/Desktop/owl/";
		String fe_filename = path + "total_word_feature_extractor.dat";
		String cat_filename = path + "category_model.dat";
		System.out.println(fe_filename);
		System.out.println(cat_filename);
		fe = new TotalWordFeatureExtractor(fe_filename);
		textCategorizer = new TextCategorizer(cat_filename);
	}

	public Category cat(String lang) {
		StringVector sv = new StringVector();
		for (int i = 0; i < lang.length(); i++) {
			sv.add(lang.substring(i, i + 1));
		}
		// SDPair pair = textCategorizer.categorizeDoc(sv);
		SDPair pair = textCategorizer.categorizeDoc(sv, fe);
		return new Category(pair.getFirst(), pair.getSecond());
	}
	
	

	public static class Category {
		private String name;
		private double score;

		private Category(String name, double score) {
			this.name = name;
			this.score = score;
		}

		public String getName() {
			return name;
		}

		public double getScore() {
			return score;
		}

		@Override
		public String toString() {
			return "Sentence:" + getName() + " Score:" + getScore();
		}
	}
}
