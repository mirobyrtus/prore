package database;

import java.util.ArrayList;

public class Article {

	private String name;
	
	/**
	 * Store List of sentences in one Article
	 */
	private ArrayList<ParsedSentence> sentences;

	public Article(String name) {
		this.name = name; 
		sentences = new ArrayList<ParsedSentence>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addNewSentence(ArrayList<String> alternativeSentences) {
		sentences.add(new ParsedSentence(alternativeSentences));
	} 
	
	public ArrayList<ParsedSentence> getSentences() {
		return sentences;
	}
	
}
