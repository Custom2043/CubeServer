package packet;

import java.io.IOException;

import main.Account;
import main.ClientMain;
import main.Cube;
import util.CustomInputStream;
import util.CustomOutputStream;

public class DPacketPlayerConnect extends Packet implements Packet.PacketDoubleSide
{
	public Cube acc;
	
	public DPacketPlayerConnect(){}
	public DPacketPlayerConnect(Cube a){acc = a;}
	
	@Override
	public void read(CustomInputStream cis) throws IOException 
	{
		acc = new Cube(cis.readString());
	}

	@Override
	public void write(CustomOutputStream cos) throws IOException 
	{
		cos.writeString(acc.name);
	}

	@Override
	public void handleClient() {
		System.out.println(acc.name+" se connecte");
		ClientMain.game.cubes.add(acc);
	}
	@Override
	public void handleServer(Account compte) {}

}
