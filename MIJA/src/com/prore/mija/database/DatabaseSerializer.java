package com.prore.mija.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class DatabaseSerializer {

	private static final String databaseFileName = "database.ser";
	private static final String databasePath = databaseFileName;
	
//	private static final String databasePath = Environment
//			.getExternalStorageDirectory().getAbsolutePath()
//			+ "/"
//			+ Startscreen.mAudioSubdir + "/" + databaseFileName;

	public static Database loadDatabase(Context context) {
		FileInputStream fis;
		Database database = null; 
		
		try {
			context.openFileInput(databasePath);
		} catch (FileNotFoundException fnfe) {
			// Create new DB 
			System.out.println("create new db");
			database = new Database(); 
			saveDatabase(context, database);
		} 
		
		if (database == null) {
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
