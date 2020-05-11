package world.viewport;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class ObjectLoaderV_C {
	static CopyOnWriteArrayList<String> loadHandles = new CopyOnWriteArrayList<String>();
	public static ConcurrentHashMap<String,Texture> loaderHash = new ConcurrentHashMap<String,Texture>();

	
	static void load(GL gl) {
		for(String s: loadHandles) {
			gl.glEnable(GL2.GL_TEXTURE_2D);
		      try{
				
		         File im = new File(s);
		         Texture t = TextureIO.newTexture(im, true);
//		         t.hashCode();
		         //loaderHash.put(t.getTextureObject(gl));
		          loaderHash.put(s, t);
		      }catch(IOException e){
		         e.printStackTrace();
		      }
		}
	}
	
	public static synchronized int addLoaderHandleInstance(String filePath) {
		loadHandles.add(filePath);
		return loadHandles.indexOf(filePath);
	}
	
}
