package parsers;

import java.util.ArrayList;

public class SimpleSentence {

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
