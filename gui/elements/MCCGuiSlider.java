package co.minecc.client.gui.elements;

import org.lwjgl.opengl.GL11;

import co.minecc.client.MCCTextures;
import co.minecc.client.gui.MCCGuiScreen;

public class MCCGuiSlider extends MCCGuiElementDraggable {

	public enum MCCGuiSliderType {
		VERTICAL,
		HORIZONTAL;
	}
	
	public final MCCGuiSliderType TYPE;
	private int range;
	
	public MCCGuiSlider(long i, int x, int y, int r, MCCGuiSliderType t) {
		super(i, x, y, 8, 16);
		TYPE = t;
		reset(x, y, r);
	}
	
	public void reset(int x, int y, int r) {
		range = r;
		posX = x;
		posY = y;
		if (TYPE == MCCGuiSliderType.VERTICAL){
			maxX = posX;
			minX = posX;
			minY = posY;
			maxY = posY + range;
		}else if (TYPE == MCCGuiSliderType.HORIZONTAL){
			maxX = posX + range;
			minX = posX;
			minY = posY;
			maxY = posY;
		}
	}
	
	@Override
	public void draw(MCCGuiScreen s, int cursorX, int cursorY) {
		s.bind(MCCTextures.ELEMENTS);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		if (!enabled)
			s.drawTexturedModalRect(posX, posY, 8, 20, 8, 16);
		else if (dragging)
			s.drawTexturedModalRect(posX, posY, 0, 36, 8, 16);
		else
			s.drawTexturedModalRect(posX, posY, 0, 20, 8, 16);
	}
	
	public double slide() {
		if (TYPE == MCCGuiSliderType.VERTICAL)
			return (double)(posY - minY) / (double)range;
		else if (TYPE == MCCGuiSliderType.HORIZONTAL)
			return (double)(posX - minX) / (double)range;
		return -1;
	}
	
}
