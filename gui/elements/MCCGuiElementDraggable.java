package co.minecc.client.gui.elements;

import co.minecc.client.gui.MCCGuiScreen;

public abstract class MCCGuiElementDraggable extends MCCGuiElement {

	public boolean dragging = false;
	public int offsetX = 0, offsetY = 0;
	public int maxX = Integer.MAX_VALUE, maxY = Integer.MAX_VALUE;
	public int minX = Integer.MIN_VALUE, minY = Integer.MIN_VALUE;
	
	public MCCGuiElementDraggable(long i, int x, int y, int w, int h) {
		super(i, x, y, w, h);
	}

	@Override
	public void drawInit(MCCGuiScreen screen, int cursorX, int cursorY) {
		dragging = dragging && MCCClick.held(MCCClick.LEFT);
		if (dragging){
			posX = Math.min(Math.max(cursorX + offsetX, minX), maxX);
			posY = Math.min(Math.max(cursorY + offsetY, minY), maxY);
		}
		super.drawInit(screen, cursorX, cursorY);
	}
	
	@Override
	public void clicked(MCCClick click, int cursorX, int cursorY) {
		if (click == MCCClick.LEFT){
			dragging = !dragging;
			if (dragging){
				offsetX = posX - cursorX;
				offsetY = posY - cursorY;
			}
		}
		super.clicked(click, cursorX, cursorY);
	}
	
	@Override
	public void clickedOther(MCCClick click, int cursorX, int cursorY) {
		dragging = false;
		super.clickedOther(click, cursorX, cursorY);
	}

}
