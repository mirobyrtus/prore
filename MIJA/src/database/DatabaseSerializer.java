package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.example.mija.Startscreen;

import android.content.Context;
import android.os.Environment;

public class DatabaseSerializer {

	private static final String databaseFileName = "database.ser";
	private static final String databasePath = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/"
			+ Startscreen.mAudioSubdir + "/" + databaseFileName;

	public static Database loadDatabase(Context context) {
		FileInputStream fis;
		Database database = null; 
		try {
			fis = context.openFileInput(databasePath);
			ObjectInputStream is = new ObjectInputStream(fis);
			database = (Database) is.readObject();
			is.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (database == null) {
			// TODO Init DB here !
		}
		
		return database;
	}

	public static void saveDatabase(Context context, Database db) {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(databasePath, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(db);
			os.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
