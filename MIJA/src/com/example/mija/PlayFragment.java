package com.example.mija;

import helper.FileIterator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.play_list_tab_row, container, 
				false);
		
		String audioPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Startscreen.mAudioSubdir;
		List<File> recordings = FileIterator.getFilesList(audioPath);
		// For every record there is a directory with fragments (after every pause/stop new audio-fragment will be created)
		int i = 0;
		for (File recording : recordings) {
			String name = "Track " + i++;
			long duration = 0;
			List<File> audioFragments = FileIterator.getFilesList(recording.getAbsolutePath());
			for (File audioFragment : audioFragments) {
		        MediaPlayer player = new MediaPlayer();
		        try {
	        	    player.setDataSource(audioFragment.getAbsolutePath());
	        	    player.prepare(); // PrepareAsync?
		            duration += player.getDuration();
		        } catch (IOException e) {
		            Log.e("AudioRecording", "prepare() failed");
		        }
		        player.release();
		        player = null;
			}
			
			int seconds = (int) (duration / 1000);
	    	int minutes = seconds / 60;
	    	seconds = seconds % 60;
	    	
	    	// Pseudocode
			// (R.id.playItem).setText(name);
			// (R.id.playItem2).setText(minutes + ":" + seconds);
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
