package com.prore.mija;

import com.example.mija.R;
import com.prore.mija.database.Article;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		
		RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.play_list_tab, container, false);
		
		LinearLayout scrollLinearLayout = (LinearLayout) rootView.findViewById(R.id.playListScrollViewLinearLayout);
		
		for (Article article : ((Startscreen)getActivity()).getDatabase().getArticles()) {
			
			LinearLayout playItemLayout = (LinearLayout) inflater.inflate(R.layout.play_item, container, false);
	    	
			TextView text = (TextView) playItemLayout.findViewById(R.id.playTextView);
	    	text.setText(article.getName());
	    	
	    	Button playButton = (Button) playItemLayout.findViewById(R.id.playItem);
	    	playButton.setContentDescription(article.getPath());
	    	
	    	scrollLinearLayout.addView(playItemLayout);
			
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
