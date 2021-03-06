package com.prore.mija;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.mija.R;
import com.prore.mija.database.Article;
import com.prore.mija.dragndrop.DynamicListView;
import com.prore.mija.dragndrop.StableArrayAdapter;
import com.prore.mija.parsers.SimpleSentence;

public class ArticleFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//View rootView = inflater
		//		.inflate(R.layout.article_tab, container, false);
		
		LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.article_tab, container, false);
		
		ArrayList<Article> articles = ((Startscreen)getActivity()).getDatabase().getArticles();
		if (! articles.isEmpty()) { 
			Article latestArticle = articles.get(articles.size() - 1);
			
			String title = latestArticle.getName();
			EditText articleTitle = (EditText) rootView.findViewById(R.id.articleTitle); 
			articleTitle.setText(title);
			
			ArrayList<SimpleSentence> simpleSentences = latestArticle.getSentences();
			
			// DragNDrop
			ArrayList<String> sentences = new ArrayList<String>(); 
			for (SimpleSentence ss : simpleSentences) {
				// Filter only important sentences
				if (ss.isImportant()) {
					sentences.add(ss.toString());	
				}
			} 
	
	        StableArrayAdapter adapter = new StableArrayAdapter(getActivity(), R.layout.sentence, R.id.sentenceTextViewId, sentences);
	        
	        ((Startscreen)getActivity()).listView = (DynamicListView) rootView.findViewById(R.id.dragndroplistview);
	        ((Startscreen)getActivity()).listView.setCheeseList(sentences);
	        ((Startscreen)getActivity()).listView.setAdapter(adapter);
	        ((Startscreen)getActivity()).listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	        ((Startscreen)getActivity()).listView.setActivity((Startscreen)getActivity());
		}

		/**
		 * final Button addButton = (Button) getView().findViewById(
		 * R.id.add_button); addButton.setOnClickListener(new
		 * View.OnClickListener() { public void onClick(View v) { // Perform
		 * action on click //TODO } });
		 * 
		 * final Button deleteButton = (Button) getView().findViewById(
		 * R.id.delete_button); deleteButton.setOnClickListener(new
		 * View.OnClickListener() { public void onClick(View v) { // Perform
		 * action on click //TODO } });
		 * 
		 * final Button emailButton = (Button) getView().findViewById(
		 * R.id.email_button); emailButton.setOnClickListener(new
		 * View.OnClickListener() { public void onClick(View v) { // Perform
		 * action on click //TODO } });
		 */

		return rootView;
	}
	
}
