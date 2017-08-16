package packet;

import java.io.IOException;

import client.ClientMain;
import main.Cube;
import packet.Packet.PacketToClient;
import util.CustomInputStream;
import util.CustomOutputStream;

public class CPacketMovement extends Packet implements PacketToClient
{
	public String cubeName;
	public double xStart, yStart;
	public byte direction;
	public CPacketMovement(){}
	public CPacketMovement(Cube c)
	{
		cubeName = c.name;
		xStart = c.x;
		yStart = c.z;
		direction = c.direction;
	}
	@Override
	public void read(CustomInputStream cis) throws IOException 
	{
		cubeName = cis.readString();
		xStart = cis.readDouble();
		yStart = cis.readDouble();
		direction = cis.readByte();
	}

	@Override
	public void write(CustomOutputStream cos) throws IOException 
	{
		cos.writeString(cubeName);
		cos.writeDouble(xStart);
		cos.writeDouble(yStart);
		cos.writeByte(direction);
	}
	@Override
	public void handleClient() 
	{
		Cube c = ClientMain.game.getCube(cubeName);
		c.x = xStart;
		c.z = yStart;
		c.direction = direction;
	}
}
