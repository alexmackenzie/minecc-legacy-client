package co.minecc.client.language;

import java.io.File;

import co.minecc.client.MCC;

/**
 * MCCLanguage was used to add multi-language support to
 * MineCC before the support was deemed too complex and
 * dropped. Now MCCLanguage and MCCLanguageText exist
 * only as relics to this previously massive feature.
 */
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
