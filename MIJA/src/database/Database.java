package database;

import java.util.ArrayList;

import android.util.Log;

public class Database {

	/**
	 * Hold a database of Articles (For one run only!)
	 */
	public static ArrayList<Article> articleDatabase = new ArrayList<Article>();
	
	public static void createNewArticle() {
		articleDatabase.add(new Article("Article-" + articleDatabase.size()));
	}
	
	public static void addSentenceToArticle(int articleId, ArrayList<String> alternativeSentences) {
		if (articleDatabase.isEmpty()) {
			createNewArticle();
		} else if (articleId > articleDatabase.size()) {
			Log.e("Database", "Index " + articleId + " out of bounds, nothing added to DB!");
			return;
		}
		
		articleDatabase.get(articleId).addNewSentence(alternativeSentences);	
	}
	
	public static Article getArticle(int id) {
		if (id >= articleDatabase.size()) return null;
		return articleDatabase.get(id);
	}
	
}
