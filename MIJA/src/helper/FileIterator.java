package helper;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileIterator {
	
	public static File[] getFilesArray(String absolutePath) {
		File directory = new File(absolutePath);        
		return directory.listFiles();
	}
	
	public static List<File> getFilesList(String absolutePath) {
		return Arrays.asList(getFilesArray(absolutePath));
	}
	
}
