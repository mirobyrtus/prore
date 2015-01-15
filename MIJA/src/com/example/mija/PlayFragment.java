package com.example.mija;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import database.Article;
import database.Database;

public class PlayFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.play_list_tab, container, 
				false);
		
		// Articles database - Crete the List like this 
		// Sorry, but no duration information, just articleName
		for (Article article : Database.articleDatabase) {
			
			System.out.println(" >> " + article.getName());
			
		}
		
		/**final Button openItemButton = (Button) getView().findViewById(
				R.id.playItem);
	openItemButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				//TODO
			}
		});
		
		final Button openItemButton2 = (Button) getView().findViewById(
				R.id.playItem2);
		openItemButton2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				//TODO
			}
		});
		
		final Button sttButton = (Button) getView().findViewById(
				R.id.stt_button);
		sttButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				//TODO
			}
		});*/

		return rootView;
	}

}
