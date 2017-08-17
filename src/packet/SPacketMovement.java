package packet;

import java.io.IOException;

import main.Account;
import main.Cube;
import server.ServerMain;
import util.CustomInputStream;
import util.CustomOutputStream;

public class SPacketMovement extends Packet implements Packet.PacketToServer
{
	public double xStart, yStart;
	public byte direction;
	public boolean toWrite(){return false;}
	public SPacketMovement(){}
	public SPacketMovement(Cube c)
	{
		xStart = c.x;
		yStart = c.z;
		direction = c.direction;
	}
	@Override
	public void read(CustomInputStream cis) throws IOException 
	{
		xStart = cis.readDouble();
		yStart = cis.readDouble();
		direction = cis.readByte();
	}

	@Override
	public void write(CustomOutputStream cos) throws IOException 
	{
		cos.writeDouble(xStart);
		cos.writeDouble(yStart);
		cos.writeByte(direction);
	}
	@Override
	public void handleServer(Account acc) 
	{
		acc.cube.x = xStart;
		acc.cube.z = yStart;
		acc.cube.direction = direction;
		ServerMain.server.sendPacketToAllPlayersExcept(new CPacketMovement(acc.cube), acc.cube.name);
	}
}
