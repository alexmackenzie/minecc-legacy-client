package co.minecc.client.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import co.minecc.client.MCCGroup;

public class MCCPacketGroup extends MCCPacket {

	public String id;
	public String title;
	public String[] members;
	public boolean participant;
	
	public MCCPacketGroup() {
	}
	
	public MCCPacketGroup(String t, String... m) {
		title = t;
		members = m;
	}
	
	@Override
	public void read(DataInputStream in) throws Exception {
		id = in.readUTF();
		title = in.readUTF();
		members = new String[in.readInt()];
		for (int i = 0; i != members.length; i++)
			members[i] = in.readUTF();
		participant = in.readBoolean();
	}

	@Override
	public void write(DataOutputStream out) throws Exception {
		out.writeUTF(title);
		out.writeInt(members.length);
		for (String m : members)
			out.writeUTF(m);
	}

	@Override
	public void handle() {
		final MCCGroup GROUP = MCCGroup.get(id);
		GROUP.setTitle(title);
		GROUP.setMembers(members);
		GROUP.setMember(participant);
	}

}
