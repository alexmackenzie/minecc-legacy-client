package co.minecc.client.language;

import java.io.File;

import co.minecc.client.MCC;

public class MCCLanguage {

	public static final File DIRECTORY = new File(MCC.DIRECTORY, "language");
	public static final File DIRECTORY_DATA = new File(DIRECTORY, "data");
	
	static {
		DIRECTORY.mkdir();
		DIRECTORY_DATA.mkdir();
	}
	
	public static File file(String id){
		return new File(DIRECTORY_DATA, id + ".dat");
	}
	
}