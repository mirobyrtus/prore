package com.nuance.stt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.mija.R;
import com.prore.mija.importantpoints.ImportantPointsHandler;

public class MainView extends Activity {
    
	ImportantPointsHandler importantPointsHandler = new ImportantPointsHandler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuance_main);
        
        final Button dictationButton = (Button)findViewById(R.id.btn_dictation);
        Button.OnClickListener l = new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (v == dictationButton)
                {
                    Intent intent = new Intent(v.getContext(), DictationView.class);
                    MainView.this.startActivity(intent);
                } 
            }
        };
        dictationButton.setOnClickListener(l);
    }
    
    @Deprecated
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// importantPointsHandler.clicked(keyCode, event); 
		return super.onKeyDown(keyCode, event);
	}
    
}