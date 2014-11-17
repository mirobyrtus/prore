package com.prore.selectablewords;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import dragndrop.DragListener;
import dragndrop.DragNDropAdapter;
import dragndrop.DragNDropListView;
import dragndrop.DropListener;
import dragndrop.RemoveListener;

public class SentenceList extends ListActivity {

	private DropListener mDropListener = new DropListener() {
		public void onDrop(int from, int to) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter) adapter).onDrop(from, to);
				getListView().invalidateViews();
			}
		}
	};

	private RemoveListener mRemoveListener = new RemoveListener() {
		public void onRemove(int which) {
			ListAdapter adapter = getListAdapter();
			if (adapter instanceof DragNDropAdapter) {
				((DragNDropAdapter) adapter).onRemove(which);
				getListView().invalidateViews();
			}
		}
	};

	private DragListener mDragListener = new DragListener() {

		int backgroundColor = 0xe0103010;
		int defaultBackgroundColor;

		public void onDrag(int x, int y, ListView listView) {
			// TODO Auto-generated method stub
		}

		public void onStartDrag(View itemView) {
			itemView.setVisibility(View.INVISIBLE);
			defaultBackgroundColor = itemView.getDrawingCacheBackgroundColor();
			itemView.setBackgroundColor(backgroundColor);
			ImageView iv = (ImageView) itemView.findViewById(R.id.ImageView01);
			if (iv != null)
				iv.setVisibility(View.INVISIBLE);
		}

		public void onStopDrag(View itemView) {
			itemView.setVisibility(View.VISIBLE);
			itemView.setBackgroundColor(defaultBackgroundColor);
			ImageView iv = (ImageView) itemView.findViewById(R.id.ImageView01);
			if (iv != null)
				iv.setVisibility(View.VISIBLE);
		}

	};

	public void OnShareViaEmail(View v) {
		// Collect info
		StringBuffer article = new StringBuffer();
		ListView listView = getListView();
		if (listView instanceof DragNDropListView) {
			for (int i = 0; i < listView.getChildCount(); i++) {
			    View view = listView.getChildAt(i);
			    
			    if (view instanceof LinearLayout) {
			    	LinearLayout linearLayout = (LinearLayout) view;
			    	for (int j = 0; j < linearLayout.getChildCount(); j++) {
			    		View element = linearLayout.getChildAt(j);
			    		if (element instanceof TextView) {
			    			TextView textView = (TextView) element; 
		    				
			    			String sentence = textView.getText().toString().trim(); 
			    			article.append(sentence);
			    		}
			    	}
			    }
			}
		}
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{ "mirobyrtus@gmail.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, "Share Article");
		i.putExtra(Intent.EXTRA_TEXT   , article.toString());
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(SentenceList.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dragndroplistview);

		// Get the data from SelectableWords
		Intent intent = getIntent();
		ArrayList<String> intentSentences = new ArrayList<String>();
		
		// TODO not really optimized... 
		int intentSentenceIndex = 0; 
		String intentSentence = intent.getStringExtra("Sentence" + intentSentenceIndex);
		do {
			intentSentences.add(intentSentence); 
			intentSentence = intent.getStringExtra("Sentence" + (++intentSentenceIndex)); 
		} while (intentSentence != null);
		
		ArrayList<String> content = new ArrayList<String>();
		for (String sentence : intentSentences) {
			content.add(sentence != null ? sentence : "<NULL>");
		}

		setListAdapter(new DragNDropAdapter(this,
				new int[] { R.layout.dragitem }, new int[] { R.id.TextView01 },
				content));// new DragNDropAdapter(this,content)
		ListView listView = getListView();
		
		if (listView instanceof DragNDropListView) {
			((DragNDropListView) listView).setDropListener(mDropListener);
			((DragNDropListView) listView).setRemoveListener(mRemoveListener);
			((DragNDropListView) listView).setDragListener(mDragListener);
		}
	}

}
