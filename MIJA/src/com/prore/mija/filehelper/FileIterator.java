package com.prore.mija.filehelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import com.prore.mija.Startscreen;

public class FileIterator {
	
	public static File[] getFilesArray(String absolutePath) {
		File directory = new File(absolutePath);        
		return directory.listFiles();
	}
	
	public static List<File> getFilesList(String absolutePath) {
		File[] files = getFilesArray(absolutePath);
		if (files == null) {
			return new ArrayList<File>();
		}
		return Arrays.asList(files);
	}
	
//	public static ArrayList<String> getRecording(String directory) {
//		ArrayList<String> result = new ArrayList<String>();
//		
//		List<File> recordings = FileIterator.getFilesList(directory);
//		// For every record there is a directory with fragments (after every pause/stop new audio-fragment will be created)
//		File recordingDir = recordings.get(recordings.size() - 1); 
//		List<File> audioFragments = FileIterator.getFilesList(recordingDir.getAbsolutePath());
//		for (File audioFragment : audioFragments) {
//			result.add(audioFragment.getAbsolutePath());
//		}
//	
//		return result;
//	}

	public static void getRecordings() {
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
	    	
	    	// TODO - would be nice to send following data in the onClick method somehow..
	    	// recording.getAbsolutePath();
		}
	}
	
}
