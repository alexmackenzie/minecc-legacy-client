package co.minecc.client;

import java.net.URI;

// This class should have really been merged with MCCUtil.
public class MCCDesktop {

	public static void openURL(String url) {
		try{
			java.awt.Desktop.getDesktop().browse(new URI(url));
		}catch (Exception e){}
	}
	
}
