package co.minecc.client;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MCCUtil {

	public static void download(final URL SOURCE, final File TARGET) throws Exception {
		final ReadableByteChannel CHANNEL = Channels.newChannel(SOURCE.openStream());
		final FileOutputStream OUTPUT = new FileOutputStream(TARGET);
		OUTPUT.getChannel().transferFrom(CHANNEL, 0, Long.MAX_VALUE);
		CHANNEL.close();
		OUTPUT.flush();
		OUTPUT.close();
	}
	
}
