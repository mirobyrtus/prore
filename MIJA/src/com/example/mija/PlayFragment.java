package com.example.mija;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import filehelper.FileIterator;

public class PlayFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		
		//View rootView = inflater.inflate(R.layout.play_list_tab, container, 
		//		false);
		RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.play_list_tab, container, false);
		/*
		LinearLayout scrollLinearLayout = (LinearLayout) rootView.findViewById(R.id.playListScrollViewLinearLayout);
		
	    // 
	    for (int i = 0; i < 3; i++) {
	    	LinearLayout playItemLayout = (LinearLayout) inflater.inflate(R.layout.play_item, container, false);
	    	TextView text = (TextView) playItemLayout.findViewById(R.id.playTextView);
	    	text.setText(">>>" + i);
	    	scrollLinearLayout.addView(playItemLayout);
	    }
	    */
		
		// Articles database - Crete the List like this
		// TODO recalculate duration (need load all the audio files and summarize the duration)
		// - implemented in FileIterator.java (ich werds dann mal hier reinkoppieren, wenns mit dem 
		// layout passt
//		for (Article article : Database.articles) {
//			
//			System.out.println(" >> " + article.getName());
//			
//			((Button) ((ViewGroup) rootView.getChildAt(0)).getChildAt(0))
//			.setText(article.getName());
//		}
		
		ArrayList<String> audioFragments = FileIterator.getLastRecording();
		
		playAudioIntent(audioFragments.get(0)); // Play one sentence
		
		// playAudioFragments(audioFragments); // Play whole article
		
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
	
	public final static int AUDIO_FINISHED_PLAYING = 123;
	
	public void playAudioIntent(String audioPath) {
		Intent audioIntent = new Intent(Intent.ACTION_VIEW);
		File file = new File(audioPath);
		audioIntent.setDataAndType(Uri.fromFile(file), "audio/*");
		
		Startscreen.playing = true; 
		
		getActivity().startActivityForResult(Intent.createChooser(audioIntent, null), AUDIO_FINISHED_PLAYING);
	}
	
	public void playAudioFragments(ArrayList<String> audioFragmentsPaths) {
		if (audioFragmentsPaths.isEmpty()) {
			Startscreen.playing = false;
			return;	
		}
		
		MediaPlayer mPlayer = new MediaPlayer();
		final ArrayList<String> stack = new ArrayList<String>(audioFragmentsPaths);
		stack.remove(0);
    	
        mPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            	playAudioFragments(stack);
            }
        });
        
        try {
    	    mPlayer.setDataSource(audioFragmentsPaths.get(0));
    	    Log.i("PlayAudio", "Playing Audio " + audioFragmentsPaths.get(0));
            mPlayer.prepare(); // PrepareAsync?
            
            Startscreen.playing = true;
    		
            mPlayer.start();        	
        } catch (IOException e) {
            Log.e("AudioRecording", "prepare() failed");
        }

	}
}
