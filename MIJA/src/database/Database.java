package database;

import java.io.Serializable;
import java.util.ArrayList;

public class Database implements Serializable {

	private static final long serialVersionUID = 6266600293444189506L;
	
	/**
	 * Hold a database of Articles (For one run only!)
	 */
	public ArrayList<Article> articles = new ArrayList<Article>();
	
	public ArrayList<Article> getArticles() {
		return articles;
	}
	
	public Article createNewArticle(String path) {
		Article newArticle = new Article("Title-" + articles.size(), path); 
		articles.add(newArticle);
		return newArticle; 
	}
	
	public void addSentenceToArticle(String articlePath, ArrayList<String> alternativeSentences) {
		Article article = null; 
		for (Article a : articles) {
			if (a.getPath().equals(articlePath)) {
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
	
	public int getActualArticleId() {
		return articles.size();
	}
	
}
