package co.minecc.client.gui;

import java.util.ArrayList;

import co.minecc.client.MCC;

public class MCCGuiScreenMessage extends MCCGuiScreen {
	
	public String[] message;
	
	public MCCGuiScreenMessage(MCCGuiScreen p, String... msg) {
		super(p);
		message = msg;
	}

	@Override
	public void init() {
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		super.draw(cursorX, cursorY);
		int y = 30;
		for (String m : message){
			@SuppressWarnings("unchecked")
			ArrayList<String> text = new ArrayList<String>(MCC.MINECRAFT.fontRenderer.listFormattedStringToWidth(m, width - 20));
			while (text.size() >= 1){
				drawStringCentered(text.get(0), width / 2, y);
				text.remove(0);
				y += 10;
			}
		}
	}

}
