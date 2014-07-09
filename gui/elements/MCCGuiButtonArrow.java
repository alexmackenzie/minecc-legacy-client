package co.minecc.client.gui.elements;

import co.minecc.client.MCCTextures;
import co.minecc.client.gui.MCCGuiScreen;

public class MCCGuiButtonArrow extends MCCGuiButton {

	public enum Direction {
		LEFT,
		RIGHT;
	}
	
	public Direction direction;
	
	public MCCGuiButtonArrow(long id, int x, int y, Direction d) {
		super(id, x, y, 14, (String)null);
		height = 22;
		direction = d;
	}
	
	@Override
	public void draw(MCCGuiScreen screen, int cursorX, int cursorY) {
		bind(MCCTextures.ELEMENTS);
		screen.drawTexturedModalRect(posX, posY, 
				64 + (direction == Direction.LEFT ? 14 : 0),
					33 + (encompasses(cursorX, cursorY) ? 22 : 0),
						width, height);
	}

}
