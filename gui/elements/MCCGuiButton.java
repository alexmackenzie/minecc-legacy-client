package co.minecc.client.gui.elements;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import co.minecc.client.MCCTextures;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.language.MCCLanguageText;

public class MCCGuiButton extends MCCGuiElement {
	
	public MCCLanguageText text = null;
	public String textRaw = null;
	public boolean flash = false;
	
	protected int texX = 0;
	protected int texY = 0;
	protected boolean texSides = true;
	
	public MCCGuiButton(long id, int x, int y, int w, String t) {
		super(id, x, y, w, 20);
		text = null;
		textRaw = t;
	}
	
	public MCCGuiButton(long id, int x, int y, int w, MCCLanguageText t){
		super(id, x, y, w, 20);
		text = t;
	}

	@Override
	public void draw(MCCGuiScreen screen, int cursorX, int cursorY) {
		bind(MCCTextures.ELEMENTS);
		float pulse = drawPulse();
		if (!enabled)
			GL11.glColor4f(0.8F, 0.15F, 0.15F, 1.0F);
		else if (encompasses(cursorX, cursorY))
			GL11.glColor4f(0.6F * pulse, 0.7F * pulse, 1.0F * pulse, 1.0F);
		else if (flash && System.currentTimeMillis() % 2000 >= 1000)
			GL11.glColor4f(0.6F, 1.0F, 0.6F, 1.0F);
		else
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(posX, posY, texX, texY, width, 20);
        if (texSides){
            drawTexturedModalRect(posX, posY, 254, 235, 1, 20);
            drawTexturedModalRect(posX + (width - 1), posY, 254, 235, 1, 20);
        }
        
        String label = display();
        if (label != null)
        	screen.drawStringCentered(label, posX + (width / 2), posY + 5);
	}
	
	private float drawPulse() {
		float pulse = 1.0F;
		long time = System.currentTimeMillis() % 1000;
		if (time > 500)
			time = 500 - (time - 500);
		pulse -= 0.4F * ((float)time / 500F);
		return pulse;
	}
	
	private String display() {
		if (text != null)
			return text.toString();
		else 
			return textRaw;
	}

}
