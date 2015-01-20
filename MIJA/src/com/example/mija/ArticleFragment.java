package com.example.mija;

import java.util.ArrayList;

import parsers.SimpleSentence;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import database.Article;
import dragndrop.DragNDropListView;
import dragndrop.DynamicListView;
import dragndrop.StableArrayAdapter;

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
			ArrayList<SimpleSentence> simpleSentences = latestArticle.getSentences();
			
			// DragNDrop
			ArrayList<String> sentences = new ArrayList<String>(); 
			for (SimpleSentence ss : simpleSentences) {
				sentences.add(ss.toString());
			} 
	
	        StableArrayAdapter adapter = new StableArrayAdapter(getActivity(), R.layout.sentence, sentences);
	        DynamicListView listView = (DynamicListView) rootView.findViewById(R.id.dragndroplistview);
	
	        listView.setCheeseList(sentences);
	        listView.setAdapter(adapter);
	        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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

	public void OnShareViaEmail(View v) {
		// Collect info
		StringBuffer article = new StringBuffer();

		dragndrop.DynamicListView dndview = (dragndrop.DynamicListView) v.findViewById(R.id.dragndroplistview);
		for (int i = 0; i < dndview.getChildCount(); i++) {
		    View view = dndview.getChildAt(i);
		    
		    if (view instanceof TextView) {
		    	TextView textView = (TextView) view;
		    	String sentence = textView.getText().toString().trim(); 
    			article.append(sentence);
		    }
		}
		
		// TODO adjust the structure to the new GUI structure
		/*
		 * ListView listView = (ListView) v.findViewById(R.id.articleBrickView);
		 * // TODO if (listView instanceof DragNDropListView) { for (int i = 0;
		 * i < listView.getChildCount(); i++) { View view =
		 * listView.getChildAt(i);
		 * 
		 * if (view instanceof LinearLayout) { LinearLayout linearLayout =
		 * (LinearLayout) view; for (int j = 0; j <
		 * linearLayout.getChildCount(); j++) { View element =
		 * linearLayout.getChildAt(j); if (element instanceof TextView) {
		 * TextView textView = (TextView) element;
		 * 
		 * String sentence = textView.getText().toString().trim();
		 * article.append(sentence); } } } } }
		 */

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "mirobyrtus@gmail.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Share Article: " + "articleName"); // TODO
																				// copy
																				// articlename
																				// here
																				// after
																				// GUI
																				// adjusted
		i.putExtra(Intent.EXTRA_TEXT, article.toString());
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Log.e("Email Service", "There are no email clients installed.");
		}
	}
	
}
