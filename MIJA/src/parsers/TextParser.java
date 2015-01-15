package parsers;

import java.util.ArrayList;

public class TextParser {

	/**
	 * TODO Send structure from STT
	 * @param text
	 * @return
	 */
	public static String[][][] parseText(String text) {
		String[] sentences = text.split("\\.");
		String[][][] result = new String[sentences.length][][];
		
		for (int i = 0; i < sentences.length; i++) {
			
			String[] words = sentences[i].split(" ");
			result[i] = new String[words.length][2]; // 2 Because we dont have any alternatives, the second one will be just "XXX" for testing
			
			for (int j = 0; j < words.length; j++) {
				result[i][j] = new String[] { words[j], "XXX" }; 
			}
			
		}
		
		return result; 
	}

	public static String[][][] parseSentences(ArrayList<Sentence> sentences) {
		String[][][] result = new String[sentences.size()][][];
		
		for (int i = 0; i < sentences.size(); i++) {
			Sentence s = sentences.get(i);
			Word[] ws = s.getWords(); 

			result[i] = new String[ws.length][];
			for (int w = 0; w < ws.length; w++) {
				ArrayList<String> alternatives = ws[w].getAlternatives(); 
				result[i][w] = new String[alternatives.size()]; 
				
				for (int a = 0; a < alternatives.size(); a++) {
					result[i][w][a] = alternatives.get(a);
				}
			} 
		}
		
		return result; 
	}
	
	public static String[][][] parseTextDummy() {
		String [][][] result = new String[][][] { 
				{	
					{ "Oh", "Oi", "Ou", }, 
					{ "Mighty", "Might", "Nightly", }, 
					{ "Computer", "Commuter", "Concluder", },
				}, 
				{
					{ "How", "Hawk", "Ho", }, 
					{ "Are", "R", }, 
					{ "You", "Your", "U", },	
				}
			};
		return result; 
	}
	
}
