package packet;

import java.io.IOException;

import main.Account;
import main.Cube;
import packet.Packet.PacketToServer;
import server.ServerMain;
import util.CustomInputStream;
import util.CustomOutputStream;

public class SPacketRotation extends Packet implements PacketToServer
{
	private float rot;
	public SPacketRotation(){}
	public SPacketRotation(Cube c){rot = c.angle;}
	public boolean toWrite(){return false;}
	@Override
	public void read(CustomInputStream cis) throws IOException
	{
		rot = cis.readFloat();
	}

	@Override
	public void write(CustomOutputStream cos) throws IOException
	{
		cos.writeFloat(rot);
	}
	
	@Override
	public void handleServer(Account acc) 
	{
		acc.cube.angle = rot;
		ServerMain.server.sendPacketToAllPlayersExcept(new CPacketRotation(acc.cube), acc.cube.name);
	}
}
