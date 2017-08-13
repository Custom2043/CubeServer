package packet;

import java.io.IOException;

import main.Account;
import main.Main;
import util.CustomInputStream;
import util.CustomOutputStream;

public class SPacketQuitGame extends Packet implements Packet.PacketToServer
{
	public SPacketQuitGame(){}
	@Override
	public void read(CustomInputStream cis) throws IOException {
	}

	@Override
	public void write(CustomOutputStream cos) throws IOException {
	}

	@Override
	public void handleServer(Account compte) {
		Main.server.removePlayer(compte.cube.name);
	}

}
