package com.example.mija;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ArticleFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.article_tab, container, false);
		
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
}
