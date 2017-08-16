package packet;

import util.IDList;

public class PacketList
{
	public static IDList<Class <? extends Packet>> packet = new IDList<Class <? extends Packet>>();

	public static Class <? extends Packet> getPacketForID(int id)
	{
		return packet.getObjectForID(id);
	}

	public static int getIDForPacket(Class <? extends Packet> c)
	{
		return packet.getIDForObject(c);
	}
	static
	{
		packet.add(DPacketPing.class, 1);
		packet.add(CPacketJoinServer.class, 2);
		packet.add(CPacketServerClosed.class, 3);
		packet.add(DPacketPlayerConnect.class, 4);
		packet.add(SPacketQuitGame.class, 5);
		packet.add(CPacketPlayerQuit.class, 6);
		packet.add(CPacketMovement.class, 7);
		packet.add(SPacketMovement.class, 8);
		packet.add(CPacketRotation.class, 9);
		packet.add(SPacketRotation.class, 10);
	}
}
