package packet;

import java.io.IOException;

import main.Cube;
import packet.Packet.PacketToClient;
import util.CustomInputStream;
import util.CustomOutputStream;
import client.ClientMain;

public class CPacketRotation extends Packet implements PacketToClient
{
	private float rot;private String name;
	public CPacketRotation(){}
	public CPacketRotation(Cube c){name = c.name;rot = c.angle;}
	public boolean toWrite(){return false;}

	@Override
	public void read(CustomInputStream cis) throws IOException 
	{
		rot = cis.readFloat();
		name = cis.readString();
	}

	@Override
	public void write(CustomOutputStream cos) throws IOException 
	{
		cos.writeFloat(rot);
		cos.writeString(name);
	}
	
	@Override
	public void handleClient() 
	{
		ClientMain.game.getCube(name).angle = rot;
	}
}
