package co.minecc.client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import net.minecraft.client.multiplayer.ServerData;

import co.minecc.client.gui.MCCGuiScreen;
import co.minecc.client.gui.MCCGuiScreenFriendsAdd;
import co.minecc.client.gui.MCCGuiScreenOutdated;
import co.minecc.client.language.MCCLanguageText;
import co.minecc.client.packets.MCCPacket;
import co.minecc.client.packets.MCCPacketAlive;
import co.minecc.client.packets.MCCPacketMultiplayer;

public class MCCConnection implements Runnable {

	private static boolean firstConnection = true;
	
	private boolean connected = false;
	private boolean login = false;
	
	private Socket socket;
	private DataInputStream input;
	private MCCConnectionInput inputHandler;
	private Thread inputHandlerThread;
	private DataOutputStream output;
	private MCCConnectionOutput outputHandler;
	private Thread outputHandlerThread;
	private final boolean PAUSE;
	
	protected long written = 0L;
	protected long read = 0L;
	protected long skipped = 0L;
	
	public MCCConnection(boolean p){
		PAUSE = p;
		final Thread THREAD = new Thread(this);
		THREAD.setName("MCC Connection");
		THREAD.setPriority(Thread.NORM_PRIORITY);
		THREAD.start();
	}

	@Override
	public void run() {
		if (PAUSE){
			try{
				Thread.sleep(2000);
			}catch (Exception e){}
		}
		MCC.OVERLAY.notification(MCCLanguageText.CONNECTING.toString(), "");
		try {
			socket = new Socket(MCC.DOMAIN_SERVER, MCC.PORT);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			MCC.OVERLAY.notification(MCCLanguageText.CONNECTING.toString(), MCCLanguageText.CONNECTING_VALIDATE_PROTOCOL.toString());
			connected = true;
			output.writeByte(MCC.PROTOCOL);
			output.flush();
			if (input.readBoolean()){
				MCC.OVERLAY.notification(MCCLanguageText.CONNECTING.toString(), MCCLanguageText.CONNECTING_VALIDATE_VERSION.toString());
				output.writeShort(MCC.VERSION);
				output.flush();
				final short VERSION_DIFFERENCE = input.readShort();
				MCC.DATA.outdated = VERSION_DIFFERENCE;
				if (input.readBoolean()){
					MCC.OVERLAY.notification(MCCLanguageText.CONNECTING.toString(), MCCLanguageText.CONNECTING_VALIDATE_LOGIN.toString());
					/** Mojang Login **/
					final boolean LOGIN = input.readBoolean();
					try {
						final String KEY = input.readUTF();
						if (LOGIN)
							MCCSession.login(KEY);
						output.writeBoolean(true);
						output.flush();
					}catch (Exception e) {
						output.writeBoolean(false);
						output.flush();
						throw new Exception(MCCLanguageText.CONNECTING_BAD_LOGIN.toString());
					}
					output.writeUTF(MCCSession.getUsername());
					output.flush();
					if (!input.readBoolean()) {
						throw new Exception(input.readUTF());
					}
					/** Login Complete **/
					MCC.DATA.server = input.readUTF();
					MCC.DATA.connection = input.readLong();
					MCC.DATA.id = input.readUTF();
					System.out.println("Server: " + MCC.DATA.server);
					if (input.readBoolean()) {
						
					}
					login = true;
					inputHandler = new MCCConnectionInput(input);
					inputHandlerThread = new Thread(inputHandler);
					inputHandlerThread.setName("MCC Connection Input");
					inputHandlerThread.setPriority(Thread.MAX_PRIORITY);
					inputHandlerThread.start();
					outputHandler = new MCCConnectionOutput(output);
					outputHandlerThread = new Thread(outputHandler);
					outputHandlerThread.setName("MCC Connection Output");
					outputHandlerThread.setPriority(Thread.MAX_PRIORITY);
					outputHandlerThread.start();
					MCC.OVERLAY.notification(MCCLanguageText.CONNECTED.toString(), MCCLanguageText.CONNECTED_KEY.toString());
					if (VERSION_DIFFERENCE >= 1 && firstConnection){
						MCC.MCC.showGui(new MCCGuiScreenOutdated(MCCGuiScreen.active()));
					}
					
					ServerData multiplayer = MCC.MCC.multiplayer;
					send(new MCCPacketMultiplayer(multiplayer == null ? "" : multiplayer.serverIP));
					firstConnection = false;
				}else{
					if (VERSION_DIFFERENCE > 0 && firstConnection){
						MCC.MCC.showGui(new MCCGuiScreenOutdated(null));
						firstConnection = false;
						throw new Exception(MCCLanguageText.CONNECTING_BAD_CLIENT.toString());
					}else if (VERSION_DIFFERENCE < 0){
						throw new Exception(MCCLanguageText.CONNECTING_BAD_SERVER.toString());
					}else{
						throw new Exception(MCCLanguageText.CONNECTING_BAD_VERSION.toString());
					}
				}
			}else{
				throw new Exception(MCCLanguageText.CONNECTING_BAD_PROTOCOL.toString());
			}
		}catch (Exception e){
			if (MCC.DEBUG || MCC.DEBUG_DC){
				e.printStackTrace();
			}
			MCC.OVERLAY.notification(MCCLanguageText.CONNECTING_FAILED.toString(), e.getMessage());
			disconnect();
		}
	}
	
