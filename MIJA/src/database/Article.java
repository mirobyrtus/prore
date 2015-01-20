package database;

import java.util.ArrayList;

import parsers.SimpleSentence;

public class Article {

	private String name;
	String path; // For matching with the directory
	
	/**
	 * Store List of sentences in one Article
	 */
	private ArrayList<SimpleSentence> sentences; // TODO back to ParsedSentence

	public Article(String name, String path) {
		this.name = name; 
		this.path = path;
		//sentences = new ArrayList<ParsedSentence>();
		sentences = new ArrayList<SimpleSentence>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addNewSentence(ArrayList<String> alternativeSentences) {
		// sentences.add(new ParsedSentence(alternativeSentences));
		sentences.add(new SimpleSentence(alternativeSentences));
	} 
	
	public ArrayList<SimpleSentence> getSentences() {
		return sentences;
	}
	
	@Override
	public String toString() {
		return sentences.toString();
	}
	
}
