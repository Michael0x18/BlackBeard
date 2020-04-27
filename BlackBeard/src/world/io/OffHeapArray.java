package world.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * EXTREME WARNING!
 * 
 * This class creates an off heap array. These arrays are special because you
 * can ditch all references to them, then increment a pointer to get back to
 * them. They are no managed by the JVM heap, so MAKE SURE YOU CALL THE
 * DESTRUCTOR METHOD OR THERE WILL BE MEMORY LEAKS AND YOU WILL BE PISSED.
 * 
 * @author Michael Ferolito
 *
 */
public class OffHeapArray {
	private final static int BYTE = 1;
	private long bin = BYTE;
	private long size;
	private long address;

	/**
	 * Creates a new OffHeapArray instance. Calls native routine malloc through Unsafe reflective access to allocate a bin size of 1 byte per bin.
	 * @param size
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public OffHeapArray(long size) throws NoSuchFieldException, IllegalAccessException {
		this.size = size;
		address = getUnsafe().allocateMemory(size * BYTE);
	}

	/**
	 * Creates a new OffHeapArray instance with specified bin size.
	 * @param size
	 * @param bin
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public OffHeapArray(long size, long bin) throws IllegalAccessException, NoSuchFieldException {
		this.size = size;
		this.bin = bin;
		address = getUnsafe().allocateMemory(size * bin);
	}

	/**
	 * Internal- crashes if Unsafe cannot be accessed.
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	private Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
		Field f = Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		return (Unsafe) f.get(null);
	}

	/**
	 * mutator method, puts byte in the bin.
	 * @param i
	 * @param value
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public void setByte(long i, byte value) throws NoSuchFieldException, IllegalAccessException {
		getUnsafe().putByte(address + i * bin, value);
	}
	
	public void setChar(long i, char value) throws NoSuchFieldException, IllegalAccessException {
		getUnsafe().putChar(address + i * bin, value);
	}
	
	public void setShort(long i, int value) throws NoSuchFieldException, IllegalAccessException {
		getUnsafe().putInt(address + i * bin, value);
	}
	
	public void setLong(long i, long value) throws NoSuchFieldException, IllegalAccessException {
		getUnsafe().putLong(address + i * bin, value);
	}
	
	public void setObject(long i, Serializable value) throws NoSuchFieldException, IllegalAccessException, IOException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bs);
		os.writeObject(value);
		os.flush();
		Unsafe u = getUnsafe();
		byte[] b = bs.toByteArray();
		for(int j = 0; j < b.length; j++) {
			u.putByte(i+j, b[j]);
		}
		//getUnsafe().putObject(address + i * bin, value);
	}

	public byte getByte(long idx) throws NoSuchFieldException, IllegalAccessException {
		return getUnsafe().getByte(address + idx * bin);
	}
	
	public int getInt(long idx) throws NoSuchFieldException, IllegalAccessException {
		return getUnsafe().getInt(address + idx * bin);
	}
	
	public char getChar(long idx) throws NoSuchFieldException, IllegalAccessException {
		return getUnsafe().getChar(address + idx * bin);
	}

	public long getLong(long idx) throws NoSuchFieldException, IllegalAccessException {
		return getUnsafe().getLong(address + idx * bin);
	}
	
	public short getShort(long idx) throws NoSuchFieldException, IllegalAccessException {
		return getUnsafe().getShort(address + idx * bin);
	}
	
	public long size() {
		return size;
	}
	
	public long getPointer() {
		return address;
	}
	
	

	public void freeMemory() throws NoSuchFieldException, IllegalAccessException {
		getUnsafe().freeMemory(address);
	}
}