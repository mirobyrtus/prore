package adapters;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mija.R;
 
public class WordsAdapter extends BaseAdapter {
	
	private Context context;
	private final String[][][] parsedText;
 
	public WordsAdapter(Context context, String[][][] parsedText) {
		this.context = context;
		this.parsedText = parsedText;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.mobile, null);
			
			// Find the word first
			int sentence_index = 0;
			int word_index = 0;
			String[] alternatives;
							
			while (position > (word_index + parsedText[sentence_index].length) - 1) {
				word_index += parsedText[sentence_index].length; 
				sentence_index ++; 
			}
			
			word_index = position - word_index;
			alternatives = parsedText[sentence_index][word_index];
			
			// Check if last word in sentence 
			if (word_index == parsedText[sentence_index].length - 1) {
				TextView tv = (TextView) gridView.findViewById(R.id.dot);
				tv.setVisibility(View.VISIBLE);
			}
			
			Spinner s = (Spinner) gridView.findViewById(R.id.grid_item_spinner);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					context,
					android.R.layout.simple_spinner_item, 
					alternatives);
			s.setAdapter(adapter);
			
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		int count = 0; 
		for (int i = 0; i < parsedText.length; i++) {
			count += parsedText[i].length;
		}
		
		return count;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}