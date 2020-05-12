package world.fx;

public class _Mixer extends Thread{

	public static void _beginRoutine() {
		_Mixer m = new _Mixer();
	}
	
	public _Mixer() {
		this.setDaemon(true);
		start();
	}
	
	public void run() {
		SoundClip s = new SoundClip("Client/Resources/Sounds/OnTheHighSeas.wav");
		while(true) {
			s.play();
		}
	}
	
}
