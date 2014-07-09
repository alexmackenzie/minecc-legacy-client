package co.minecc.client.gui;

import java.util.ArrayList;

import co.minecc.client.MCC;
import co.minecc.client.MCCFriend;
import co.minecc.client.gui.elements.MCCGuiButton;
import co.minecc.client.gui.elements.MCCGuiElement;
import co.minecc.client.gui.elements.MCCGuiFriend;
import co.minecc.client.gui.elements.MCCGuiSlider;
import co.minecc.client.gui.elements.MCCGuiSlider.MCCGuiSliderType;
import co.minecc.client.language.MCCLanguageText;

public class MCCGuiScreenFriends extends MCCGuiScreen {
	
	public MCCGuiButton buttonSearch;
	public MCCGuiButton buttonAdd;
	public MCCGuiButton buttonRequests;
	
	private static MCCGuiSlider slider = new MCCGuiSlider(1024, 0, 0, 0, MCCGuiSliderType.VERTICAL);
	private static int sliderW = 0, sliderH = 0;
	
	private ArrayList<MCCGuiFriend> buttonFriends = new ArrayList<MCCGuiFriend>();
	
	public MCCGuiScreenFriends(MCCGuiScreen p) {
		super(p);
	}
	
	@Override
	public void init() {
		updateContent();
		if (sliderW != width || sliderH != height){
			slider.reset(width - 92, 4, height - 28);
			sliderW = width;
			sliderH = height;
		}
		ELEMENTS.add(slider);
		ELEMENTS.add(buttonSearch = new MCCGuiButton(0, width - 80, 4, 70, MCCLanguageText.FRIENDS_SEARCH));
		ELEMENTS.add(buttonAdd = new MCCGuiButton(1, width - 80, 29, 70, MCCLanguageText.FRIENDS_ADD));
		ELEMENTS.add(buttonRequests = new MCCGuiButton(2, width - 80, 54, 70, ""));
	}
	
	@Override
	public void draw(int cursorX, int cursorY) {
		buttonRequests.enabled = MCC.DATA.requests.size() >= 1;
		buttonRequests.textRaw = MCCLanguageText.FRIENDS_REQUESTS + " [" + MCC.DATA.requests.size() + "]";
		buttonRequests.flash = buttonRequests.enabled;
		update();
		super.draw(cursorX, cursorY);
		final MCCFriend[] FRIENDS = MCCFriend.getFriends();
		if (!MCC.MCC.getFriendsLoaded()){
			drawStringCentered(MCCLanguageText.FRIENDS_LOADING.toString(), (width / 2) - (width / 5), (height / 2) - 5, 0xFFFFFF);
		}else if (FRIENDS.length == 0){
			drawStringCentered(MCCLanguageText.FRIENDS_NONE.toString(), (width / 2) - (width / 5), (height / 2) - 5, 0xFFFFFF);
		}
	}
	
	public void update() {
		updateContent();
		
		final MCCGuiFriend[] ALL = buttonFriends.toArray(new MCCGuiFriend[]{});
		double scroll = (slider.slide() * ALL.length) * 25;
		for (int i = 0; i != ALL.length; i++){
			ALL[i].posX = 60;
			ALL[i].posY = (int) ((20 + (i * 25)) - scroll);
		}
	}
	
	public void updateContent() {
		while (buttonFriends.size() > 0){
			ELEMENTS.remove(buttonFriends.get(0));
			buttonFriends.remove(0);
		}
		
		for (MCCFriend f : MCCFriend.getFriends()){
			MCCGuiFriend e = new MCCGuiFriend(Long.MAX_VALUE, 0, 0, f);
			buttonFriends.add(e);
			ELEMENTS.add(e);
		}
	}
	
	@Override
	public void clicked(MCCGuiElement clicked) {
		super.clicked(clicked);
		if (clicked.id == buttonAdd.id) 
			MCC.MCC.showGui(new MCCGuiScreenFriendsAdd(this));
		else if (clicked.id == buttonRequests.id)
			MCC.MCC.showGui(new MCCGuiScreenFriendsRequests(this));
		else if (clicked.id == buttonSearch.id)
			MCC.MCC.showGui(new MCCGuiScreenFriendsSearch(this));
	}
		
}
