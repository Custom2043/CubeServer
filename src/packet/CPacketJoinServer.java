package packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.ClientMain;
import main.Account;
import main.Cube;
import util.CustomInputStream;
import util.CustomOutputStream;
import util.CustomTimer;

public class CPacketJoinServer extends Packet implements Packet.PacketToClient
{
	public ArrayList<Cube> acc = new ArrayList<Cube>();
	public CPacketJoinServer(){}
	public CPacketJoinServer(List<Account> a)
	{
		for (Account aa : a)
			acc.add(aa.cube);
	}
	@Override
	public void read(CustomInputStream cis) throws IOException 
	{
		int j = cis.readInt();
		for (int i=0;i<j;i++)
			acc.add(new Cube(cis.readString(), cis.readDouble(), cis.readDouble()));
	}

	@Override
	public void write(CustomOutputStream cos) throws IOException 
	{
		cos.writeInt(acc.size());
		for (Cube a : acc)
		{
			cos.writeString(a.name);
			cos.writeDouble(a.x);
			cos.writeDouble(a.z);
		}
	}

	@Override
	public void handleClient() 
	{
		ClientMain.startNewGame();
		for (Cube j : acc)
		{
			System.out.println(j.name+" est connecté");
			ClientMain.game.cubes.add(j);
		}
		ClientMain.game.cubes.add(ClientMain.compte.cube);
		ClientMain.compte.timer = new CustomTimer();
	}

}
