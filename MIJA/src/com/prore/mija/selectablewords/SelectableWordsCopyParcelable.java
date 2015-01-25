package com.prore.mija.selectablewords;
 
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mija.R;
import com.prore.mija.adapters.WordsAdapter;
import com.prore.mija.parsers.Sentence;
import com.prore.mija.parsers.TextParser;
 
public class SelectableWordsCopyParcelable extends Activity {
 
	GridView gridView;
	String[][][] parsed_text; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
 
		// Check if data sent from DictationView
		Intent intent = getIntent();
		
		ArrayList<Sentence> intentSentences = new ArrayList<Sentence>();
		StringBuffer content = new StringBuffer(); 
		// TODO not really optimized... 
		int intentSentenceIndex = 0; 
		
		intent.setExtrasClassLoader(Sentence.class.getClassLoader());
		// String intentSentence = intent.getStringExtra("Sentence" + intentSentenceIndex);
		Sentence sentence = intent.getParcelableExtra("Sentence" + intentSentenceIndex);
		
		do {
			
			intentSentences.add(sentence);
//			intentSentences.add(intentSentence);
			
			// content.append(intentSentence);
			// intentSentence = intent.getStringExtra("Sentence" + (++intentSentenceIndex));
			sentence = intent.getParcelableExtra("Sentence" + (++intentSentenceIndex));
		} while (sentence != null);
		// End DictationView check
		
		parsed_text = // TextParser.parseTextDummy();
		// TextParser.parseText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");		
		// TextParser.parseText(content.toString());
		TextParser.parseSentences(intentSentences);
		
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new WordsAdapter(this, parsed_text));
 
	}
 
	public void OnNextButtonClick(View v) {
		// Collect Data from activity (the selected alternatives) and send them to 
		ArrayList<String> sentences = new ArrayList<String>(); 
		String tempSentence = "";
		
		for (int i = 0; i < gridView.getChildCount(); i++) {
		    View view = gridView.getChildAt(i);
		    
		    if (view instanceof LinearLayout) {
		    	LinearLayout linearLayout = (LinearLayout) view;
		    	for (int j = 0; j < linearLayout.getChildCount(); j++) {
		    		View element = linearLayout.getChildAt(j);
		    		if (element instanceof Spinner) {
		    			Spinner spinner = (Spinner) element; 
		    			String selectedWord = spinner.getSelectedItem().toString(); 
//		    			System.out.println("Spiner with value = " + selectedWord);
		    			
		    			tempSentence += " " + selectedWord;
		    		} else if (element instanceof TextView) {
		    			TextView textView = (TextView) element; 
		    			if (textView.getVisibility() == View.VISIBLE) {
		    				String dot = textView.getText().toString().trim(); 
//			    			System.out.println("new Sentence = " + dot);	
			    			if (dot.equals(".")) {
				    			sentences.add(tempSentence + dot); 
				    			tempSentence = "";	
			    			}
		    			}
		    		}
		    	}
		    }
		}
		
		// ... and send them to SentenceList Activity
		Intent intent = new Intent(this, SentenceList.class);
		for (int i = 0; i < sentences.size(); i++) {
			intent.putExtra("Sentence" + i, sentences.get(i));
		}
		startActivity(intent);
	}
	
}