	public void disconnect(){
		MCC.MCC.onDisconnect();
		if (!connected){return;}
		connected = false;
		login = false;
		try{inputHandler.stop();}catch(Exception e){}
		try{outputHandler.stop();}catch(Exception e){}
		try{input.close();}catch(Exception e){}
		try{output.close();}catch(Exception e){}
		try{socket.close();}catch(Exception e){}
		
		if (!MCCGuiScreenFriendsAdd.getStatusResult()) {
			MCCGuiScreenFriendsAdd.setStatus("Connection lost!", true);
		}
	}
		
	public boolean loggedIn(){
		return login;
	}
	
	public boolean send(MCCPacket p){
		if (outputHandler != null && p != null){
			outputHandler.send(p);
			return true;
		}
		return false;
	}
	
	public void keepAlive(){
		try{
			outputHandler.keepAlive();
		}catch (Exception e){}
	}
	
	public String[] stats(){
		return new String[]{
				MCCLanguageText.STATS_C_ID.toString() + ": #" + MCC.DATA.connection,
				"",
				MCCLanguageText.STATS_C_READ.toString() + ": " + read,
				MCCLanguageText.STATS_C_WRITTEN.toString() + ": " + written,
				MCCLanguageText.STATS_C_QUEUED.toString() + ": " + outputHandler.queue(),
				MCCLanguageText.STATS_C_IGNORED.toString() + ": " + skipped,
		};
	}
	
}
class MCCConnectionInput implements Runnable {

	public final DataInputStream STREAM;
	
	private boolean run = false;
	
	MCCConnectionInput(DataInputStream s){
		STREAM = s;
	}
	
	@Override
	public void run() {
		run = true;
		while (run){
			try {
				final byte ID = STREAM.readByte();
				final MCCPacket PACKET = MCCPacket.get(ID);
				final byte[] DATA = new byte[STREAM.readInt()];
				for (int i = 0; i != DATA.length; i++)
					DATA[i] = STREAM.readByte();
				
				if (PACKET != null) {
					final ByteArrayInputStream DATA_INPUT = new ByteArrayInputStream(DATA);
					PACKET.read(new DataInputStream(DATA_INPUT));
					MCC.MCC.getConnection().read++;
					PACKET.handle();
					if (MCC.DEBUG){
						System.out.println("MCC>INP>" + PACKET.getClass().getName());
					}
				}else if (MCC.DEBUG){
					System.out.println("MCC>SKP>" + ID);
				}
				
			}catch (Exception e){
				if (MCC.DEBUG || MCC.DEBUG_DC){
					e.printStackTrace();
				}
				MCC.MCC.disconnect(MCCLanguageText.CONNECTED_ERROR.toString());
			}
		}
	}
	
	public void stop(){
		run = false;
	}
	
}
class MCCConnectionOutput implements Runnable {

	public final DataOutputStream STREAM;
	private final Object LOCK = new Object();
	private final ArrayList<MCCPacket> QUEUE = new ArrayList<MCCPacket>();
	
	private boolean run = false;
	private boolean keep = false;
	private int keepTimer = 0;
	
	MCCConnectionOutput(DataOutputStream s){
		STREAM = s;
	}
	
	@Override
	public void run() {
		run = true;
		while (run){
			try{
				synchronized (QUEUE) {
					while (QUEUE.size() > 0){
						final MCCPacket PACKET = QUEUE.get(0);
						STREAM.writeByte(PACKET.id());
						PACKET.write(STREAM);
						MCC.MCC.getConnection().written++;
						QUEUE.remove(0);
						keep = true;
						if (MCC.DEBUG){
							System.out.println("MCC>OUT>" + PACKET.getClass().getName());
						}
					}
				}
				synchronized (LOCK) {
					LOCK.wait(3250);
				}
			}catch (Exception e){
				if (MCC.DEBUG || MCC.DEBUG_DC){
					e.printStackTrace();
				}
				MCC.MCC.disconnect(MCCLanguageText.CONNECTED_ERROR.toString());
			}
		}
	}
	
	public void stop(){
		run = false;
	}
	
	public void send(MCCPacket p){
		synchronized (QUEUE) {
			QUEUE.add(p);
		}
		synchronized (LOCK) {
			LOCK.notifyAll();
		}
	}
	
	public void keepAlive(){
		keepTimer--;
		if (keepTimer > 0){return;}
		if (!keep){
			send(new MCCPacketAlive());
		}
		keep = false;
		keepTimer = 30;
	}
	
	public int queue(){
		synchronized (QUEUE) {
			return QUEUE.size();
		}
	}
	
}