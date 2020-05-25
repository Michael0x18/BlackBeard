package world.fx;

/**
 * Formatted like a set of eZ80 romcalls.
 * 
 * @author Michael Ferolito
 *
 */
public class _Mixer extends Thread {

	/**
	 * Starts the routine. Just call this.
	 */
	public static void _beginRoutine() {
		_Mixer m = new _Mixer();
		m.checkAccess();
	}

	/**
	 * Extension of default, but Daemon.
	 */
	public _Mixer() {
		this.setDaemon(true);
		start();
	}

	/**
	 * Runs a SoundClip.
	 */
	public void run() {
		SoundClip s = new SoundClip("Client/Resources/Sounds/OnTheHighSeas.wav");
		while (true) {
			s.play();
		}
	}

}
