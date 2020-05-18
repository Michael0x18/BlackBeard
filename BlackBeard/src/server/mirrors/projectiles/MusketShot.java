package server.mirrors.projectiles;

import server.net.ClientHandler;
import server.util.MVector;

public class MusketShot extends Projectile{

	public MusketShot(MVector position, MVector velocity, ClientHandler source) {
		super(position, velocity,source);
	}

	@Override
	public String getSerialInfo() {
		return "MusketShot`"+x()+"`"+y()+"`"+z();
	}

}
