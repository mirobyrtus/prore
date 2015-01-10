package com.example.mija;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Startscreen extends FragmentActivity {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startscreen);
		setUpTabs();
	}

	private void setUpTabs() {
		// setup action bar for tabs
		ActionBar actionBar = getActionBar();

		// create actionbar tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// hide actionbar title and icon
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setIcon(null);

		/** --- record tab --- */
		Tab recordTab = actionBar
				.newTab()
				.setText(R.string.record)
				.setTabListener(
						new FragmentTabListener<RecordFragment>(this, "record",
								RecordFragment.class));
		actionBar.addTab(recordTab);
		actionBar.selectTab(recordTab);

		/** --- play tab --- */
		Tab playTab = actionBar
				.newTab()
				.setText(R.string.play)
				.setTabListener(
						new FragmentTabListener<PlayFragment>(this, "play",
								PlayFragment.class));
		actionBar.addTab(playTab);

		/** --- article tab --- */
		Tab articleTab = actionBar
				.newTab()
				.setText(R.string.article)
				.setTabListener(
						new FragmentTabListener<ArticleFragment>(this,
								"article", ArticleFragment.class));
		actionBar.addTab(articleTab);

		/** --- settings tab --- */
		Tab settingsTab = actionBar
				.newTab()
				.setIcon(R.drawable.ic_settings)
				.setTabListener(
						new FragmentTabListener<SettingsFragment>(this,
								"settings", SettingsFragment.class));
		actionBar.addTab(settingsTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.buttons_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.record:
			record();
			return true;
		case R.id.pause:
			pause();
			return true;
		case R.id.play:
			play();
			return true;
		case R.id.stop:
			stop();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void record() {
		//TODO: Add code
	};

	public void pause() {
		//TODO: Add code
	};
	
	public void play() {
		//TODO: Add code
	};

	public void stop() {
		//TODO: Add code
	};
}
