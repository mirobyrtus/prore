package com.prore.mija.parsers;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

//simple class that just has one member property as an example
public class Word implements Parcelable {
	// private String[] word; // With alternatives
	ArrayList<String> word = new ArrayList<String>();
	
	public ArrayList<String> getAlternatives() {
		return word; 
	}
	
	public boolean addAlternative(String alternative) {
		for (String w : word) {
			if (w.equals(alternative)) {
				// TODO some log.. 
				return false; 
			}
		}
		
		word.add(alternative);
		return true; 
	}
	
	/* everything below here is for implementing Parcelable */

	// 99.9% of the time you can just ignore this
	public int describeContents() {
		return 0;
	}

	// write your object's data to the passed-in Parcel
	@Override
	public void writeToParcel(Parcel out, int flags) {
//		out.writeStringArray(word);
		out.writeList(word); 
	}
	
	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
		public Word createFromParcel(Parcel in) {
			return new Word(in);
		}

		public Word[] newArray(int size) {
			return new Word[size];
		}
	};

	public Word(String[] word_alternatives) {
		// Copy the array
		word = new ArrayList<String>(Arrays.asList(word_alternatives));
	}
	
	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	public Word(Parcel in) {
//		in.readStringArray(word);
		in.readList(word, String.class.getClassLoader());
		// in.readTypedArray(word, CREATOR);
	}
}