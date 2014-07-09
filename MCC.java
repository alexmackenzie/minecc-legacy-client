package co.minecc.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.lwjgl.input.Keyboard;

import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.main.MCCGuiScreenMain;
import co.minecc.client.gui.main.MCCGuiScreenMainBase;
import co.minecc.client.language.MCCLanguageText;
import co.minecc.client.packets.MCCPacket;
import co.minecc.client.packets.MCCPacketMultiplayer;

public class MCC implements Runnable {
	
	public static final Minecraft MINECRAFT = Minecraft.getMinecraft();
	
	public static final short VERSION = 11;
	
	public static final String[] NEWS = new String[] {"", ""};
	
	public static final byte PROTOCOL = 2;
	public static final String PROTOCOL_NAME = "Lucy";
	
	public static final String URL_DONATION = "http://donate.minecc.co/category/213977";
	public static final String URL_PREMIUM = "http://donate.minecc.co/category/213988";
	public static final String URL_NEWS = "http://alexmack.net/minecc/news.txt";
	public static final String URL_UPDATE = "http://update.minecc.co";
	
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
	public static final String ALPHABET_NUMERIC = ALPHABET + "0123456789";
	
	public static final int PORT = 36676;
	public static final File DIRECTORY = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/minecc");
	public static final File KEYFILE = new File(DIRECTORY.getPath() + "/key.dat");
	
	public static final MCCData DATA = new MCCData();
	
	public static final boolean DEBUG = new File(DIRECTORY, "debug.dat").exists();
	public static final boolean DEBUG_DC = false;
	
	public static final String DOMAIN = (DEBUG ? "localhost" : "minecc.co");
	public static final String DOMAIN_SERVER = (DEBUG ? DOMAIN : "connect." + DOMAIN);
	
	static {
		DIRECTORY.mkdir();
		
		MCCPull.pull(URL_NEWS, new MCCPullHandler() {
			
			@Override
			public void onSuccess(String[] data) {
				NEWS[0] = data.length > 0 ? data[0] : "";
				NEWS[1] = data.length > 1 ? data[1] : "";
			}
			
			@Override
			public void onFailure(Exception e) {
				NEWS[0] = "Failed to load News!";
			}
		});
	}
	
	public static final MCCOverlay OVERLAY = new MCCOverlay(Minecraft.getMinecraft());
	public static final MCC MCC = new MCC();
	
	private MCCConnection connection;
	private int connectionRetry = 0;
	
	private boolean init = false;
	
	private final ArrayList<String> IGNORE = new ArrayList<String>();
	private final File IGNORE_FILE = new File(DIRECTORY.getPath() + "/ignore.dat");
	
	public void init() {
		if (init)
			return;
		
		System.out.println("MCC: " + DOMAIN);
		loadIgnore();
		init = true;
		MINECRAFT.guiAchievement = OVERLAY;
		MCCSession.set(MINECRAFT.getSession());
		connect(true);
		Thread t = new Thread(this);
		t.setName("MCC Timer");
		t.start();
		MCCChat.getChat(MCCStream.ANNOUNCEMENT).setTitle(MCCColour.ITALIC + "Announcements");
		MCCChat.getChat(MCCStream.ERROR).setTitle(MCCColour.ITALIC + "Errors");
		MCCChat.getChat(MCCStream.COMMANDLINE).setTitle(MCCColour.ITALIC + "Commandline");
		MCCLanguageText.CONNECTED_KEY.data("F4");
		MCCLanguageText.GUI_BANNED_CONTACT.data("support@minecraftconnect.co");
		MCCLanguageText.GUI_BANNED_EXIT.data("F4");
		MCCGuiScreen.init(new MCCGuiScreenMainBase());
	}
	
	public void connect(boolean p) {
		if (connection != null)
			return;
		
		connection = new MCCConnection(p);
	}
	
	private boolean key = false;
	
	public void onTick() {
		/** Check for MCC UI Key **/
		if (Keyboard.isKeyDown(Keyboard.KEY_F4) || (MINECRAFT.currentScreen instanceof MCCGuiScreen.MCCGuiScreenNative
				&& Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))) {
			if (!key){
				key = true;
				toggleGui();
			}
		}else{
			key = false;
		}
		
