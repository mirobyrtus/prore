package database;

import java.util.ArrayList;

public class Article {

	private String name;
	String path; // For matching with the directory
	
	/**
	 * Store List of sentences in one Article
	 */
	private ArrayList<ParsedSentence> sentences;

	public Article(String name, String path) {
		this.name = name; 
		this.path = path;
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
