package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class MCCPacket {

	private static final Map<Byte, Class<?>> MAP_CLASS = new HashMap<Byte, Class<?>>();
	private static final Map<Class<?>, Byte> MAP_ID = new HashMap<Class<?>, Byte>();
	private static final Map<Byte, Short> MAP_VERSION = new HashMap<Byte, Short>();
	private static final Map<Byte, Boolean> MAP_VALID = new HashMap<Byte, Boolean>();
	
	static {
		/** 0x0X: Protocol **/
		register(0x00, MCCPacketAlive.class);
		register(0x01, MCCPacketDisconnect.class);
		register(0x02, MCCPacketChat.class);
		register(0x03, MCCPacketChatTyping.class);
		/** 0x1X: General **/
		register(0x10, MCCPacketBanned.class);
		register(0x11, MCCPacketNew.class);
		register(0x12, MCCPacketNotification.class);
		register(0x13, MCCPacketMultiplayer.class);
		register(0x14, MCCPacketOS.class);
		register(0x15, MCCPacketStatus.class);
		register(0x16, MCCPacketMessage.class);
		register(0x17, MCCPacketTitle.class);
		/** 0x2X: Friend **/
		register(0x20, MCCPacketFriend.class);
		register(0x21, MCCPacketFriendDelete.class);
		register(0x22, MCCPacketFriendAdd.class);
		register(0x23, MCCPacketFriendRequest.class);
		register(0x24, MCCPacketFriendRequestDelete.class);
		/** 0x3X: Premium **/
		register(0x30, MCCPacketPremium.class);
		register(0x31, MCCPacketPremiumNeeded.class);
		/** 0x4X: Groups **/
		register(0x40, MCCPacketGroup.class);
		register(0x41, MCCPacketGroupInvite.class);
	}
	
	private static void register(int i, Class<? extends MCCPacket> c, short v) {
		i += Byte.MIN_VALUE;
		MAP_CLASS.put((byte) i, c);
		MAP_ID.put(c, (byte) i);
		MAP_VERSION.put((byte) i, v);
		MAP_VALID.put((byte) i, true);
	}
	
	private static void register(int i, Class<? extends MCCPacket> c) {
		register(i, c, (short) 0);
	}

	public static MCCPacket get(byte i) {
		try{
			return (MCCPacket) MAP_CLASS.get(i).newInstance();
		}catch (Exception e){
			return null;
		}
	}
	
	public static short getVersion(byte i) {
		if (!MAP_VERSION.containsKey(i))
			return 0;
		return MAP_VERSION.get(i).shortValue();
	}
	
	public static boolean getValid(byte i) {
		return MAP_VALID.get(i).booleanValue();
	}
	
	public static void setValid(byte i, boolean v) {
		MAP_VALID.put(i, v);
	}
	
	public final byte id() {
		return MAP_ID.get(getClass());
	}
	
	public abstract void read(DataInputStream in) throws Exception;
	public abstract void write(DataOutputStream out) throws Exception;
	public abstract void handle();
	
}
