package importantpoints;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class ImportantPointsHandler {

	// File -> importantPoints
	private static HashMap<String, ArrayList<Long>> importantPoints = new HashMap<String, ArrayList<Long>>(); 
	private static ArrayList<Integer> importantSentenceIds = new ArrayList<Integer>();
	
	public void clicked(int keyCode, KeyEvent event, long millis, String audioPath, int counter) { // Context context - Send from Activity if u need 
		clicked(keyCode, event, millis, audioPath);
		importantSentenceIds.add(counter);
	}

	public void clicked(int keyCode, KeyEvent event, long millis, String audioPath) { // Context context - Send from Activity if u need 
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			addTimestampForFile(audioPath, millis);
			Log.i("ImportantPoint", "Important point captured!");
			// Toast.makeText(context, "Important point captured", Toast.LENGTH_SHORT).show();			
		}
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			// TODO handle
		}
		
		if (keyCode == KeyEvent.KEYCODE_ENTER) { // Remote Control small Button
			addTimestampForFile(audioPath, millis);
		}
	}
	
	public void addTimestampForFile(String audioPath, long placeMillis) {
		if (! importantPoints.containsKey(audioPath)) {
			importantPoints.put(audioPath, new ArrayList<Long>());
		}
		importantPoints.get(audioPath).add(placeMillis);
	}
	
}
