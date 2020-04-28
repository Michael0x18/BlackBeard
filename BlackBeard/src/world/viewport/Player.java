package world.viewport;

import java.util.concurrent.CopyOnWriteArrayList;

import world.constants.declaration.MConstants;
import world.constructs.blocks.Clippable;
import world.constructs.blocks.Ship;

public class Player extends Camera {
	private double w, h, d;
	private boolean grounded;
	private double gravity;
	public double dx;
	public double dy;
	public double dz;
	private CopyOnWriteArrayList<Clippable> br3 = new CopyOnWriteArrayList<Clippable>();
	boolean allowJump = true;
	// private boolean gravityOn = true;

	// public static Chunk selectedChunk;

	public Player(JoglPane jp) {
		// speed is at .1f max
		this(0.5, 1.5, 0.5, .05f, .5f, .5f, .75f, MConstants.PI / 3f, 1000f, jp);
		this.position.y = 5;
	}

	/**
	 * It would appear that the Player is not covered under the Server license, but
	 * the wtfpl still applies.
	 * 
	 * @param w            Width of the player
	 * @param h            Height of the player
	 * @param d            Depth of the player
	 * @param speed        How fast the player moves
	 * @param xSensitivity Mouse sensitivity on the x-axis
	 * @param ySensitivity Mouse sensitivity on the y-axis
	 * @param friction     The amount of friction the player experiences while
	 *                     moving
	 * @param fov          The player's field of view
	 * @param viewDistance How far the player can look in the distance
	 * 
	 * 
	 */
	public Player(double w, double h, double d, double speed, double xSensitivity, double ySensitivity, double friction,
			double fov, double viewDistance, JoglPane jp) {
		super(speed, xSensitivity, ySensitivity, friction, fov, viewDistance, jp);
		this.w = w;
		this.h = h;
		this.d = d;
		grounded = false;
		// gravity = 0.0111f;
		gravity = 0.02f;
	}

	public void jump() {
		if (grounded) {
			grounded = false;
			vY += 0.18;
//			velocity.x *= 2.5;
//			velocity.z *= 2.5;
		}
		if (!gravityOn) {
			position.y += 0.01;
		}
	}

