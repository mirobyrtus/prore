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
	private ArrayList<SimpleSentence> sentences; // ParsedSentence

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
	
	public void addNewSentence(ArrayList<String> alternativeSentences, boolean importantSentence) {
		// sentences.add(new ParsedSentence(alternativeSentences));
		sentences.add(new SimpleSentence(alternativeSentences, importantSentence));
	} 
	
	public ArrayList<SimpleSentence> getSentences() {
		return sentences;
	}
	
	public SimpleSentence getSentence(int index) {
		if (index >= 0 && index < sentences.size()) {
			return sentences.get(index);
		}
		return null;
	}
	
	public int getSentencesCount() {
		return sentences.size();
	}
	
	public int filterImportantId(int id) {
		int filteredId = 0;
		int importants = 0;
		for (SimpleSentence ss : sentences) {
			if (ss.isImportant()) {
				importants++;
				if (importants > id) {
					return filteredId;
				}
			}
			filteredId++;
		}
		// Should not happen
		return filteredId - 1; // Or 0
	}
	
	@Override
	public String toString() {
		return sentences.toString();
	}
	
}
