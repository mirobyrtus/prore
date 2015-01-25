package com.prore.mija;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mija.R;
import com.prore.mija.database.Database;
import com.prore.mija.database.DatabaseSerializer;
import com.prore.mija.dragndrop.DynamicListView;
import com.prore.mija.filehelper.FileIterator;
import com.prore.mija.importantpoints.ImportantPointsHandler;
import com.prore.mija.speechrecognition.SpeechRecognitionHelper;
import com.prore.mija.timers.TimerUtils;

public class Startscreen extends FragmentActivity implements OnClickListener, OnKeyListener {

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

	// Database
	private Database database;
	public Database getDatabase() {
		return database;
	}
	
	// Timer
	private Timer myTimer;
	boolean tabs_setup = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.startscreen);
		
		prepareScreen();
		
		// Setup tabs
		myTimer = new Timer();
	    myTimer.schedule(new TimerTask() {          
	        @Override
	        public void run() {
	            TimerMethod();
	        }

	    }, 2000, 1);
		
		setAudioDirName();
		
		database = DatabaseSerializer.loadDatabase(this);
		
		// record();
	}
	
	private void TimerMethod()
	{
	    //This method is called directly by the timer
	    //and runs in the same thread as the timer.

	    //We call the method that will work with the UI
	    //through the runOnUiThread method.
	    this.runOnUiThread(Timer_Tick);
	}


	private Runnable Timer_Tick = new Runnable() {
	    public void run() {

	    	//This method runs in the same thread as the UI.               

	    	//Do something to the UI thread here
	    	setUpTabs();
	    }
	};

	private void prepareScreen() {
		ActionBar actionBar = getActionBar();

		// hide actionbar title and icon
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(new ColorDrawable(getResources().getColor(
				android.R.color.transparent)));
	}
	
	private void setUpTabs() {
		
		if (tabs_setup) return; 
		tabs_setup = true;
		
		// setup action bar for tabs
		ActionBar actionBar = getActionBar();

		// hide actionbar title and icon
//		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setDisplayUseLogoEnabled(false);
//		actionBar.setIcon(new ColorDrawable(getResources().getColor(
//				android.R.color.transparent)));

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
		Intent intent = SpeechRecognitionHelper.prepareIntent();
		
		touchAudioDir();
		
		recognizedAudioPath = getNewAudioFileName();
        // startTimer(); // TODO Missing output - refresh some label or what..
        
		try {
			startActivityForResult(intent, RESPONSECODE);
			recording = true; 
			start_IP = SystemClock.uptimeMillis();
			recording_IP = recognizedAudioPath;
		} catch (ActivityNotFoundException a) {
			Log.e("SpeechRecognition", "Speech Recognition not availible on this device.");
		}
	}
	
	private static int messCounter = 0; 
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		recording = false; 

		switch (requestCode) {
			case AUDIO_FINISHED_PLAYING: {
				playing = false;
				break;
			}
			case RESPONSECODE: {
				if (resultCode == RESULT_OK && data != null) {
	
					ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					if (results.isEmpty()) {
						// Nothing recognized 
						messCounter++;
					}
					
					SpeechRecognitionHelper.processTextData(database, results, mDirName);
					
					// Save Database
					DatabaseSerializer.saveDatabase(this, database);
					
					// Process AUDIO data
					SpeechRecognitionHelper.saveAudioData(data, getContentResolver(), recognizedAudioPath);
					
				} else if (resultCode == 0) {
					// Failed or cancelled 
					
					// Nobody talks / wants to talk anymore?
					messCounter += 2;
				}
				
				if (messCounter < 2) { // 2 sentences
					record();
				}
				
				break;
			}
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
	
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_ENTER) {
			System.out.println();
		}
		
		boolean captured = importantPointsHandler.clicked(keyCode, event, SystemClock.uptimeMillis() - start_IP, recording_IP, database.getActualArticleId());
		if (captured) Toast.makeText(this, "Important Point Captured", Toast.LENGTH_SHORT).show();
	
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println();
	}
	
	public void DeleteBrick(View v) {
		System.out.println();
	}
	

	public void OnMicroClick(View v) {
		messCounter = 0; // Reset mess counter
		record();
	}

	public void onRecordClicked(View v) {
		System.out.println();
	}
	
	public void OnPlayButtonClick(MenuItem item) {
		System.out.println();
		
		// TODO Play the last article ? 
		
	}
	
	public void OnStopButton(MenuItem item) {
		System.out.println();

		// HIDE KEYBOARD
		hideKeyboard();
	}
	
	/** 
	 * From PlayFragment
	 */
	
	public void playArticle(View v) {
		Button button = (Button) v;
		String directory = button.getContentDescription().toString() + "/";
		
		List<File> audioFragments = FileIterator.getFilesList(directory);
		if (! audioFragments.isEmpty()) {
			playAudioIntent(audioFragments.get(0).getAbsolutePath()); // Play one sentence (With GUI)
			// playAudioFragments(audioFragments); // Play whole article (WithOut GUI)
		}
	}
	
	public final static int AUDIO_FINISHED_PLAYING = 123;

	public void playAudioIntent(String audioPath) {
		Intent audioIntent = new Intent(Intent.ACTION_VIEW);
		File file = new File(audioPath);
		audioIntent.setDataAndType(Uri.fromFile(file), "audio/*");
		
		Startscreen.playing = true; 
		
		startActivityForResult(Intent.createChooser(audioIntent, null), AUDIO_FINISHED_PLAYING);
	}
	
	public void playAudioFragments(List<File> audioFragmentsPaths) {
		if (audioFragmentsPaths.isEmpty()) {
			Startscreen.playing = false;
			return;	
		}
		
		MediaPlayer mPlayer = new MediaPlayer();
		final ArrayList<File> stack = new ArrayList<File>(audioFragmentsPaths);
		stack.remove(0);
    	
        mPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            	playAudioFragments(stack);
            }
        });
        
        try {
    	    mPlayer.setDataSource(audioFragmentsPaths.get(0).getAbsolutePath());
    	    Log.i("PlayAudio", "Playing Audio " + audioFragmentsPaths.get(0));
            mPlayer.prepare(); // PrepareAsync?
            
            Startscreen.playing = true;
    		
            mPlayer.start();        	
        } catch (IOException e) {
            Log.e("AudioRecording", "prepare() failed");
        }

	}

	/**
	 * From ArticleFragment
	 */
	
	DynamicListView listView;

	public void RemoveSentence(View v) {
		listView.removeLastItem();
	}
	
	public void AddNewSentence(View v) {
		listView.appendEmptyItem();
	}
	
	public void OnShareViaEmail(View v) {
	
		LinearLayout parent = (LinearLayout) v.getParent().getParent();
		DynamicListView dndview = (DynamicListView) parent.findViewById(R.id.dragndroplistview);
		
		String articleName = ((EditText) parent.findViewById(R.id.articleTitle)).getText().toString();
		
		// Collect info
		StringBuffer article = new StringBuffer();

		for (int i = 0; i < dndview.getChildCount(); i++) {
		    View view = dndview.getChildAt(i);
		    
		    if (view instanceof TextView) {
		    	TextView textView = (TextView) view;
		    	String sentence = textView.getText().toString().trim();
    			article.append(sentence + ". \n");
		    }
		}
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		// i.putExtra(Intent.EXTRA_EMAIL, new String[] { "recipient@domain.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Share Article: " + articleName);
		i.putExtra(Intent.EXTRA_TEXT, article.toString());
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Log.e("Email Service", "There are no email clients installed.");
		}
	}

	public void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
		if (inputManager != null) {
			IBinder binder = getCurrentFocus().getWindowToken();
			if (binder != null) {
				inputManager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);	
			} else {
				System.out.println();
			}
		} else {
			System.out.println();
		}
	}
	
	HashMap<Integer, Integer> redirections = new HashMap<Integer, Integer>();
	public void swap(int from, int to) {
		redirect(from, to);
		// redirect(to, from);
	}
	
	public void redirect(int one, int two) {
		redirections.put(one, two);
	}
	
	public int findRedirected(int original) {
		int redirectedKey = original;
		
		int search = -1;
		
		while (search != redirectedKey) {
			search = redirectedKey;
			
			for (Integer key : redirections.keySet()) {
				if (redirections.get(key).equals(redirectedKey)) {
					redirectedKey = key;
					break;
				}
			}
		}
		
		return redirectedKey;
	}
	
	public void playSentence(int position) {
		int actualposition = findRedirected(position);
		
		List<File> audioFragments = FileIterator.getFilesList(database.getArticle(database.getArticles().size() - 1).getPath());
		if (! audioFragments.isEmpty()) {
			playAudioIntent(audioFragments.get(actualposition).getAbsolutePath());
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		
		// TODO Implement this
		
		System.out.println();
		
		return false;
	}

}
