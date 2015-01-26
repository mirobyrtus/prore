package com.prore.mija.parsers;

import java.io.Serializable;
import java.util.ArrayList;

public class SimpleSentence implements Serializable {

	private static final long serialVersionUID = 5018689251633220645L;

	public String sentence;
	public boolean important;
	
	public SimpleSentence(String newSentence, boolean isImportant) {
		sentence = newSentence;
		important = isImportant;
	}

	public SimpleSentence(ArrayList<String> alternativeSentences, boolean isImportant) {
		if (! alternativeSentences.isEmpty()) {
			sentence = alternativeSentences.get(0);
		}
		important = isImportant;
	}
	
	public boolean isImportant() {
		return important;
	}
	
	@Override
	public String toString() {
		return sentence;
	}
	
}
