package com.example.mija;

import helper.FileIterator;

import java.io.File;
import java.io.IOException;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Startscreen extends FragmentActivity {

	/** Called when the activity is first created. */

	// Audio
	private MediaRecorder mRecorder = null;
	private MediaPlayer   mPlayer = null;

	// Flags
	private boolean recording = false;
	private boolean playing = false;

	// Filestructure
	private String mDirName = null;
	public final static String mAudioSubdir = "mija_audio";
	
	// Time
	private Handler timerHandler = new Handler();
	long startTime = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startscreen);
		setUpTabs();		
		setAudioDirName();
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
		mDirName = externalStorageDirectory.getAbsolutePath() + "/" + mAudioSubdir + "/" + System.currentTimeMillis();
		File audioFolder = new File(mDirName);
		if (! audioFolder.exists()) {
		    if (! audioFolder.mkdirs()) {
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
            mRecorder.prepare(); // PrepareAsync?
        } catch (IOException e) {
            Log.e("AudioRecording", "prepare() failed");
        }

        mRecorder.start();
        
        startTimer();
        
        recording = true; 
    }

    private void stopRecording() {
    	mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        
        stopTimer(); 
        
        recording = false;
    }
    
    public void startTimer() {
        startTime = SystemClock.uptimeMillis();
        timerHandler.postDelayed(updateTimerMethod, 0);
    }
    
    public void stopTimer() {
    	timerHandler.removeCallbacks(updateTimerMethod);
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
        
        try {
        	String lastAudioName = getLatestAudioFileName();
        	if (lastAudioName != null) {
	            mPlayer.setDataSource(lastAudioName);
	            mPlayer.prepare(); // PrepareAsync?
	            mPlayer.start();
	            startCountDown(mPlayer.getDuration());
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
    
    /**
     * Timer for recording audio
     */
    private Runnable updateTimerMethod = new Runnable() {
    	public void run() {
	    	long actualTime = SystemClock.uptimeMillis() - startTime;
	    	
	    	int milliseconds = (int) (actualTime % 1000);
	    	int seconds = (int) (actualTime / 1000);
	    	int minutes = seconds / 60;
	    	seconds = seconds % 60;
	    	
	    	// TODO Show the time in the time counter
	    	// e.g. Label.setText(minutes + ":" + seconds + "." + milliseconds);
	    	
	    	timerHandler.postDelayed(this, 0);
    	}
    };
    
    /** 
     * Countdown for playing audio
     */
    private void startCountDown(int audioDurationMillisecond) {
    	if (audioDurationMillisecond >= 0) {

    		// 1000 means every second onTick()
    		new CountDownTimer(audioDurationMillisecond, 1000) {
	        	
	        	public void onTick(long millisUntilFinished) {
	    	    	int milliseconds = (int) (millisUntilFinished % 1000);
	    	    	int seconds = (int) (millisUntilFinished / 1000);
	    	    	int minutes = seconds / 60;
	    	    	seconds = seconds % 60;
	    	    	
	    	    	// TODO Show the remaining time in the time countdown counter
	    	    	// e.g. Label.setText(minutes + ":" + seconds + "." + milliseconds);
	        	}
	        	
				@Override
				public void onFinish() { }
				
	        }.start();
    	} else {
    		Log.e("AudioRecording", "Cannot start countdown");
    	}
    } 
    
}
