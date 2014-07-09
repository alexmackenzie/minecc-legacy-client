package co.minecc.client.gui.elements;

import co.minecc.client.gui.MCCGui;
import co.minecc.client.gui.MCCGuiScreen;

public abstract class MCCGuiElement extends MCCGui {
	
	public long id = (long) 0x0;
	public int posX = 0, posY = 0;
	public int width = 0, height = 0;
	public boolean enabled = true;
	
	public MCCGuiElement(long i, int x, int y, int w, int h){
		id = i;
		posX = x;
		posY = y;
		width = w;
		height = h;
	}
	
	public void drawInit(MCCGuiScreen screen, int cursorX, int cursorY) {};
	public abstract void draw(MCCGuiScreen screen, int cursorX, int cursorY);
	
	public boolean encompasses(int x, int y){
		return enabled && (x >= posX && x <= (posX + width) && (y >= posY && y <= posY + height));
	}
	
	public void init() {}
	public void clicked(MCCClick click, int cursorX, int cursorY) {};
	public void clickedOther(MCCClick click, int cursorX, int cursorY) {};
	public void typed(char typed, int key) {};
	public void closed() {};
	
}
