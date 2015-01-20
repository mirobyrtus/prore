package database;

import java.util.ArrayList;

import android.util.Log;

public class Database {

	/**
	 * Hold a database of Articles (For one run only!)
	 */
	public ArrayList<Article> articles = new ArrayList<Article>();
	
	public ArrayList<Article> getArticles() {
		return articles;
	}
	
	public Article createNewArticle(String path) {
		Article newArticle = new Article("Article-" + articles.size(), path); 
		articles.add(newArticle);
		return newArticle; 
	}
	
	public void addSentenceToArticle(String articlePath, ArrayList<String> alternativeSentences) {
		Article article = null; 
		for (Article a : articles) {
			if (a.path.equals(articlePath)) {
				article = a; 
				break;
			}
		}
		
		if (article == null) {
			article = createNewArticle(articlePath);
		}
		
		article.addNewSentence(alternativeSentences);	
	}
	
	public Article getArticle(int id) {
		if (id >= articles.size()) return null;
		return articles.get(id);
	}
	
}
