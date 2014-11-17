package com.nuance.nmdp.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainView extends Activity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}