	/**
	 * Checks to see if the player is colliding with any of the Block objects inside
	 * the specified ArrayList
	 * 
	 * @param chunks ArrayList of Block objects to check collision with
	 */
	public void act(CopyOnWriteArrayList<Clippable> t) {
		// selectedChunk = chunks.get(0);
		// if ((int)Math.abs(c.getC1() - position.z / 32) == 0 &&
		// (int)Math.abs(c.getC2() - position.x / 32) == 0)
		// selectedChunk = c;

		grounded = false;

		for (Clippable b : t) {
			if (b != null) {
				// position is in the center of the so you have to add/substract
				// its (dimension in axis)/2 to get the edges
				double left = position.x - w / 2;
				double right = position.x + w / 2;
				double top = (double) (position.y + h / 8);
				double bottom = (double) (position.y - h / 2);
				double front = position.z + d / 2;
				double back = position.z - d / 2;
//				System.out.println();
//				System.out.println(top);
//				System.out.println(bottom);

				// block position is in the center of the block so you have to
				// add/substract its
				// dimensions/2 to get the edges
				double blockSize = b.getSize();
				double blockHeight = b.getSize();
				double blockLeft = b.getX() - blockSize / 2;
				double blockRight = b.getX() + blockSize / 2;
				double blockTop = b.getY() + blockHeight / 2;
				double blockBottom = b.getY() - blockHeight / 2;
				double blockFront = b.getZ() - blockSize / 2;
				double blockBack = b.getZ() + blockSize / 2;

//				if (b.containsPoint(position.x, top, position.z)) {
//					// move down
//					if (vY > 0) {
//						position.y = blockBottom - h / 8;
//						vY = 0;
//						// vY += gravity;
//						return;
//					}
//				}

				// Checks to see if any of the sides are in the block and move
				// the player
				// accordingly
				if (b.containsPoint(left, position.y, position.z)
						|| b.containsPoint(left, bottom + h / 4, position.z)) {
					// move right
					position.x = blockRight + w / 2;
					// System.out.println("Hit left line 118");

				} else if (b.containsPoint(right, position.y, position.z)
						|| b.containsPoint(right, bottom + h / 4, position.z)) {
					// move left
					position.x = blockLeft - w / 2;
					// System.out.println("Hit Right line 124");

				}

				if (b.containsPoint(position.x, top, position.z)) {
					// move down
					if (vY > 0) {
						position.y = blockBottom - h / 8;
						vY = 0;
						// vY += gravity;
						// System.out.println("Hit: UP line 134");

					}
				} else if (b.containsPoint(position.x, bottom, position.z)) {
					// move up/grounded
					if (vY < 0) {
						position.y = blockTop + h / 2;
						vY = 0;
						// System.out.println("Hit: DOWN line 142");
						grounded = true;

					}
				}

				if (position.y <= -5) {
					moveTo(2.5, 5, 2.5);
					vY = 0;
				}

				if (b.containsPoint(position.x, position.y, front) || b.containsPoint(position.x, bottom + h / 4, front)
						|| b.containsPoint(position.x, top - h / 4, front)) {
					// System.out.println("Hit Front line 155");
					// move back
					position.z = blockFront - d / 2;
				} else if (b.containsPoint(position.x, position.y, back)
						|| b.containsPoint(position.x, bottom + h / 4, back)
						|| b.containsPoint(position.x, top - h / 4, back)) {
					// move forward
					position.z = blockBack + d / 2;
					// System.out.println("Hit back Line 163");
				}
				
				double dirX = Math.cos(pan);
				double dirY = Math.tan(tilt);
				double dirZ = Math.sin(pan);
				
				//selection (buggy)
// 				for(int reach = 0; reach <=2; reach+=0.25) {
// 					MVector selection = getPosition().add(
// 							new MVector(reach*dirX, reach*dirY, reach*dirZ));
// 					for(int i = 0; i<Grid.world.size(); i++) {
// 						if (Grid.world.get(i).containsPoint(
// 								selection.getPosition()[0], selection.getPosition()[1], 
// 								selection.getPosition()[2])) {
// 							Grid.world.get(i).select(true);
// 						} else {
// 							Grid.world.get(i).select(false);
// 						}
// 					}
// 				}

			}
		}
		for (Ship s : Grid.ships) {
			double rad = Math.toRadians(s.rot);
			MVector position = new MVector();
			position.x = this.position.x;
			position.z = this.position.z;
			position.y = this.position.y;
			position.z = -position.z;
			position.x -= (s.x);
			position.z -= (s.z);
//			position.x = Math.cos(rad) * (position.x - s.x) - Math.sin(rad) * (position.z - s.z) + s.x;///////////////////////////////////
//			position.z = Math.sin(rad) * (position.x - s.x) + Math.cos(rad) * (position.z - s.z) + s.z;////////////////////////////////

			
			double newx = position.x*Math.cos(rad)-position.z*Math.sin(-rad);
			double newz = position.z*Math.cos(rad)+position.x*Math.sin(-rad);
			position.x = newx;
			position.z = newz;
			
			double r = Math
					.sqrt(((position.x - (s.x)) * ((position.x - (s.x))) + (position.z - s.z) * (position.z - (s.z))));
			if (Double.isNaN(r)) {
				r = 0.1;
			}
			//System.out.println(rad);

//			position.z += r * (Math.sin(rad));
//			position.x -= r * (Math.cos(rad));
//			double x = position.z;
//			position.z = -position.x;
//			position.x = x;
			// System.out.println((int)(position.x*100)/100.0+" "+(int)(s.x*100)/100.0);

			for (Clippable b : s.blocks) {
				if (b != null) {
					// position is in the center of the so you have to add/substract
					// its (dimension in axis)/2 to get the edges

//					System.out.println(position.x);
					double left = position.x - w / 2;
					double right = position.x + w / 2;
					double top = (double) (position.y + h / 8);
					double bottom = (double) (position.y - h / 2);
					double front = position.z + d / 2;
					double back = position.z - d / 2;
//				System.out.println();
//				System.out.println(top);
//				System.out.println(bottom);

					// block position is in the center of the block so you have to
					// add/substract its
					// dimensions/2 to get the edges
					double blockSize = b.getSize();
					double blockHeight = b.getSize();
					double blockLeft = b.getX() - blockSize / 2;
					double blockRight = b.getX() + blockSize / 2;
					double blockTop = b.getY() + blockHeight / 2;
					double blockBottom = b.getY() - blockHeight / 2;
					double blockFront = b.getZ() - blockSize / 2;
					double blockBack = b.getZ() + blockSize / 2;
//					double r = Math.sqrt((position.x - (s.x - 8) * (position.x - (s.x - 8))
//							+ (position.z - s.z) * (position.z - (s.z))));
//					position.x += r * (Math.PI + Math.cos(Math.toRadians(-s.rot)));
//					position.z += r * (Math.PI + Math.sin(Math.toRadians(-s.rot)));
					// System.out.println(position.x);
					// System.out.println(b.getX());
//				if (b.containsPoint(position.x, top, position.z)) {
//					// move down
//					if (vY > 0) {
//						position.y = blockBottom - h / 8;
//						vY = 0;
//						// vY += gravity;
//						return;
//					}
//				}

					// Checks to see if any of the sides are in the block and move
					// the player
					// accordingly
					if (b.containsPoint(left, position.y, position.z)
							|| b.containsPoint(left, bottom + h / 4, position.z)) {
						// move right
						position.x = blockRight + w / 2;
						// System.out.println("Hit left line 118");

					} else if (b.containsPoint(right, position.y, position.z)
							|| b.containsPoint(right, bottom + h / 4, position.z)) {
						// move left
						position.x = blockLeft - w / 2;
						// System.out.println("Hit Right line 124");

					}

					if (b.containsPoint(position.x, top, position.z)) {
						// move down
						if (vY > 0) {
							position.y = blockBottom - h / 8;
							vY = 0;
							// vY += gravity;
							// System.out.println("Hit: UP line 134");

						}
					} else if (b.containsPoint(position.x, bottom, position.z)) {
						// move up/grounded
						if (vY < 0) {
							position.y = blockTop + h / 2;
							vY = 0;
							// System.out.println("Hit: DOWN line 142");
							grounded = true;

						}
					}

//					if (this.position.y <= -5) {
//						moveTo(2.5, 5, 2.5);
//						vY = 0;
//					}

					if (b.containsPoint(position.x, position.y, front)
							|| b.containsPoint(position.x, bottom + h / 4, front)
							|| b.containsPoint(position.x, top - h / 4, front)) {
						// System.out.println("Hit Front line 155");
						// move back
						position.z = blockFront - d / 2;
					} else if (b.containsPoint(position.x, position.y, back)
							|| b.containsPoint(position.x, bottom + h / 4, back)
							|| b.containsPoint(position.x, top - h / 4, back)) {
						// move forward
						position.z = blockBack + d / 2;
						// System.out.println("Hit back Line 163");
					}

				}

			}
			// double r = Math.sqrt((position.x - (s.x - 8) * (position.x - (s.x - 8))
//					+ (position.z - s.z) * (position.z - (s.z))));
//			position.x += -r * (Math.PI + Math.cos(Math.toRadians(-s.rot)));
//			position.z += -r * (Math.PI + Math.sin(Math.toRadians(-s.rot)));

//			position.z -= -r * (Math.cos(Math.toRadians(s.rot)));
//			position.x += (r-r * (Math.sin(Math.toRadians(s.rot))));
//			position.x = (Math.cos(rad) * (position.x - s.x) - Math.sin(-rad) * (position.z - s.z)) + s.x;//////////////////////////////////
//			position.z = (Math.sin(-rad) * (position.x - s.x) + Math.cos(rad) * (position.z - s.z)) + s.z;///////////////////////////////

//			position.z -= r * (Math.sin(rad));
//			position.x += r * (Math.cos(rad));
			
//			x = position.x;
//			position.x = -position.z;
//			position.z = x;
			
			newx = position.x*Math.cos(rad)-position.z*Math.sin(rad);
			newz = position.z*Math.cos(rad)+position.x*Math.sin(rad);
			position.x = newx;
			position.z = newz;
			
			position.x += s.x;
			position.z += s.z;
			position.z = -position.z;
			
			
			
//			
			this.position = position.copy();
		}

		// if (!grounded)
		vY -= gravity;
		if (vY <= -0.3)
			vY = -0.3;

		if (!gravityOn) {
			if (vY < 0)
				;
			vY = 0;

			grounded = true;
		}
	}

	// vY += gravity;
	// if(vY > .3f) {
	// vY = .3f;
	// }

//	public void jump() {
//		if (grounded && allowJump) {
//			grounded = false;
//			allowJump = false;
//			vY -= 1.0f;
//			velocity.x *= 1.5;
//			vY *= 1.5;
//			velocity.z *= 1.5;
//			// getPosition().y -= 3;
//			// JumpPatch p = new JumpPatch();
//			// p.start();
//		}
//	}

	public double getWidth() {
		return w;
	}

	public double getHeight() {
		return h;
	}

	public double getDepth() {
		return d;
	}

	public void toggleGravity() {
		gravityOn = !gravityOn;
	}

	/**
	 * Sets the position of the player to the given coordinates
	 * 
	 * @param x x-coordinate of where to move the player
	 * @param y y-coordinate of where to move the player
	 * @param z z-coordinate of where to move the player
	 */
	public void moveTo(double x, double y, double z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public double getAngle() {
		return this.getPan();
	}

	public double getAngle2() {
		return this.getTilt();
	}

}
