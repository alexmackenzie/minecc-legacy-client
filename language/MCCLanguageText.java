package co.minecc.client.language;

public enum MCCLanguageText {
	
	YES("yes", "Yes"),
	NO("no", "No"),
	
	DISCONNECTED("disconnected", "Disconnected!"),
	DISCONNECTED_ERROR("disconnected.error", "You are not connected to MCC!"),
	DISCONNECTED_RECONNECT("disconnected.reconnect", "Reconnect"),
	
	CONNECTED("connected", "Connected!"),
	CONNECTED_KEY("connected.key", "Press [0] to begin!"),
	CONNECTED_ERROR("connected.error", "Connection Error!"),
	
	CONNECTING("connecting", "Connecting..."),
	CONNECTING_VALIDATE_PROTOCOL("connecting.validate.p", "Validating protocol..."),
	CONNECTING_VALIDATE_VERSION("connecting.validate.v", "Validating version..."),
	CONNECTING_VALIDATE_LOGIN("connecting.validate.l", "Logging in..."),
	CONNECTING_BAD_LOGIN("connecting.bad.l", "Bad login!"),
	CONNECTING_BAD_CLIENT("connecting.bad.c", "Outdated Client!"),
	CONNECTING_BAD_SERVER("connecting.bad.s", "Outdated Server!"),
	CONNECTING_BAD_VERSION("connecting.bad.v", "Invalid version!"),
	CONNECTING_BAD_PROTOCOL("connecting.bad.p", "Different protocol!"),
	CONNECTING_FAILED("connecting.failed", "Connection failed!"),
	CONNECTING_SYNC("connecting.sync", "Syncing protocol..."),
	
	STATS_C_ID("stats.c.id", "ID"),
	STATS_C_READ("stats.c.read", "Packets Read"),
	STATS_C_WRITTEN("stats.c.written", "Packets Written"),
	STATS_C_QUEUED("stats.c.queued", "Packets Queued"),
	STATS_C_IGNORED("stats.c.ignored", "Packets Ignored"),
	
	CHAT("chat", "Chat"),
	CHAT_ROOMS("chat.rooms", "Chat Rooms"),
	CHAT_IGNORE("chat.ignore", "Ignoring"),
	CHAT_IGNORE_INFO("chat.ignore.info", "You're now ignoring this chat. You won't " +
			"receive notifications."),
	CHAT_NOIGNORE("chat.noignore", "No longer ignoring"),
	CHAT_NOIGNORE_INFO("chat.noignore.info", "You're no longer ignoring this chat. You will " +
			"receive notifications."),
	
	GUI_BACK("gui.back", "Back"),
	GUI_BANNED("gui.banned", "You are banned from MineCC!"),
	GUI_BANNED_DATE("gui.banned.date", "Date"),
	GUI_BANNED_REASON("gui.banned.reason", "Reason"),
	GUI_BANNED_ADMIN("gui.banned.by", "By"),
	GUI_BANNED_UNBAN("gui.banned.unban", "Unban"),
	GUI_BANNED_MISTAKE("gui.banned.mistake", "Think this ban is a mistake?"),
	GUI_BANNED_CONTACT("gui.banned.contact", "Contact [0] with your IGN."),
	GUI_BANNED_EXIT("gui.banned.exit", "Press [0] to exit..."),
	GUI_CHANGELOG("gui.changelog", "Changelog"),
	GUI_CHANGELOG_DOWNLOAD("gui.changelog.download", "Downloading changelog..."),
	GUI_STORE("gui.store", "Store"),
	GUI_STORE_LINE0("gui.store.line.0", "MineCC is funded by donations,"),
	GUI_STORE_LINE1("gui.store.line.1", "every dollar is greatly appreciated!"),
	
	FRIENDS("friends", "Friends"),
	FRIENDS_SEARCH("friends.search", "Search"),
	FRIENDS_SEARCH_MIN("friends.search.min", "No results!"),
	FRIENDS_SEARCH_MAX("friends.search.max", "Too many results!"),
	FRIENDS_ADD("friends.add", "Add Friend"),
	FRIENDS_LOADING("friends.loading", "Loading Friends..."),
	FRIENDS_PROFILE_CHAT("friends.profile.chat", "Open Chat"),
	FRIENDS_PROFILE_JOIN("friends.profile.join", "Join Multiplayer"),
	FRIENDS_PROFILE_REMOVE("friends.profile.remove", "Remove Friend"),
	FRIENDS_NONE("friends.none", "You don't have any Friends yet!"),
	FRIENDS_REQUESTS("friends.requests", "Requests"),
	FRIENDS_REQUESTS_NONE("friends.requests.none", "No requests to display."),
	FRIENDS_REQUESTS_REQUEST("friends.requests.friendly", "wants to be Friends!"),
	FRIENDS_REQUESTS_OK("friends.requests.ok", "Add Friend"),
	FRIENDS_REQUESTS_DENY("friends.requests.no", "Remove Request"),
	FRIENDS_LOCATION("friends.location", "Location"),
	
	STATUS_ONLINE("status.online", "Online"),
	STATUS_ONLINE_YOU("status.online.you", "You are online."),
	STATUS_OFFLINE("status.offline", "Offline"),
	STATUS_OFFLINE_YOU("status.offline.you", "You are offline."),
	STATUS_INVISIBLE("status.invisible", "Invisible"),
	STATUS_INVISIBLE_YOU("status.invisible.you", "You are invisible."),
	
	PREMIUM("premium", "Premium"),
	PREMIUM_GET("premium.get", "Get Premium");
	
	public final String TAG;
	
	private String translation;
	private String[] data;
	
	MCCLanguageText(String t, String f){
		TAG = t;
		translation = f;
		data = new String[]{};
	}
	
	MCCLanguageText(String t){
		this(t, "404");
	}
	
	protected MCCLanguageText update(){
		synchronized (TAG) {
			for (int i = 0; i != data.length; i++){
				translation = translation.replaceAll("\\[" + i + "\\]", data[i]);
			}
		}
		return this;
	}
	
	public MCCLanguageText data(String... d){
		data = d;
		return update();
	}
	
	public MCCLanguageText set(String t){
		translation = t;
		return update();
	}
	
	@Override
	public String toString() {
		synchronized (TAG) {
			return translation;
		}
	}
	
}
