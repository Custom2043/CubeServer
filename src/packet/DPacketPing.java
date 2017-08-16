package packet;

import java.io.IOException;

import client.ClientMain;
import main.Account;
import util.CustomInputStream;
import util.CustomOutputStream;

public class DPacketPing extends Packet implements Packet.PacketDoubleSide
{
	public DPacketPing(){}
	public void read(CustomInputStream cis) throws IOException {}
	public void write(CustomOutputStream cos) throws IOException {}
	public void handleClient() 
	{
		ClientMain.compte.ping = (int)ClientMain.compte.timer.getDifference()/2;
	}
	@Override
	public void handleServer(Account compte)
	{
		new DPacketPing().send(compte.socket);
		compte.timer.set0();
	}
	public boolean toWrite()
	{
		return false;
	}
}
