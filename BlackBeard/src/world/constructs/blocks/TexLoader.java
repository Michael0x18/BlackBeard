package world.constructs.blocks;

import world.viewport.ObjectLoaderV_C;

public class TexLoader {
	public static TexLoader runtimeExec;
	
	static {
		runtimeExec = new TexLoader();
	}
	
	public TexLoader() {
		System.out.println("true! THIS METHOD HAS BEEN CALLED AT RUNTIME");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Sailcloth/Sailcloth.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Stone/Stone.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Wood/Wood.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Wood/Wood2.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Grass/Grass.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Grass/Grass1.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Grass/Grass2.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Grass/Grass3.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Grass/Grass4.png");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Grass/Grass5.png");
	}

}
