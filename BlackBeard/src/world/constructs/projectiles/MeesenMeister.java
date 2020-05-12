package world.constructs.projectiles;

public class MeesenMeister {

	public static Projectile yeet(String s) {
		String[] args = s.split("`");
		if(args[0].equals("MusketShot")){
			return new MusketShot(Double.parseDouble(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3]));
		}
		return new CannonBall(Double.parseDouble(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3]));
	}
	
}
