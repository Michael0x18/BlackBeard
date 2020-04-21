package world.io;

import com.ferolito.UnsafeWrapper;
import sun.misc.Unsafe;

public class Handle {

	public static Unsafe getUnsafe() {
		return UnsafeWrapper.getUnsafe();
	}

}
