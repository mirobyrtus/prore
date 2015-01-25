package com.prore.mija.database;

import java.io.Serializable;
import java.util.ArrayList;

import com.prore.mija.parsers.SimpleSentence;


public class Article implements Serializable {

	private static final long serialVersionUID = 6960103713016751703L;

	private String name;
	private String path; // For matching with the directory
	
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
	
	public String getPath() {
		return path;
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
