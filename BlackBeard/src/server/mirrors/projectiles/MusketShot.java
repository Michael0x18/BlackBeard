package server.mirrors.projectiles;

import server.util.MVector;

public class MusketShot extends Projectile{

	public MusketShot(MVector position, MVector velocity) {
		super(position, velocity);
	}

	@Override
	public String getSerialInfo() {
		return ":shot`MusketShot`"+x()+" "+y()+" "+z();
	}

}
