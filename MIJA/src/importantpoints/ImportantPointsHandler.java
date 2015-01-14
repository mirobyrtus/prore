package importantpoints;

import java.util.ArrayList;

import android.view.KeyEvent;
import android.widget.Toast;

public class ImportantPointsHandler {

	private long start = -1; 
	private ArrayList<Long> importantPointsTimestamps; 
	
	public ImportantPointsHandler() {
		reset();
	}
	
	public void reset() {
		importantPointsTimestamps = new ArrayList<Long>();
		start = System.currentTimeMillis();
	}
	
	public void clicked(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			addTimestamp(); 
		}
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			// TODO handle
		}
	}
	
	public void addTimestamp() {
		long millis = System.currentTimeMillis(); 
		importantPointsTimestamps.add(start - millis); // Millis of the recording, not the system
	}
	
	public ArrayList<Long> getTimestamps() {
		return importantPointsTimestamps;
	}
	
}
