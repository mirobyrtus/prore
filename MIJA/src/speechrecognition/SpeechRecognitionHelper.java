package speechrecognition;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import com.example.mija.Startscreen;

import database.Database;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.util.Log;

public class SpeechRecognitionHelper {

	public static Intent prepareIntent() {
		
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		
		// Setup language
		//intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
		//		RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		// intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMANY);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.GERMAN);

		// Hidden Google Features
		intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR");
		intent.putExtra("android.speech.extra.GET_AUDIO", true);

		// Recording message
		// intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recording...");
		
		return intent;

	}
	
	public static void processTextData(Database database, Intent data, String articlePath) {
		ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		
		if (results != null && results.size() > 0) {
        	database.addSentenceToArticle(articlePath, results);
        } else {
        	Log.e("SpeechRecognition", "Nothing was recognized!");
        }
	}
	
	public static void saveAudioData(Intent data, ContentResolver contentResolver, String audioFilePath) {
		Uri audioUri = data.getData();
		
	    try {
	    	FileOutputStream fos = new FileOutputStream(audioFilePath);
		    	
			InputStream filestream = contentResolver.openInputStream(audioUri);
			byte[] buffer = new byte[filestream.available()]; 
			filestream.read(buffer);
			
			fos.write(buffer);
		    fos.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
