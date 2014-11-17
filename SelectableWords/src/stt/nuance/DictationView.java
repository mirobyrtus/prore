package stt.nuance;

import importantpoints.ImportantPointsHandler;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nuance.nmdp.speechkit.Prompt;
import com.nuance.nmdp.speechkit.Recognition;
import com.nuance.nmdp.speechkit.Recognizer;
import com.nuance.nmdp.speechkit.SpeechError;
import com.nuance.nmdp.speechkit.SpeechKit;
import com.prore.selectablewords.R;
import com.prore.selectablewords.SelectableWords;

public class DictationView extends Activity 
{
	// From main 
    private static SpeechKit _speechKit;
    
    // Allow other activities to access the SpeechKit instance.
    static SpeechKit getSpeechKit()
    {
        return _speechKit;
    }
	// End From main
    
    ImportantPointsHandler importantPointsHandler = new ImportantPointsHandler();
    
    private static final int LISTENING_DIALOG = 0;
    private Handler _handler = null;
    private final Recognizer.Listener _listener;
    private Recognizer _currentRecognizer;
    private ListeningDialog _listeningDialog;
    private ArrayAdapter<String> _arrayAdapter;
    private ArrayList<Integer> _scores = new ArrayList<Integer>();
    private boolean _destroyed;
    
    private class SavedState
    {
        String DialogText;
        String DialogLevel;
        boolean DialogRecording;
        Recognizer Recognizer;
        Handler Handler;
    }

    public DictationView()
    {
        super();
        _listener = createListener();
        _currentRecognizer = null;
        _listeningDialog = null;
        _destroyed = true;
    }

