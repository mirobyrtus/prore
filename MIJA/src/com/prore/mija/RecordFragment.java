package com.prore.mija;

import java.util.ArrayList;
import java.util.Locale;

import com.example.mija.R;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class RecordFragment extends Fragment implements OnClickListener {

	private ProgressBar spinner;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.choice_record_mode, container, false);

		/**spinner = (ProgressBar) getView().findViewById(R.id.recordProgress);
		spinner.setVisibility(View.GONE);*/

		return rootView;
	}
	
	public void startRecord(View view) {
		spinner.setVisibility(View.VISIBLE);
		//TODO
	}
	
	public void startSttForTitle(View view) {
		//TODO
	}
	
	public void onRecordClicked(View v) {
		System.out.println();
	}
	
	/**
	 * GoogleVoice Methods
	 */
	private final int RESPONSECODE = 100;

	public void startRecognizing() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		
		// intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");
		
		try {
			startActivityForResult(intent, RESPONSECODE);
		} catch (ActivityNotFoundException a) {
			Log.e("SpeechRecognition", "Speech Recognition not availible on this device.");
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case RESPONSECODE: {
				// TODO RESULT_OK == -1, set it back to constant from Activity!
				if (resultCode == -1 && data != null) {
	
					// Results here
					ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					
				}
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println();
	}
}
