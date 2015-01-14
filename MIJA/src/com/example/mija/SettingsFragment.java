package com.example.mija;

import java.util.Locale;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsFragment extends Fragment {

	private RadioGroup radioLanguageGroup;
	private RadioButton radioLanguageButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings_tab, container,
				false);

		String languageToLoad  = "de"; // your language
	    Locale locale = new Locale(languageToLoad); 
	    Locale.setDefault(locale);
	    Configuration config = new Configuration();
	    config.locale = locale;
	    //getBaseContext().getResources().updateConfiguration(config, 
	    //  getBaseContext().getResources().getDisplayMetrics());
	    
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