    @Override
    protected void onPrepareDialog(int id, final Dialog dialog) {
        switch(id)
        {
        case LISTENING_DIALOG:
            _listeningDialog.prepare(new Button.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if (_currentRecognizer != null)
                    {
                        _currentRecognizer.stopRecording();
                    }
                }
            });
            break;
        }
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id)
        {
        case LISTENING_DIALOG:
            return _listeningDialog;
        }
        return null;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // FROM MAIN 

        // If this Activity is being recreated due to a config change (e.g. 
        // screen rotation), check for the saved SpeechKit instance.
        _speechKit = (SpeechKit)getLastNonConfigurationInstance();
        if (_speechKit == null)
        {
            _speechKit = SpeechKit.initialize(getApplication().getApplicationContext(), AppInfo.SpeechKitAppId, AppInfo.SpeechKitServer, AppInfo.SpeechKitPort, AppInfo.SpeechKitSsl, AppInfo.SpeechKitApplicationKey);
            _speechKit.connect();
            // TODO: Keep an eye out for audio prompts not working on the Droid 2 or other 2.2 devices.
            Prompt beep = _speechKit.defineAudioPrompt(R.raw.beep);
            _speechKit.setDefaultRecognizerPrompts(beep, Prompt.vibration(100), null, null);
        }
        // END FROM MAIN 
        
        setVolumeControlStream(AudioManager.STREAM_MUSIC); // So that the 'Media Volume' applies to this activity
        setContentView(R.layout.dictation);
        
        _destroyed = false;
        
        // Use the same handler for both buttons
        final Button dictationButton = (Button)findViewById(R.id.btn_startDictation);
        Button.OnClickListener startListener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            	_listeningDialog.setText("Initializing...");   
                showDialog(LISTENING_DIALOG);
            	_listeningDialog.setStoppable(false);
                setResults(new Recognition.Result[0]);
                
                // String locale = "en_US";  
                String locale = "de_DE";
                
                if (v == dictationButton)
                    _currentRecognizer = _speechKit.createRecognizer(Recognizer.RecognizerType.Dictation, Recognizer.EndOfSpeechDetection.Long, locale, _listener, _handler);
                else
                    _currentRecognizer = _speechKit.createRecognizer(Recognizer.RecognizerType.Search, Recognizer.EndOfSpeechDetection.Short, locale, _listener, _handler);
                _currentRecognizer.start();
                
                importantPointsHandler.reset();
            }
        };
        dictationButton.setOnClickListener(startListener);

        // Set up the list to display multiple results
        ListView list = (ListView)findViewById(R.id.list_results);
        _arrayAdapter = new ArrayAdapter<String>(list.getContext(), R.layout.listitem)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Button b = (Button)super.getView(position, convertView, parent);
                b.setBackgroundColor(Color.GREEN);
                b.setOnClickListener(new Button.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Button b = (Button)v;
                        EditText t = (EditText)findViewById(R.id.text_DictationResult);
                        
                        // Copy the text (without the [score]) into the edit box
                        String text = b.getText().toString();
                        int startIndex = text.indexOf("]: ");
                        t.setText(text.substring(startIndex > 0 ? (startIndex + 3) : 0));
                    }
                });
                return b;
            }   
        };
        list.setAdapter(_arrayAdapter);

        // Initialize the listening dialog
        createListeningDialog();
        
        SavedState savedState = (SavedState)getLastNonConfigurationInstance();
        if (savedState == null)
        {
            // Initialize the handler, for access to this application's message queue
            _handler = new Handler();
        } else
        {
            // There was a recognition in progress when the OS destroyed/
            // recreated this activity, so restore the existing recognition
            _currentRecognizer = savedState.Recognizer;
            _listeningDialog.setText(savedState.DialogText);
            _listeningDialog.setLevel(savedState.DialogLevel);
            _listeningDialog.setRecording(savedState.DialogRecording);
            _handler = savedState.Handler;
            
            if (savedState.DialogRecording)
            {
                // Simulate onRecordingBegin() to start animation
                _listener.onRecordingBegin(_currentRecognizer);
            }
            
            _currentRecognizer.setListener(_listener);
        }
    }

    public void OnProcessButtonClicked(View v) {
    	Intent intent = new Intent(this, SelectableWords.class);

    	int i = 0;
    	// TODO Make it in loop, but first Synchronize sentences! 
//    	for (int i = 0; i < _arrayAdapter.getCount(); i++) {
    		intent.putExtra("Sentence" + i, _arrayAdapter.getItem(i));
    		intent.putExtra("Score" + i, _scores.get(i));
//    	}

    	// Jump to the next Activity
		startActivity(intent);
    }
    
    @Override
    protected void onDestroy() {
    	// TODO copy from Main 
//        if (_speechKit != null)
//        {
//            _speechKit.release();
//            _speechKit = null;
//        }
        // End Copy of main
    	
        super.onDestroy();
        _destroyed = true;
        if (_currentRecognizer !=  null)
        {
            _currentRecognizer.cancel();
            _currentRecognizer = null;
        }
    }
    
    @Override
    public Object onRetainNonConfigurationInstance()
    {
    	// TODO copy from Main 
        // Save the SpeechKit instance, because we know the Activity will be
        // immediately recreated.
//        SpeechKit sk = _speechKit;
//        _speechKit = null; // Prevent onDestroy() from releasing SpeechKit
//        return sk;
        // End copy from main
    	
        if (_listeningDialog.isShowing() && _currentRecognizer != null)
        {
            // If a recognition is in progress, save it, because the activity
            // is about to be destroyed and recreated
            SavedState savedState = new SavedState();
            savedState.Recognizer = _currentRecognizer;
            savedState.DialogText = _listeningDialog.getText();
            savedState.DialogLevel = _listeningDialog.getLevel();
            savedState.DialogRecording = _listeningDialog.isRecording();
            savedState.Handler = _handler;
            
            _currentRecognizer = null; // Prevent onDestroy() from canceling
            return savedState;
        }
        return null;
    }

    private Recognizer.Listener createListener()
    {
        return new Recognizer.Listener()
        {            
            @Override
            public void onRecordingBegin(Recognizer recognizer) 
            {
                _listeningDialog.setText("Recording...");
            	_listeningDialog.setStoppable(true);
                _listeningDialog.setRecording(true);
                
                // Create a repeating task to update the audio level
                Runnable r = new Runnable()
                {
                    public void run()
                    {
                        if (_listeningDialog != null && _listeningDialog.isRecording() && _currentRecognizer != null)
                        {
                            _listeningDialog.setLevel(Float.toString(_currentRecognizer.getAudioLevel()));
                            _handler.postDelayed(this, 500);
                        }
                    }
                };
                r.run();
            }

            @Override
            public void onRecordingDone(Recognizer recognizer) 
            {
                _listeningDialog.setText("Processing...");
                _listeningDialog.setLevel("");
                _listeningDialog.setRecording(false);
            	_listeningDialog.setStoppable(false);
            }

            @Override
            public void onError(Recognizer recognizer, SpeechError error) 
            {
            	if (recognizer != _currentRecognizer) return;
            	if (_listeningDialog.isShowing()) dismissDialog(LISTENING_DIALOG);
                _currentRecognizer = null;
                _listeningDialog.setRecording(false);

                // Display the error + suggestion in the edit box
                String detail = error.getErrorDetail();
                String suggestion = error.getSuggestion();
                
                if (suggestion == null) suggestion = "";
                setResult(detail + "\n" + suggestion);
                // for debugging purpose: printing out the speechkit session id
                android.util.Log.d("Nuance SampleVoiceApp", "Recognizer.Listener.onError: session id ["
                        + _speechKit.getSessionId() + "]");
            }

            @Override
            public void onResults(Recognizer recognizer, Recognition results) {
                if (_listeningDialog.isShowing()) dismissDialog(LISTENING_DIALOG);
                _currentRecognizer = null;
                _listeningDialog.setRecording(false);
                int count = results.getResultCount();
                Recognition.Result [] rs = new Recognition.Result[count];
                for (int i = 0; i < count; i++)
                {
                    rs[i] = results.getResult(i);
                }
                setResults(rs);
                // for debugging purpose: printing out the speechkit session id
                android.util.Log.d("Nuance SampleVoiceApp", "Recognizer.Listener.onResults: session id ["
                        + _speechKit.getSessionId() + "]");
            }
        };
    }
    
    private void setResult(String result)
    {
        EditText t = (EditText)findViewById(R.id.text_DictationResult);
        if (t != null)
            t.setText(result);
    }
    
    private void setResults(Recognition.Result[] results)
    {
        _arrayAdapter.clear();
        _scores.clear();
        if (results.length > 0)
        {
            setResult(results[0].getText());
            
            for (int i = 0; i < results.length; i++) {
                // _arrayAdapter.add("[" + results[i].getScore() + "]: " + results[i].getText());
            	_arrayAdapter.add(results[i].getText());
            	_scores.add(results[i].getScore());
            }
        }  else
        {
        	// TODO test this if its when more sentences
            setResult("");
        }
    }
    
    private void createListeningDialog()
    {
        _listeningDialog = new ListeningDialog(this);
        _listeningDialog.setOnDismissListener(new OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (_currentRecognizer != null) // Cancel the current recognizer
                {
                    _currentRecognizer.cancel();
                    _currentRecognizer = null;
                }
                
                if (!_destroyed)
                {
                    // Remove the dialog so that it will be recreated next time.
                    // This is necessary to avoid a bug in Android >= 1.6 where the 
                    // animation stops working.
                    DictationView.this.removeDialog(LISTENING_DIALOG);
                    createListeningDialog();
                }
            }
        });
    }
    
    /**
     * Important Points Handler
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		importantPointsHandler.clicked(keyCode, event); 
		return super.onKeyDown(keyCode, event);
	}
}
