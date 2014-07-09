package co.minecc.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

public class MCCSettings {

	private static final File DIRECTORY = new File(MCC.DIRECTORY, "settings");
	
	public static final String LANGUAGE = "language";
	
	static {
		DIRECTORY.mkdir();
	}
	
	private static File file(String s) {
		return new File(DIRECTORY, s.toLowerCase() + ".dat");
	}
	
	/**
	 * Get the setting corresponding to the given tag.<br>
	 * If the setting cannot be found, the fallback will be returned.
	 * @param s Setting Tag
	 * @param f Fallback
	 */
	public static String get(String s, String f){
		try{
			final File FILE = file(s);
			final BufferedReader READER = new BufferedReader(new FileReader(FILE));
			final String CONTENT = READER.readLine();
			READER.close();
			return CONTENT;
		}catch (Exception e){
			return f;
		}
	}
	
	/**
	 * Set the setting corresponding to the given tag.<br>
	 * Returns success.
	 * @param s Setting Tag
	 * @param v Value
	 * @return Success
	 */
	public static boolean set(String s, String v){
		try{
			final File FILE = file(s);
			final BufferedWriter WRITER = new BufferedWriter(new FileWriter(FILE));
			WRITER.write(v + '\n');
			WRITER.write(new Date().toString());
			WRITER.close();
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
}
