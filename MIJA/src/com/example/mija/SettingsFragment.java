package com.example.mija;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	private RadioGroup radioLanguageGroup;
	private RadioButton radioLanguageButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings_tab, container,
				false);

		//addListenerOnButton();
		return rootView;
	}

	/*public void addListenerOnButton() {
		//TODO
		radioLanguageGroup = (RadioGroup) getView().findViewById(
				R.id.radioLanguage);
		radioLanguageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// get selected radio button from radioGroup
				int selectedId = radioLanguageGroup.getCheckedRadioButtonId();

				// find the radiobutton by returned id
				radioLanguageButton = (RadioButton) getView().findViewById(
						selectedId);
			}
		});
	}*/
}
