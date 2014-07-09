package co.minecc.client.gui.elements;

import org.lwjgl.opengl.GL11;

import co.minecc.client.MCC;
import co.minecc.client.MCCFriend;
import co.minecc.client.MCCTextures;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.MCCGuiScreenFriend;

public class MCCGuiFriend extends MCCGuiElement {

	public MCCFriend friend;
	
	public MCCGuiFriend(long i, int x, int y, MCCFriend f) {
		super(i, x, y, 178, 24);
		friend = f;
	}

	@Override
	public void draw(MCCGuiScreen screen, int cursorX, int cursorY) {
		if (friend == null)
			return;
		
		if (posY > screen.height || posY + height < 0)
			return;
		
		bind(MCCTextures.FRIENDS);
		if (friend.getOnline())
			GL11.glColor3f(0.0F, 1.0F, 0.0F);
		else
			GL11.glColor3f(1.0F, 0.4F, 0.4F);
		screen.drawTexturedModalRect(posX, posY, 0, 0, width, height);
		screen.drawString(friend.toString(), posX + 2, posY + 2);
		screen.drawString(friend.toStringServer(), posX + 2, posY + 12);
	}
	
	@Override
	public void clicked(MCCClick click, int cursorX, int cursorY) {
		MCC.MCC.showGui(new MCCGuiScreenFriend(MCCGuiScreen.active(), friend));
		super.clicked(click, cursorX, cursorY);
	}

}
