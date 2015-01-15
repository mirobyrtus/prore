package com.example.mija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import database.Article;
import database.Database;
import database.ParsedSentence;

public class ArticleFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.article_tab, container, false);
		
		// TODO Which Article was chosen? 
		int articleId = 0;
		Article article = Database.getArticle(0);
		String articleName = article.getName(); 
		
		for (ParsedSentence sentence : article.getSentences()) { 
			
			// TODO fill the GUI with this data + the articleName on the top of articleBox
			System.out.println(" >> " + sentence);
			
		}
		
		/**final Button addButton = (Button) getView().findViewById(
				R.id.add_button);
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				//TODO
			}
		});
		
		final Button deleteButton = (Button) getView().findViewById(
				R.id.delete_button);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				//TODO
			}
		});
		
		final Button emailButton = (Button) getView().findViewById(
				R.id.email_button);
		emailButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				//TODO
			}
		});*/
		
        return rootView;
	}
	
	public void OnShareViaEmail(View v) {
		// Collect info
		StringBuffer article = new StringBuffer();
		
		// TODO adjust the structure to the new GUI structure
		/*
		ListView listView = (ListView) v.findViewById(R.id.articleBrickView); // TODO 
		if (listView instanceof DragNDropListView) {
			for (int i = 0; i < listView.getChildCount(); i++) {
			    View view = listView.getChildAt(i);
			    
			    if (view instanceof LinearLayout) {
			    	LinearLayout linearLayout = (LinearLayout) view;
			    	for (int j = 0; j < linearLayout.getChildCount(); j++) {
			    		View element = linearLayout.getChildAt(j);
			    		if (element instanceof TextView) {
			    			TextView textView = (TextView) element; 
		    				
			    			String sentence = textView.getText().toString().trim(); 
			    			article.append(sentence);
			    		}
			    	}
			    }
			}
		}
		*/
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{ "mirobyrtus@gmail.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Share Article");
		i.putExtra(Intent.EXTRA_TEXT   , article.toString());
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Log.e("Email Service", "There are no email clients installed.");
		}
	}

}
