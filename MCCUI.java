package co.minecc.client;

import java.math.BigDecimal;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class MCCUI {

	private static MCCUI ui = new MCCUI();
	
	public static void set(MCCUI u) {
		ui = u;
	}
	
	public static void draw() {
		if (ui != null)
			ui.draw(MCC.MINECRAFT);
	}
	
	public final ArrayList<String> DISPLAY = new ArrayList<String>();
	
	public void draw(final Minecraft MC) {
		if (MC.gameSettings.showDebugInfo) return;
		
		DISPLAY.clear();
		
		if (MCC.DATA.title != null)
			DISPLAY.add(MCC.DATA.title);
		
		if (MC.thePlayer != null) {
			double d = ((MC.thePlayer.rotationYaw * 4) / 360);
		    double f = d % 4;
		    if (f < 0){
		    	f = 4.0 - (f - (f * 2));
		    }
		    DISPLAY.add(MCCColour.WHITE.toString() + 
		    		BigDecimal.valueOf(MC.thePlayer.posX).setScale(1, BigDecimal.ROUND_HALF_UP)
		    		+ ", " + 
		    		BigDecimal.valueOf(MC.thePlayer.posY).setScale(1, BigDecimal.ROUND_HALF_UP)
		    		+ ", " + 
		    		BigDecimal.valueOf(MC.thePlayer.posZ).setScale(1, BigDecimal.ROUND_HALF_UP)
		    		+ "  F: " + 
		    		BigDecimal.valueOf(f).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
		DISPLAY.add(MCCColour.WHITE.toString() + MC.debug.split(" ")[0] + " FPS");
		
		build();
		
		for (int i = 0; i != DISPLAY.size(); i++) {
			MC.fontRenderer.drawString(DISPLAY.get(i), 2, 2 + (i * 10), 0);
		}
	}
	
	public void build() {
		
	}
	
}
