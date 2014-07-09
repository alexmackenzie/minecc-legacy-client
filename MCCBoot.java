package co.minecc.client;

import net.minecraft.client.Minecraft;

public class MCCBoot {

	private static boolean booted = false;
	
	public static synchronized void boot() {
		if (booted)
			return;
		
		booted = true;
		
		final Thread THREAD = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().guiAchievement == null)
					try{Thread.sleep(500);}catch(Exception e){};
				MCC.MCC.init();
			}
		});
		THREAD.setName("MCC Boot");
		THREAD.start();
	}
	
}
