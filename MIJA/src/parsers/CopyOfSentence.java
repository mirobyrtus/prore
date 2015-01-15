package parsers;

import android.os.Parcel;
import android.os.Parcelable;

//simple class that just has one member property as an example
public class CopyOfSentence implements Parcelable {
	private String sentence;

	/* everything below here is for implementing Parcelable */

	public String getStrVal() {
		return sentence; 
	}
	
	public void setStrVal(String newsentence) {
		sentence = newsentence;
	}
	
	// 99.9% of the time you can just ignore this
	public int describeContents() {
		return 0;
	}

	// write your object's data to the passed-in Parcel
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(sentence);
	}
	
	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<CopyOfSentence> CREATOR = new Parcelable.Creator<CopyOfSentence>() {
		public CopyOfSentence createFromParcel(Parcel in) {
			return new CopyOfSentence(in);
		}

		public CopyOfSentence[] newArray(int size) {
			return new CopyOfSentence[size];
		}
	};

	public CopyOfSentence(String newsentence) {
		sentence = newsentence;
	}
	
	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	private CopyOfSentence(Parcel in) {
		sentence = in.readString();
	}
}