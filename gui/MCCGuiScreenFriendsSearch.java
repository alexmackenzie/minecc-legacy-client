package co.minecc.client.gui;

import org.lwjgl.input.Keyboard;

import co.minecc.client.MCCFriend;
import co.minecc.client.gui.elements.MCCGuiFriend;
import co.minecc.client.gui.elements.MCCGuiText;

public class MCCGuiScreenFriendsSearch extends MCCGuiScreen {

	public MCCGuiText text;
	public String textLast;
	public MCCGuiFriend[] friends;
	public boolean friendsRefresh;
	public boolean friendsExcess;
	public int friendsResults = 0;
	
	public MCCGuiScreenFriendsSearch(MCCGuiScreen p) {
		super(p);
	}

	@Override
	public void init() {
		Keyboard.enableRepeatEvents(true);
		ELEMENTS.add(text = new MCCGuiText(0, 4, 30, 200, null));
		text.selected = true;
		text.selectedLock = true;
		textLast = text.getInput();
		int size = (height - 80) / 25;
		friends = new MCCGuiFriend[size];
		for (int i = 0; i != friends.length; i++) {
			ELEMENTS.add(friends[i] = new MCCGuiFriend(i + 1, 4, 60 + (i * 25), null));
		}
		friendsRefresh = true;
		friendsExcess = false;
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		if (friendsRefresh = friendsRefresh || !text.getInput().equals(textLast)) {
			textLast = text.getInput();
			friendsRefresh = false;
			friendsExcess = false;
			final String SEARCH = text.getInput();
			final MCCFriend[] RESULTS = MCCFriend.getSearch(SEARCH);
			friendsResults = RESULTS.length;
			if (RESULTS.length > friends.length)
				friendsExcess = true;
			else
				for (int i = 0; i != friends.length; i++)
					friends[i].friend = i >= RESULTS.length ? null : RESULTS[i];
		}
		super.draw(cursorX, cursorY);
		drawStringCentered("Friend Search", width / 2, 10);
		if (friendsExcess) {
			for (MCCGuiFriend f : friends)
				f.friend = null;
			drawStringCentered("Too many results!", width / 2, 20);
		}else if (friendsResults == 0) {
			drawStringCentered("No results!", width / 2, 20);
		}
	}
	
	@Override
	public void close() {
		Keyboard.enableRepeatEvents(false);
		super.close();
	}

}
