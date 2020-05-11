package world.constructs.blocks;

import world.viewport.ObjectLoaderV_C;

public class TexLoader {
	public static TexLoader runtimeExec;
	
	public TexLoader() {
		System.out.println("true! THIS METHOD HAS BEEN CALLED AT RUNTIME");
		ObjectLoaderV_C.addLoaderHandleInstance("Client/Resources/Sailcloth/Sailcloth.png");
	}

}
