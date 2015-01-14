package com.example.mija;

import helper.FileIterator;

import java.io.File;
import java.io.IOException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Startscreen extends FragmentActivity {

	/** Called when the activity is first created. */

	private MediaRecorder mRecorder = null;
	private MediaPlayer   mPlayer = null;

	private boolean recording = false;
	private boolean playing = false;

	private String mDirName = null;
	private final static String mAudioSubdir = "mija_audio";
	// private static String mFileName = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startscreen);
		setUpTabs();		
		setAudioDirName();
		String aname = getNewAudioFileName();
	}

	private void setUpTabs() {
		// setup action bar for tabs
		ActionBar actionBar = getActionBar();

		// hide actionbar title and icon
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(new ColorDrawable(getResources().getColor(
				android.R.color.transparent)));

		// create actionbar tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		/** --- record tab --- */
		Tab recordTab = actionBar
				.newTab()
				.setText(R.string.record)
				.setTabListener(
						new FragmentTabListener<RecordFragment>(this, "record",
								RecordFragment.class));
		actionBar.addTab(recordTab);
		actionBar.selectTab(recordTab);

		/** --- play tab --- */
		Tab playTab = actionBar
				.newTab()
				.setText(R.string.play)
				.setTabListener(
						new FragmentTabListener<PlayFragment>(this, "play",
								PlayFragment.class));
		actionBar.addTab(playTab);

		/** --- article tab --- */
		Tab articleTab = actionBar
				.newTab()
				.setText(R.string.article)
				.setTabListener(
						new FragmentTabListener<ArticleFragment>(this,
								"article", ArticleFragment.class));
		actionBar.addTab(articleTab);

		/** --- settings tab --- */
		Tab settingsTab = actionBar
				.newTab()
				.setIcon(R.drawable.ic_settings)
				.setTabListener(
						new FragmentTabListener<SettingsFragment>(this,
								"settings", SettingsFragment.class));
		actionBar.addTab(settingsTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.buttons_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.record:
			record();
			return true;
		case R.id.pause:
			pause();
			return true;
		case R.id.play:
			play();
			return true;
		case R.id.stop:
			stop();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void setAudioDirName() {
		File externalStorageDirectory = Environment.getExternalStorageDirectory();
		mDirName = externalStorageDirectory.getAbsolutePath() + "/" + mAudioSubdir;
		File audioFolder = new File(mDirName);
		if (! audioFolder.exists()) {
		    if (! audioFolder.mkdir()) {
		    	Log.e("AudioRecorder", "Cannot create new Directory!");	
		    }
		}
	}
	
	private String getNewAudioFileName() {
		File files[] = FileIterator.getFilesArray(mDirName);
		return mDirName + "/audiorecordtest_" + files.length + ".3gp";
	}
	
	private String getLatestAudioFileName() {
		File files[] = FileIterator.getFilesArray(mDirName);
		if (files.length == 0) {
			return null; 
		}
		return files[files.length - 1].getAbsolutePath();
	}

	public void record() {
		if (! recording) {
			startRecording();
        } else {
        	Log.e("AudioRecording", "Already recording!");
        }
	};

	public void pause() {
		// stop() can be used since record() can be executed multiple times
		// and will save input to multiple files anyway. 
		// ! Only problem that while playing u cannot continue from that point - for prototype not needed
		stop(); 
	};

	public void play() {
		if (! recording) {
			startPlaying();
		} else {
			Log.e("AudioRecording", "Cannot play while recording!");
		}
	};

	public void stop() {
		if (recording) {
            stopRecording();
        } else if (playing) {
        	stopPlaying();
        } else {
        	Log.e("AudioRecording", "Nothing to stop!");
        }
	};
	
	/**
	 * Recording region
	 */
	
	private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(getNewAudioFileName());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("AudioRecording", "prepare() failed");
        }

        mRecorder.start();
        
        recording = true; 
    }

    private void stopRecording() {
    	mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        
        recording = false; 
    }
    
    /**
     * Playing region
     */
	
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
        	String lastAudioName = getLatestAudioFileName();
        	if (lastAudioName != null) {
	            mPlayer.setDataSource(lastAudioName);
	            mPlayer.prepare();
	            mPlayer.start();
        	} else {
        		Log.e("AudioRecording", "Nothing to play!");
        		return;
        	}
        } catch (IOException e) {
            Log.e("AudioRecording", "prepare() failed");
        }
        
        playing = true; 
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        
        playing = false; 
    }
}
