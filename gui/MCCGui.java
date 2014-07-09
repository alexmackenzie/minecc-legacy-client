package co.minecc.client.gui;

import co.minecc.client.MCC;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class MCCGui extends Gui {

	public void bind(ResourceLocation l){
		MCC.MINECRAFT.getTextureManager().bindTexture(l);
	}
	
}
