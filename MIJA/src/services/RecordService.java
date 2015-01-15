package services;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RecordService extends Service {

	MediaRecorder mRecorder = null;

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	public RecordService() {
		super();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

		// TODO
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/MyFolder/";
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		String myfile = path + "googleaudiotest.3gp";

		mRecorder.setOutputFile(myfile);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare(); // PrepareAsync?
		} catch (IOException e) {
			Log.e("AudioRecording", "prepare() failed");
		}

		mRecorder.start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {

		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();

		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;			
		}

		super.onDestroy();
	}
}