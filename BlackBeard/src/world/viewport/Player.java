package world.viewport;

import java.util.concurrent.CopyOnWriteArrayList;

import world.constants.declaration.MConstants;
import world.constructs.Ship;
import world.constructs.blocks.Clippable;

/**
 * The player, extension of camera for collisions.
 * 
 * @author Michael Ferolito
 *
 */
public class Player extends Camera {
	private double w, h, d;
	private boolean grounded;
	private double gravity;
	/**
	 * dx
	 */
	public double dx;
	/**
	 * dy
	 */
	public double dy;
	/**
	 * dz
	 */
	public double dz;
	
	@SuppressWarnings("unused")
	private CopyOnWriteArrayList<Clippable> br3 = new CopyOnWriteArrayList<Clippable>();
	/**
	 * DONT USE For internal use only
	 */
	boolean allowJump = true;
	private double VCT = 0.01;

	// private boolean gravityOn = true;

	// public static Chunk selectedChunk;

	/**
	 * Creates a new default player.
	 * @param jp
	 */
	public Player(JoglPane jp) {
		// speed is at .1f max
		this(0.5, 1.5, 0.5, .05f, .5f, .5f, .75f, MConstants.PI / 3f, 1000f, jp);
		this.position.y = 5;
	}

	/**
	 * It would appear that the Player is not covered under the Server license, but
	 * the 3-clause BSD still applies.
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

	/**
	 * Yeets the player into the air.
	 */
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
	 * the specified ArrayList. Also checks ships. This is he thicckest method here.
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

			}
		}
		for (Ship s : Grid.ships) {
			boolean hit = false;
			z0 = 0;
			x0 = 0;
			double rad = Math.toRadians(s.rot);
			MVector position = new MVector();
			position.x = this.position.x;
			position.z = this.position.z;
			position.y = this.position.y;
			//position.z = -position.z;
			position.x -= (s.x);
			position.z -= (s.z);
//			position.x = Math.cos(rad) * (position.x - s.x) - Math.sin(rad) * (position.z - s.z) + s.x;///////////////////////////////////
//			position.z = Math.sin(rad) * (position.x - s.x) + Math.cos(rad) * (position.z - s.z) + s.z;////////////////////////////////

			double newx = position.x * Math.cos(rad) - position.z * Math.sin(-rad);
			double newz = position.z * Math.cos(rad) + position.x * Math.sin(-rad);
			position.x = newx;
			position.z = newz;

			double r = Math
					.sqrt(((position.x - (s.x)) * ((position.x - (s.x))) + (position.z - s.z) * (position.z - (s.z))));
			if (Double.isNaN(r)) {
				r = 0.1;
			}
			// System.out.println(rad);

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
						hit = true;
						position.x = blockRight + w / 2;
						// System.out.println("Hit left line 118");

					} else if (b.containsPoint(right, position.y, position.z)
							|| b.containsPoint(right, bottom + h / 4, position.z)) {
						// move left
						position.x = blockLeft - w / 2;
						hit = true;
						// System.out.println("Hit Right line 124");

					}

					if (b.containsPoint(position.x, top, position.z)) {
						// move down
						if (vY > 0) {
							position.y = blockBottom - h / 8;
							hit = true;
							vY = 0;
							// vY += gravity;
							// System.out.println("Hit: UP line 134");

						}
					} else if (b.containsPoint(position.x, bottom, position.z)) {
						// move up/grounded
						if (vY < 0) {
							position.y = blockTop + h / 2;
							vY = 0;
							hit = true;
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
						hit = true;
						// System.out.println("Hit Front line 155");
						// move back
						position.z = blockFront - d / 2;
					} else if (b.containsPoint(position.x, position.y, back)
							|| b.containsPoint(position.x, bottom + h / 4, back)
							|| b.containsPoint(position.x, top - h / 4, back)) {
						hit = true;
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

			newx = position.x * Math.cos(rad) - position.z * Math.sin(rad);
			newz = position.z * Math.cos(rad) + position.x * Math.sin(rad);
			position.x = newx;
			position.z = newz;

			position.x += s.x;
			position.z += s.z;
//			position.z = -position.z;

			this.position = position.copy();
			if (hit) {
				this.velocity.x += s.velocity * Math.cos(rad) * (1.0 / friction);
				this.velocity.z += s.velocity * Math.sin(rad) * (1.0 / friction);
				if (Math.abs(this.velocity.x - s.velocity * Math.cos(rad)) < VCT ) {
					this.velocity.x = s.velocity * Math.cos(rad) / friction;
				}
				if (Math.abs(this.velocity.z - s.velocity * Math.sin(rad)) < VCT) {
					this.velocity.z = s.velocity * Math.sin(rad) / friction;
				}

//				this.position.x -= s.x;
//				this.position.z -= s.z;
//				newx = this.position.x * Math.cos(s.lastrot) - this.position.z * Math.sin(-s.lastrot);
//				newz = this.position.z * Math.cos(s.lastrot) + this.position.x * Math.sin(-s.lastrot);
//				this.position.x += s.x;
//				this.position.z += s.z;
				
//				

//				// this.setZeroVelocity(s.velocity*Math.cos(rad),s.velocity*Math.sin(rad));
//				this.position.x = (this.position.x-s.x) * Math.cos(Math.toRadians(s.lastrot)) - (this.position.z-s.z) * Math.sin(Math.toRadians(s.lastrot))+s.x;
//				this.position.z = (this.position.x-s.x) * Math.cos(Math.toRadians(s.lastrot)) + (this.position.z-s.z) * Math.sin(Math.toRadians(s.lastrot))+s.z;
				// this.pan += ((s.rot)-s.lastrot)*Math.PI/180;
//				double sx = s.velocity*Math.cos(rad);
//				double sz = s.velocity*Math.cos(rad);

				/**
				* this.velocity.x += (this.position.x-s.x) * Math.cos(Math.toRadians(s.lastrot))*(1.0/friction)/6;
				* this.velocity.z += (this.position.z-s.z) * Math.sin(Math.toRadians(s.lastrot))*(1.0/friction)/6;
				*/
//				r = Math
//						.sqrt(((this.position.x - (s.x)) * ((this.position.x - (s.x))) + (this.position.z - s.z) * (this.position.z - (s.z))));
//				if (Double.isNaN(r)) {
//					r = 0.1;
//				}
//				this.velocity.x += r*Math.cos(Math.toRadians(s.lastrot));
//				this.velocity.z += r*Math.sin(Math.toRadians(s.lastrot));
//				System.out.println(velocity);

//				this.velocity.x += sx * Math.cos(rad) - sz * Math.sin(-rad)*(1.0/friction);
//				this.velocity.z += sz * Math.cos(rad) + sx * Math.sin(-rad)*(1.0/friction);

			}
			
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

	/**
	 * Oh no you don't. Leave this alone.
	 * @param e
	 * @param f
	 */
	void setZeroVelocity(double e, double f) {
		x0 = e;
		z0 = f;

	}

	/**
	 * Returns the thicckness of the player
	 * @return
	 */
	public double getWidth() {
		return w;
	}

	/**
	 * returns the height of the player
	 * @return
	 */
	public double getHeight() {
		return h;
	}

	/**
	 * returns the obesity rating of the Player.
	 * @return
	 */
	public double getDepth() {
		return d;
	}

	/**
	 * switches the Earth on and off.
	 */
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

	/**
	 * Fancy getPan();
	 * @return
	 */
	public double getAngle() {
		return this.getPan();
	}

	/**
	 * Fancy getTilt();
	 * <head>
<button onmousedown="accelerate(-0.2)" onmouseup="accelerate(0.05)">ACCELERATE</button>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<style>
canvas {
    border:1px solid #d3d3d3;
    background-color: #f1f1f1;
}
</style>
</head>
<body onload="startGame()">
<script>
var myGamePiece;
var myObstacles = [];
var myScore;
window.addEventListener('keydown',this.check,false);
window.addEventListener('keyup',this.check2,false);
function check(e) {
    accelerate(-0.2);
}
function check2(e){
	accelerate(0.2)
}
function startGame() {
    myGamePiece = new component(30, 30, "red", 10, 120);
    myGamePiece.gravity = 0.05;
    myScore = new component("30px", "Consolas", "black", 280, 40, "text");
    myGameArea.start();
}
var myGameArea = {
    canvas : document.createElement("canvas"),
    start : function() {
        this.canvas.width = 480;
        this.canvas.height = 270;
        this.context = this.canvas.getContext("2d");
        document.body.insertBefore(this.canvas, document.body.childNodes[0]);
        this.frameNo = 0;
        this.interval = setInterval(updateGameArea, 20);
        },
    clear : function() {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }
}
function component(width, height, color, x, y, type) {
    this.type = type;
    this.score = 0;
    this.width = width;
    this.height = height;
    this.speedX = 00;
    this.speedY = 0;    
    this.x = x;
    this.y = y;
    this.gravity = 0;
    this.gravitySpeed = 0;
    this.update = function() {
        ctx = myGameArea.context;
        if (this.type == "text") {
            ctx.font = this.width + " " + this.height;
            ctx.fillStyle = color;
            ctx.fillText(this.text, this.x, this.y);
        } else {
            ctx.fillStyle = color;
            ctx.fillRect(this.x, this.y, this.width, this.height);
        }
    }
    this.newPos = function() {
        this.gravitySpeed += this.gravity;
        this.x += this.speedX;
        this.y += this.speedY + this.gravitySpeed;
        this.hitBottom();
    }
    this.hitBottom = function() {
        var rockbottom = myGameArea.canvas.height - this.height;
        if (this.y > rockbottom) {
            this.y = rockbottom;
            this.gravitySpeed = 0;
        }
    }
    this.crashWith = function(otherobj) {
        var myleft = this.x;
        var myright = this.x + (this.width);
        var mytop = this.y;
        var mybottom = this.y + (this.height);
        var otherleft = otherobj.x;
        var otherright = otherobj.x + (otherobj.width);
        var othertop = otherobj.y;
        var otherbottom = otherobj.y + (otherobj.height);
        var crash = true;
        if ((mybottom < othertop) || (mytop > otherbottom) || (myright < otherleft) || (myleft > otherright)) {
            crash = false;
        }
        return crash;
    }
}
function updateGameArea() {
    var x, height, gap, minHeight, maxHeight, minGap, maxGap;
    for (i = 0; i < myObstacles.length; i += 1) {
        if (myGamePiece.crashWith(myObstacles[i])) {
            return;
        } 
    }
    myGameArea.clear();
    myGameArea.frameNo += 1;
    if (myGameArea.frameNo == 1 || everyinterval(150)) {
        x = myGameArea.canvas.width;
        minHeight = 20;
        maxHeight = 200;
        height = Math.floor(Math.random()*(maxHeight-minHeight+1)+minHeight);
        minGap = 50;
        maxGap = 200;
        gap = Math.floor(Math.random()*(maxGap-minGap+1)+minGap);
        myObstacles.push(new component(10, height, "green", x, 0));
        myObstacles.push(new component(10, x - height - gap, "green", x, height + gap));
    }
    for (i = 0; i < myObstacles.length; i += 1) {
        myObstacles[i].x += -1;
        myObstacles[i].update();
    }
    myScore.text="SCORE: " + myGameArea.frameNo;
    myScore.update();
    myGamePiece.newPos();
    myGamePiece.update();
}
function everyinterval(n) {
    if ((myGameArea.frameNo / n) % 1 == 0) {return true;}
    return false;
}
function accelerate(n) {
    myGamePiece.gravity = n;
}
</script>
<br>
<p>Use the ACCELERATE button to stay in the air</p>
<p>How long can you stay alive?</p>
</body>
	 */
	public double getAngle2() {
		return this.getTilt();
	}

}