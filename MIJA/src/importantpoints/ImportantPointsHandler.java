package importantpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.util.Log;
import android.view.KeyEvent;

public class ImportantPointsHandler {

	// File -> importantPoints
	private static HashMap<String, ArrayList<Long>> importantPoints = new HashMap<String, ArrayList<Long>>();
	// TODO 
	public static Set<Integer> importantSentenceIds = new HashSet<Integer>();
	
		public void clicked(int keyCode, KeyEvent event, long millis, String audioPath, int sentenceId) { // Context context - Send from Activity if u need 
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			addTimestampForFile(audioPath, millis);
			importantSentenceIds.add(sentenceId);
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
