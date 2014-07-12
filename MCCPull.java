package co.minecc.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Used to pull data from a website.
 */
public class MCCPull {

	public static void pull(final String TARGET, final MCCPullHandler HANDLER){
		final Thread THREAD = new Thread(new MCCPullInstance(TARGET, HANDLER));
		THREAD.setName("MCC Pull (" + System.currentTimeMillis() + ")");
		THREAD.setPriority(Thread.MIN_PRIORITY);
		THREAD.start();
	}
	
}
class MCCPullInstance implements Runnable {

	public final String TARGET;
	public final MCCPullHandler HANDLER;
	
	public MCCPullInstance(String t, MCCPullHandler c){
		TARGET = t;
		HANDLER = c;
	}
	
	@Override
	public void run() {
		try {
			final URL TARGET_URL = new URL(TARGET);
			final BufferedReader READER = new BufferedReader(new InputStreamReader(TARGET_URL.openStream()));
			final ArrayList<String> RESULT = new ArrayList<String>();
			String line = null;
			do {
				line = READER.readLine();
				if (line != null)
					RESULT.add(line);
			}while (line != null);
			READER.close();
			HANDLER.onSuccess(RESULT.toArray(new String[]{}));
		}catch (Exception e){
			HANDLER.onFailure(e);
		}
	}
	
}
