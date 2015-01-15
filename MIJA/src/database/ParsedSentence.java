package database;

import java.util.ArrayList;

import parsers.TextParser;

public class ParsedSentence {

	String[][][] parsed_text;

	public ParsedSentence(ArrayList<String> alternatives) {
		parsed_text = TextParser.parseTextDummy(); 
		// TODO somewhere out there it was alrerady implemented by myself: TextParser.parseText(alternatives);
		// TextParser.parseText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");		
		
		// TODO gridView.setAdapter(new WordsAdapter(this, parsed_text));
 
	}
	
	@Override
	public String toString() {
		// Simple show
		String sentence = "";
		String[][] parsed_sentence = parsed_text[0];
		for (String[] word : parsed_sentence) {
			sentence += word[0];
		}
		return sentence;
	}
	
}
