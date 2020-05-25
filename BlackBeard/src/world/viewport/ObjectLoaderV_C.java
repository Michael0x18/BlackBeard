package world.viewport;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Loads textures from registered hooks and inserts them into a hash table.
 * 
 * @author Michael Ferolito, Dennis Huang
 *
 */
public class ObjectLoaderV_C {
	/**
	 * true if loaded.
	 */
	static boolean loaded = false;
	/**
	 * Concurrent volatile ArrayList for handles.
	 */
	static volatile CopyOnWriteArrayList<String> loadHandles = new CopyOnWriteArrayList<String>();
	/**
	 * Hash table that contains the textures.
	 */
	public static ConcurrentHashMap<String, Texture> loaderHash = new ConcurrentHashMap<String, Texture>();

	/**
	 * Loads all of the textures with the GL object.
	 * 
	 * @param gl
	 */
	static void load(GL gl) {
		for (String s : loadHandles) {
			gl.glEnable(GL2.GL_TEXTURE_2D);
			try {

				File im = new File(s);
				Texture t = TextureIO.newTexture(im, true);
//		         t.hashCode();
				// loaderHash.put(t.getTextureObject(gl));
				loaderHash.put(s, t);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loaded = true;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static synchronized int addLoaderHandleInstance(String filePath) {
		System.out.println("CALLED!!!!!");
		loadHandles.add(filePath);
		return loadHandles.indexOf(filePath);
	}

}
