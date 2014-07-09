package co.minecc.client.gui.elements;

import org.lwjgl.opengl.GL11;

import co.minecc.client.MCCTextures;
import co.minecc.client.gui.MCCGuiScreen;

public class MCCGuiText extends MCCGuiElementTextfield {

	public interface MCCGuiTextHandler {
		
		public void handle(MCCGuiText t, String i);
		
	}
	
	private String input = "";
	
	public final MCCGuiTextHandler HANDLER;
	
	public MCCGuiText(long i, int x, int y, int w, MCCGuiTextHandler h) {
		super(i, x, y, w, 12);
		HANDLER = h;
	}

	@Override
	public void draw(MCCGuiScreen screen, int cursorX, int cursorY) {
		bind(MCCTextures.ELEMENTS);
		
		if (selected)
			GL11.glColor3f(0.7F, 1.0F, 0.7F);
		
		int widthR = width;
		int widthD = 0;
		
		while (widthR > 0) {
			drawTexturedModalRect(posX + widthD, posY, 36 + (widthD == 0 ? 0 : 1), 20, Math.min(widthR, 64), 13);
			widthD += 64;
			widthR -= 64;
		}
		drawTexturedModalRect(posX + (width - 1), posY, 255, 20, 1, 12);
		screen.drawString(getInput(), posX + 2, posY + 2);
	}

	@Override
	public String getInput() {
		return input;
	}

	@Override
	public String setInput(String i) {
		input = i;
		return getInput();
	}

	@Override
	public void sendInput() {
		if (HANDLER != null)
			HANDLER.handle(this, getInput());
	}

}