		/** Check for Minecraft Server **/
		ServerData server = MINECRAFT.func_147104_D();
		if (multiplayer == null && server != null) {
			send(new MCCPacketMultiplayer(server.serverIP));
		}else if (multiplayer != null && server == null) {
			send(new MCCPacketMultiplayer(""));
		}
		multiplayer = server;
		
		/** Check for Minecraft Server join command **/
		if (multiplayerJoin != null) {
			try{
	            MINECRAFT.theWorld.sendQuittingDisconnectingPacket();
	            MINECRAFT.loadWorld(null);
			}catch (Exception e){}
			
			if (multiplayerJoin.length() > 1)
		        MCC.showGuiMinecraft(new GuiConnecting(guiMCC, MINECRAFT, 
		        		new ServerData("MCC Join", multiplayerJoin)));
			
			multiplayerJoin = null;
		}
	}
	
	public ServerData multiplayer = null;
	public String multiplayerJoin = null;
		
	public GuiScreen guiMinecraft;
	public GuiScreen guiMCC = new co.minecc.client.gui.MCCGuiScreen.MCCGuiScreenNative();
	
	public void toggleGui() {
		final GuiScreen GUI = Minecraft.getMinecraft().currentScreen;
		if (GUI instanceof MCCGuiScreen.MCCGuiScreenNative){
			guiMCC = GUI;
			showGuiMinecraft(guiMinecraft);
		}else{
			guiMinecraft = GUI;
			showGuiMinecraft(guiMCC);
		}
	}
	
	public void showGui(MCCGuiScreen gui) {
		MCCGuiScreen.init(gui);
	}
	
	public void showGuiMinecraft(GuiScreen g) {
		Minecraft.getMinecraft().displayGuiScreen(g);
	}
	
	public void disconnect(String r) {
		if (connection != null){
			connection.disconnect();
			OVERLAY.notification(MCCLanguageText.DISCONNECTED.toString(), r);
		}
	}
	
	public void onDisconnect() {
		connection = null;
		connectionRetry = (int) (60 * (4.00 + new Random().nextDouble()));
	}
		
	public boolean isConnected() {
		return connection != null;
	}
	
	public boolean isLoggedIn() {
		return connection != null && connection.loggedIn();
	}
	
	public boolean send(MCCPacket p) {
		if (connection != null){
			return connection.send(p);
		}
		return false;
	}
		
	public boolean getFriendsLoaded() {
		if (connection == null)
			return false;
		
		return connection.loggedIn();
	}
		
	@Override
	public void run() {
		while (true){
			try{Thread.sleep(1000);}catch(Exception e){}
			if (!isConnected()){
				connectionRetry--;
				if (connectionRetry <= 0){
					connect(false);
				}
			}else{
				connection.keepAlive();
			}
			MCCGuiScreenMain.decreaseCooldown();
		}
	}
	
	public void join(String i) {
		multiplayerJoin = i;
	}
	
	public void setIgnore(String s, boolean i) {
		if (i && !IGNORE.contains(s)){
			IGNORE.add(s);
		}else if (!i && IGNORE.contains(s)){
			IGNORE.remove(s);
		}
		saveIgnore();
	}
	
	public boolean getIgnore(String s) {
		return IGNORE.contains(s);
	}
	
	public void saveIgnore() {
		try {
			final DataOutputStream OUTPUT = new DataOutputStream(new FileOutputStream(IGNORE_FILE));
			final String[] IGNORE_DATA = IGNORE.toArray(new String[]{});
			OUTPUT.writeInt(IGNORE_DATA.length);
			for (int i = 0; i != IGNORE_DATA.length; i++){
				OUTPUT.writeUTF(IGNORE_DATA[i]);
			}
			OUTPUT.close();
		}catch (Exception e){}
	}
	
	public void loadIgnore() {
		try {
			IGNORE.clear();
			final DataInputStream INPUT = new DataInputStream(new FileInputStream(IGNORE_FILE));
			final int SIZE = INPUT.readInt();
			for (int i = 0; i != SIZE; i++){
				IGNORE.add(INPUT.readUTF());
			}
			INPUT.close();
		}catch (Exception e){}
	}
	
	public MCCConnection getConnection() {
		return connection;
	}
	
}
