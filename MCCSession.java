package co.minecc.client;

import java.net.Proxy;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft.util.Session;

public class MCCSession {

	private static Session session;
	
	public static void set(Session s){
		session = s;
	}
	
	public static String getUsername(){
		return MCC.DEBUG || session == null
				? "Player501" : session.getUsername();
	}
	
	public static String getId(){
		return MCC.DEBUG || session == null
				? "sessionID" : session.getSessionID();
	}
	
	public static GameProfile getProfile() {
		return session.func_148256_e();
	}
	
	public static void login(String key) throws AuthenticationException {		
		final YggdrasilAuthenticationService YGGDRASIL = 
				new YggdrasilAuthenticationService(Proxy.NO_PROXY,
						UUID.randomUUID().toString());
		final MinecraftSessionService SERVICE = YGGDRASIL.createMinecraftSessionService();
		SERVICE.joinServer(getProfile(), session.getToken(), key);
	}
	
}
