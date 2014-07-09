package co.minecc.client;

import java.net.URI;

public class MCCDesktop {

	public static void openURL(String url) {
		try{
			java.awt.Desktop.getDesktop().browse(new URI(url));
		}catch (Exception e){}
	}
	
}
