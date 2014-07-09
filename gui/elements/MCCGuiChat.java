package co.minecc.client.gui.elements;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import co.minecc.client.MCC;
import co.minecc.client.MCCChat;
import co.minecc.client.MCCColour;
import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.language.MCCLanguageText;

public class MCCGuiChat extends MCCGuiElementTextfield {
	
	public MCCChat chat;
	
	public MCCGuiChat(long i, int x, int y, int w, int h, MCCChat c) {
		super(i, x, y, w, h);
		chat = c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void draw(MCCGuiScreen screen, int cursorX, int cursorY) {
		if (chat == null)
			return;
		
		chat.setFlash(false);
		chat.setShowing(true);
		
		screen.drawStringCentered(MCCLanguageText.CHAT.toString() + " - " + chat.getTitle(), posX + (width / 2), posY + 5);
		final String[] MESSAGES = chat.getMessages();
		
		if (chat.getInput() == null)
			chat.setInput("");
		
		final List<String> EXTRA = new ArrayList<String>(MCC.MINECRAFT.fontRenderer.listFormattedStringToWidth(chat.getInput(), width - 20));
		if (chat.getTyping())
			EXTRA.add(0, MCCColour.GREY.toString() + MCCColour.ITALIC + (String)chat.getTitle() + " is typing...");
		
		for (int i = 0; i != MESSAGES.length; i++) {
			if (timestamp) {
				MESSAGES[i] = MESSAGES[i].replaceAll(Character.toString('\u2022'), "");
			}else{
				MESSAGES[i] = MESSAGES[i].split(Character.toString('\u2022'))[1];
			}
		}
		ArrayUtils.reverse(MESSAGES);
		
		int overflow = EXTRA.size() - 1;
		int lines = ((height - 20) / 9) - (overflow);
		int linesWritten = 0;
		int linesIndex = 0;
		while (linesWritten < lines && linesIndex < MESSAGES.length) {
			final ArrayList<String> MESSAGE = new ArrayList<String>();
			MESSAGE.addAll(MCC.MINECRAFT.fontRenderer.listFormattedStringToWidth(MESSAGES[linesIndex], width));
			while (MESSAGE.size() > 0 && linesWritten < lines){
				linesWritten++;
				int index = MESSAGE.size() - 1;
				int y = posY + (lines * 9) - (linesWritten * 9) + 14;
				screen.drawString(MESSAGE.get(index), posX, y);
				MESSAGE.remove(index);
			}
			linesIndex++;
		}
		linesIndex = 0;
		linesWritten = 0;
		for (String s : EXTRA) {
			if (System.currentTimeMillis() % 1000 >= 500 && selected && linesIndex == EXTRA.size() - 1)
				s = s + "_";
			int y = 14 + posY + ((lines + overflow) * 9) - (overflow * 9) + (linesWritten * 9);
			screen.drawString("> " + s, posX, y, selected ? 0xFFFF00 : 0xAAAAAA);
			linesIndex++;
			linesWritten++;
		}
	}

	@Override
	public String getInput() {
		if (chat == null)
			return "";
		
		return chat.getInput();
	}

	@Override
	public String setInput(String i) {
		if (chat != null)
			chat.setInput(i);
		return getInput();
	}

	@Override
	public void sendInput() {
		if (chat != null)
			chat.send(getInput());
	}

	@Override
	public void closed() {
		if (chat != null)
			chat.setShowing(false);
	}
	
}
