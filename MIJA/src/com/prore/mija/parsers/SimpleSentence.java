package com.prore.mija.parsers;

import java.io.Serializable;
import java.util.ArrayList;

public class SimpleSentence implements Serializable {

	private static final long serialVersionUID = 5018689251633220645L;

	public String sentence;
	
	public SimpleSentence(String newSentence) {
		sentence = newSentence;
	}

	public SimpleSentence(ArrayList<String> alternativeSentences) {
		if (! alternativeSentences.isEmpty()) {
			sentence = alternativeSentences.get(0);
		} 
	}
	
	@Override
	public String toString() {
		return sentence;
	}
	
}
