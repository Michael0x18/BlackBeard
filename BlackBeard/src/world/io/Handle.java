package world.io;

import com.ferolito.UnsafeWrapper;
import sun.misc.Unsafe;

/**
 * WARNING- USE THIS AT YOUR OWN RISK. This class provides a static method that
 * calls the UnsafeWrapper included with the Unsafe.jar classes. That class
 * references sun.misc.Unsafe.
 * 
 * sun.misc.Unsafe has over a hundred methods. Only a small subset of them are
 * used. Some of the most notable are: AllocateMemory, FreeMemory, CompareAndSwap...
 * 
 * @author Michael Ferolito
 * @version 1
 * @since 2
 *
 */
public class Handle {

	public static Unsafe getUnsafe() {
		return UnsafeWrapper.getUnsafe();
	}
	

}
