package com.example.mija;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class RecordFragment extends Fragment {

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
}
