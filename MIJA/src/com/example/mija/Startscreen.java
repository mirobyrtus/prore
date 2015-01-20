package com.example.mija;

import filehelper.FileIterator;
import importantpoints.ImportantPointsHandler;

import java.io.File;
import java.io.IOException;

import speechrecognition.SpeechRecognitionHelper;
import timers.TimerUtils;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;

public class Startscreen extends FragmentActivity {

	/** Called when the activity is first created. */

	// Audio
	private MediaPlayer mPlayer = null;

	// Flags
	public static boolean recording = false;
	public static boolean playing = false;

	// Filestructure
	private String mDirName = null;
	public final static String mAudioSubdir = "mija_audio";
	
	// Important Points
	ImportantPointsHandler importantPointsHandler = new ImportantPointsHandler();
	long start_IP = 0;
	String recording_IP = null;
	
	// Sentences & Parsers
	// private ArrayAdapter<String> _arrayAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startscreen);
		setUpTabs();
		setAudioDirName();
		
		record();
		
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

	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.record_button:
			record();
			return true;
		case R.id.pause_button:
			pause();
			return true;
		case R.id.play_button:
			play();
			return true;
		case R.id.stop_button:
			stop();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	*/
	
	private void setAudioDirName() {
		File externalStorageDirectory = Environment.getExternalStorageDirectory();
		mDirName = externalStorageDirectory.getAbsolutePath() + "/" + mAudioSubdir + "/" + System.currentTimeMillis();
	}
	
	private void touchAudioDir() {
		File audioFolder = new File(mDirName);
		if (! audioFolder.exists()) {
		    if (! audioFolder.mkdirs()) {
		    	Log.e("AudioRecorder", "Cannot create new Directory!");	
		    }
		}
	}
	
	private String getNewAudioFileName() {
		File files[] = FileIterator.getFilesArray(mDirName);
		return mDirName + "/record_" + files.length + ".amr";
	}
	
	private String getLatestAudioFileName() {
		File files[] = FileIterator.getFilesArray(mDirName);
		if (files == null || files.length == 0) {
			return null; 
		}
		return files[files.length - 1].getAbsolutePath();
	}

	private final int RESPONSECODE = 100;
	private String recognizedAudioPath;
	
	public void startRecognizingAndRecording() {
		counter++; 
		
		Intent intent = SpeechRecognitionHelper.prepareIntent();
		
		touchAudioDir();
		
		recognizedAudioPath = getNewAudioFileName();
        startTimer(); // TODO Missing output - refresh some label or what..
        
		try {
			startActivityForResult(intent, RESPONSECODE);
			recording = true; 
			start_IP = SystemClock.uptimeMillis();
			recording_IP = recognizedAudioPath;
		} catch (ActivityNotFoundException a) {
			Log.e("SpeechRecognition", "Speech Recognition not availible on this device.");
		}
	}
	
	private static int counter = 0; 
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		recording = false; 

		switch (requestCode) {
			case PlayFragment.AUDIO_FINISHED_PLAYING: {
				playing = false;
				break;
			}
			case RESPONSECODE: {
				if (resultCode == RESULT_OK && data != null) {
	
					// Process TEXT data
					SpeechRecognitionHelper.processTextData(data, mDirName);
					
					// Process AUDIO data
					SpeechRecognitionHelper.saveAudioData(data, getContentResolver(), recognizedAudioPath);
					
				}
				break;
			}
		}
		
		if (counter < 2) { // 2 sentences
			record();
		}
	}
	
	public void record() {
		if (! recording) {
			startRecognizingAndRecording();
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
		
		// TODO Since Buttons not working properly : 
		record();
		
		if (! recording) {
			startPlaying();
		} else {
			Log.e("AudioRecording", "Cannot play while recording!");
		}
	};

	public void stop() {
		if (recording) {
            // Not needed, handled by SpeechRecognition Intent
        } else if (playing) {
        	stopPlaying();
        } else {
        	Log.e("AudioRecording", "Nothing to stop!");
        }
	};
	
	/**
	 * Recording region
	 */    
    public void startTimer() {
        TimerUtils.setStartTime(SystemClock.uptimeMillis());
        TimerUtils.startTimer(); 
    }
    
    public void stopTimer() {
    	TimerUtils.stopTimer();
    }
    
    /**
     * Playing region
     */
    private void startPlaying() {
    	mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            	playing = false;
            }
        });
        
        String lastAudioName = getLatestAudioFileName();
        try {
        	if (lastAudioName != null) {
	            mPlayer.setDataSource(lastAudioName);
	            mPlayer.prepare(); // PrepareAsync?
	            mPlayer.start();
	            TimerUtils.startCountDown(mPlayer.getDuration());
        	} else {
        		Log.e("AudioRecording", "Nothing to play!");
        		return;
        	}
        } catch (IOException e) {
            Log.e("AudioRecording", "prepare() failed");
        }
        
        playing = true;
        start_IP = SystemClock.uptimeMillis();
		recording_IP = lastAudioName;
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        
        playing = false; 
    }
    
    /**
     * Important Points Handler
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (recording || playing) {
			importantPointsHandler.clicked(keyCode, event, SystemClock.uptimeMillis() - start_IP, recording_IP);
		} else {
			// Log.e("CaptureImportantPoint", "Nothing to assign the important point to");
		}
		return super.onKeyDown(keyCode, event);
	}
}
