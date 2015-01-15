package parsers;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

//simple class that just has one member property as an example
public class Sentence implements Parcelable {
	private Word[] sentence;
	
	public Word[] getWords() {
		return sentence;
	} 
	
	public void addAlternative(String s) {
		if (sentence == null || sentence.length == 0) {
			String[] words = s.split(" ");
			sentence = new Word[words.length]; 
			for (int i = 0; i < words.length; i++) {
				Word word = new Word(new String[] { words[i] });
				sentence[i] = word;
			}
		} else {
			String[] words = s.split(" ");
			if (sentence.length == words.length) {
				for (int i = 0; i < words.length; i++) {
					Word savedWord = sentence[i];
					savedWord.addAlternative(words[i]);
					sentence[i] = savedWord; 
				}	
			} else {
				// TODO 
				Log.e(this.getClass().toString(), " NOT IMPLEMENTED YET ! TODO ... ");
			}
		}
	}
	
	/* everything below here is for implementing Parcelable */

	// 99.9% of the time you can just ignore this
	public int describeContents() {
		return 0;
	}

	// write your object's data to the passed-in Parcel
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelableArray(sentence, flags);
	}
	
	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<Sentence> CREATOR = new Parcelable.Creator<Sentence>() {
		public Sentence createFromParcel(Parcel in) {
			return new Sentence(in);
		}

		public Sentence[] newArray(int size) {
			return new Sentence[size];
		}
	};

	public Sentence(String newsentence) {
		addAlternative(newsentence);
	}
	
	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	private Sentence(Parcel in) {
		Parcelable[] parcelableArray = in.readParcelableArray(Word.class.getClassLoader());
		// Cast array
		if (parcelableArray != null) {
		    sentence = Arrays.copyOf(parcelableArray, parcelableArray.length, Word[].class);
		}
	}
}