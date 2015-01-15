package timers;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

public class TimerUtils {

	// Time
	public static Handler timerHandler = new Handler();
	static long startTime = 0;
		
	public static void setStartTime(long millis) {
		startTime = millis;
	}
	
	public static void startTimer() {
		timerHandler.postDelayed(updateTimerMethod, 0);
	}
	
	public static void stopTimer() {
		timerHandler.removeCallbacks(updateTimerMethod);
	}

	/**
     * Timer for recording audio
     */
    public static Runnable updateTimerMethod = new Runnable() {
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
    public static void startCountDown(int audioDurationMillisecond) {
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
