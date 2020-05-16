package world.viewport;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import world.constants.declaration.MConstants;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A Camera object encapsulates the information needed to define a viewing
 * transform and a projection for an OpenGL context. The apply method can be
 * called to applied this information to a context. The default view is from the
 * point (0,0,30), looking at (0,0,0), with (0,1,0) pointing upwards on the
 * screen. The default projection is a perspective projection. The x and y
 * limits on the screen include at least -5 to 5. Limits in either the x or y
 * direction will be expanded if necessary to match the aspect ratio of the
 * screen. And the view volume extends from -10 to 10 along the z-axis. Only the
 * default constructor exists. Non-default properties must be set by calling
 * methods.
 */
public class Camera {
	/**
	 * Represents whether the Camera should experience a downward force.
	 */
	protected boolean gravityOn = true;
	/**
	 * The x component for zero friction vector.
	 */
	double x0 = 0;
	/**
	 * The z component for zero friction vector
	 */
	double z0 = 0;
	/**
	 * Viewport statistics representing the position the center of the viewport.
	 */
	protected double eyex, eyey, eyez = 30;
	/**
	 * Viewport statistics representing the viewport directional components.
	 */
	protected double refx, refy, refz;
	/**
	 * Viewport statistics representing the upward reference vector components.
	 */
	protected double upx, upy = 1, upz;

	/**
	 * Viewport statistics for the requested minimun and maximum of the viewport
	 * range.
	 */
	protected double xminRequested = -5, xmaxRequested = 5;
	/**
	 * Viewport statistics for the requested minimun and maximum of the viewport
	 * range.
	 */
	protected double yminRequested = -5, ymaxRequested = 5;
	/**
	 * Minimum and maximum BASE render rances
	 */
	protected double zmin = -10, zmax = 10;
	/**
	 * Suggests that the Camera preserve the size of objects without frustums.
	 */
	protected boolean orthographic;
	/**
	 * Suggests that the Camera avoid distortions at the edge of the viewport.
	 */
	protected boolean preserveAspect = true;

	/**
	 * In contrast to the requested values, which may not be valid, these values
	 * represent the real viewport parameters.
	 */
	protected double xminActual, xmaxActual, yminActual, ymaxActual;
	/**
	 * The GLU object used to transforms.
	 */
	protected GLU glu = new GLU();

	/**
	 * Set to true if the Camera has experienced a change in position within the
	 * last cycle.
	 */
	public volatile static boolean hasMoved = false;
	/**
	 * The Robot used to lock the mouse within the confines of the viewport.
	 */
	protected Robot robot;
	/**
	 * The center vector is representative of the viewport center. The right vector
	 * is the right velocity vector. The forward vector is the forward velocity
	 * vector.
	 */
	protected volatile MVector center, right, forward;
	/**
	 * The position vector holds the Camera's position from the origin vector.
	 */
	protected volatile MVector position;
	/**
	 * The main velocity vector, which applies to all planes except y.
	 */
	protected volatile MVector velocity;
	/**
	 * Trackball components, used for moving the camera.
	 */
	protected double speed, xSensitivity, ySensitivity, pan, friction, fov, viewDistance;
	double tilt;
	protected Point mouse, pMouse;
	protected boolean sprinting;
	protected JoglPane parent;
	protected int speedCap;
	protected double vY = -0.0000;
	protected double RENDER = 25;

	/**
	 * No args constructor calls this(3, 1, 1, .75f, MConstants.PI / 3f, 1000f);
	 */
	public Camera(JoglPane jp) {
		this(.1, 0.5, 0.5, .999, MConstants.PI / 3, 1000, jp);
	}

	/**
	 * Sets up a camera with the given patameters.
	 * 
	 * @param speed
	 * @param xSensitivity
	 * @param ySensitivity
	 * @param friction
	 * @param d
	 * @param viewDistance
	 * @param jp
	 */
	public Camera(double speed, double xSensitivity, double ySensitivity, double friction, double d,
			double viewDistance, JoglPane jp) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
		}
		this.parent = jp;
		this.speed = speed;
		this.xSensitivity = xSensitivity;
		this.ySensitivity = ySensitivity;
		this.friction = friction;
		this.fov = d;
		this.viewDistance = viewDistance;

		position = new MVector(0f, 0f, 0f);
		right = new MVector(1f, 0f, 0f);
		forward = new MVector(0f, 0f, 1f);
		velocity = new MVector(0f, 0f, 0f);

		pan = 0;
		tilt = 0;
	}

	/**
	 * Returns true if orthographic mode is enabled.
	 * 
	 * @return
	 */
	public boolean getOrthographic() {
		return orthographic;
	}

	/**
	 * Determine whether the projection is orthographic or perspective. The default
	 * is perspective.
	 * 
	 * @param orthographic set to true for orthographic projection and to false for
	 *                     perspective projection.
	 */
	public void setOrthographic(boolean orthographic) {
		this.orthographic = orthographic;
	}

	/**
	 * Returns the current state of the aspect preservation.
	 * 
	 * @return
	 */
	public boolean getPreserveAspect() {
		return preserveAspect;
	}

	/**
	 * Determine whether the xy-limits should be adjusted to match the aspect ratio
	 * of the display area. The default is true.
	 * 
	 * @param preserveAspect set to true for preservation and false for not.
	 */
	public void setPreserveAspect(boolean preserveAspect) {
		this.preserveAspect = preserveAspect;
	}

	/**
	 * Set the limits of the view volume. The limits are set with respect to the
	 * viewing coordinates. That is, the view center is assumed to be at the point
	 * (0,0) in the plane of the screen. The view up vector (more precisely, its
	 * projection onto the screen) points upwards on the screen. The z-axis is
	 * perpendicular to the screen, with the positive direction of the z-axis
	 * pointing out of the screen. In this coordinate system, xmin and xmax give the
	 * horizontal limits on the screen, ymin and ymax give the vertical limits on
	 * the screen, and zmin and zmax give the limits of the view volume along the
	 * z-axis. (Note that this is NOT exactly the same as the parameters in either
	 * glOrtho or glFrustum! Most important to note is that zmin and zmax are given
	 * with reference to the view center, not the eye.) Note that xmin/xmax or
	 * ymin/ymax might be adjusted to match the aspect ratio of the display area.
	 */
	public void setLimits(double xmin, double xmax, double ymin, double ymax, double zmin, double zmax) {
		xminRequested = xminActual = xmin;
		xmaxRequested = xmaxActual = xmax;
		yminRequested = yminActual = ymin;
		ymaxRequested = ymaxActual = ymax;
		this.zmin = zmin;
		this.zmax = zmax;
	}

	/**
	 * Returns the robot
	 * 
	 * @return robot
	 */
	public Robot getRobot() {
		return this.robot;
	}

	/**
	 * A convenience method for calling
	 * setLimits(-limit,limit,-limit,limit,-2*limit,2*limit)
	 */
	public void setScale(double limit) {
		setLimits(-limit, limit, -limit, limit, -2 * limit, 2 * limit);
	}

	/**
	 * Returns the view limits. The return value is an array that contains the same
	 * data as the parameters to setLimits(). Note that the returned values included
	 * the originally requested xmin/xmax and ymin/ymax, and NOT values that have
	 * been adjusted to reflect the aspect ratio of the display area.
	 */
	public double[] getLimits() {
		return new double[] { xminRequested, xmaxRequested, yminRequested, ymaxRequested, zmin, zmax };
	}

	/**
	 * Returns the actual xmin, xmax, ymin, ymax limits that were used when the
	 * apply method was most recently called. These are the limits after they were,
	 * possibly, adjusted to match the aspect ratio of the display. If apply has not
	 * been called since the limits were set, then the return value contains the
	 * unadjusted, requested limits.
	 */
	public double[] getActualXYLimits() {
		return new double[] { xminActual, xmaxActual, yminActual, ymaxActual };
	}

	/**
	 * Set the information for the viewing transformation. The view will be set in
	 * the apply method with a call to
	 * gluLookAt(eyeX,eyeY,eyeZ,viewCenterX,viewCenterY
	 * ,viewCenterZ,viewUpX,viewUpY,viewUpZ)
	 */
	public void lookAt(double eyeX, double eyeY, double eyeZ, double viewCenterX, double viewCenterY,
			double viewCenterZ, double viewUpX, double viewUpY, double viewUpZ) {
		eyex = eyeX;
		eyey = eyeY;
		eyez = eyeZ;
		refx = viewCenterX;
		refy = viewCenterY;
		refz = viewCenterZ;
		upx = viewUpX;
		upy = viewUpY;
		upz = viewUpZ;
	}

	/**
	 * Moves only the eye position and not any of the other vectors. Used for hard
	 * resets.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(double x, double y, double z) {
		eyex += x;
		eyey += y;
		eyex += z;
		refx += x;
		refy += y;
		refz += z;
	}

	/**
	 * Returns the view information -- the 9 parameters of lookAt(), in an array.
	 * The array can be used as a matrix for spacial transformations.
	 */
	public double[] getViewParameters() {
		return new double[] { eyex, eyey, eyez, refx, refy, refz, upx, upy, upz };
	}

	/**
	 * Apply the camera to an OpenGL context. This method completely replaces the
	 * projection and the modelview transformation in the context. It sets these
	 * transformations to the identity and then applies the view and projection
	 * represented by the camera. This method is meant to be called at the begining
	 * of the display method and should replace any other means of setting the
	 * projection and view.
	 * 
	 * This method relies on gluLookAt.
	 */
	public void apply(GL2 gl) {
		eyex = position.x;
		eyey = position.y;
		eyez = position.z;
		int[] viewport = new int[4];
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
		xminActual = xminRequested;
		xmaxActual = xmaxRequested;
		yminActual = yminRequested;
		ymaxActual = ymaxRequested;
		if (preserveAspect) {
			double viewWidth = viewport[2];
			double viewHeight = viewport[3];
			double windowWidth = xmaxActual - xminActual;
			double windowHeight = ymaxActual - yminActual;
			double aspect = viewHeight / viewWidth;
			double desired = windowHeight / windowWidth;
			if (desired > aspect) { // expand width
				double extra = (desired / aspect - 1.0) * (xmaxActual - xminActual) / 2.0;
				xminActual -= extra;
				xmaxActual += extra;
			} else if (aspect > desired) {
				double extra = (aspect / desired - 1.0) * (ymaxActual - yminActual) / 2.0;
				yminActual -= extra;
				ymaxActual += extra;
			}
		}
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		double viewDistance = norm(new double[] { refx - eyex, refy - eyey, refz - eyez });
		// viewDistance=this.viewDistance;
		if (orthographic) {
			gl.glOrtho(xminActual, xmaxActual, yminActual, ymaxActual, viewDistance - zmax, viewDistance - zmin);
		} else {
			// double near = viewDistance - zmax;
			double near = 0.01;
			if (near < 0.01)
				near = 0.01;
			double centerx = (xminActual + xmaxActual) / 2;
			double centery = (yminActual + ymaxActual) / 2;
			double newwidth = (near / viewDistance) * (xmaxActual - xminActual);
			double newheight = (near / viewDistance) * (ymaxActual - yminActual);
			double x1 = centerx - newwidth / 2;
			double x2 = centerx + newwidth / 2;
			double y1 = centery - newheight / 2;
			double y2 = centery + newheight / 2;
			gl.glFrustum(x1, x2, y1, y2, near, viewDistance * RENDER - zmin);
		}
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(eyex, eyey, eyez, refx, refy, refz, upx, upy, upz);
	}

	/**
	 * Returns the position vector.
	 * 
	 * @return
	 */
	public MVector getPosition() {
		return position;
	}

	/**
	 * Returns the normalised form of a vector held in a double matrix.
	 * 
	 * @param v
	 * @return
	 */
	protected double norm(double[] v) {
		double norm2 = v[0] * v[0] + v[1] * v[1] + v[2] * v[2];
		if (Double.isNaN(norm2) || Double.isInfinite(norm2) || norm2 == 0)
			throw new NumberFormatException("Vector length zero, undefined, or infinite.");
		return Math.sqrt(norm2);
	}

	/**
	 * Normalises a vector held in a double matrix.
	 * 
	 * @param v
	 */
	protected void normalize(double[] v) {
		double norm = norm(v);
		v[0] /= norm;
		v[1] /= norm;
		v[2] /= norm;
	}

	/**
	 * applies a transvection from e1 to e2. Processing originally did this behind
	 * the scenes, but since we're not using processing at all, this is necessary.
	 * 
	 * @param e1
	 * @param e2
	 */
	protected void applyTransvection(double[] e1, double[] e2) {
		// rotate vector e1 onto e2; must be 3D *UNIT* vectors.
		double[] zDirection = new double[] { eyex - refx, eyey - refy, eyez - refz };
		double viewDistance = norm(zDirection);
		normalize(zDirection);
		double[] yDirection = new double[] { upx, upy, upz };
		double upLength = norm(yDirection);
		double proj = yDirection[0] * zDirection[0] + yDirection[1] * zDirection[1] + yDirection[2] * zDirection[2];
		yDirection[0] = yDirection[0] - proj * zDirection[0];
		yDirection[1] = yDirection[1] - proj * zDirection[1];
		yDirection[2] = yDirection[2] - proj * zDirection[2];
		normalize(yDirection);
		double[] xDirection = new double[] { yDirection[1] * zDirection[2] - yDirection[2] * zDirection[1],
				yDirection[2] * zDirection[0] - yDirection[0] * zDirection[2],
				yDirection[0] * zDirection[1] - yDirection[1] * zDirection[0] };
		e1 = transformToViewCoords(e1, xDirection, yDirection, zDirection);
		e2 = transformToViewCoords(e2, xDirection, yDirection, zDirection);
		double[] e = new double[] { e1[0] + e2[0], e1[1] + e2[1], e1[2] + e2[2] };
		normalize(e);
		double[] temp = new double[3];
		reflectInAxis(e, zDirection, temp);
		reflectInAxis(e1, temp, zDirection);
		reflectInAxis(e, xDirection, temp);
		reflectInAxis(e1, temp, xDirection);
		reflectInAxis(e, yDirection, temp);
		reflectInAxis(e1, temp, yDirection);
		eyex = refx + viewDistance * zDirection[0];
		eyey = refy + viewDistance * zDirection[1];
		eyez = refz + viewDistance * zDirection[2];
		upx = upLength * yDirection[0];
		upy = upLength * yDirection[1];
		upz = upLength * yDirection[2];
	}

	/**
	 * Reflects the given vector around the given axis vector. the source vector is
	 * left unchanged, and the new values are stored in destination.
	 * 
	 * @param axis
	 * @param source
	 * @param destination
	 */
	protected void reflectInAxis(double[] axis, double[] source, double[] destination) {
		double s = 2 * (axis[0] * source[0] + axis[1] * source[1] + axis[2] * source[2]);
		destination[0] = s * axis[0] - source[0];
		destination[1] = s * axis[1] - source[1];
		destination[2] = s * axis[2] - source[2];
	}

	protected double[] transformToViewCoords(double[] v, double[] x, double[] y, double[] z) {
		double[] w = new double[3];
		w[0] = v[0] * x[0] + v[1] * y[0] + v[2] * z[0];
		w[1] = v[0] * x[1] + v[1] * y[1] + v[2] * z[1];
		w[2] = v[0] * x[2] + v[1] * y[2] + v[2] * z[2];
		return w;
	}

	/**
	 * "Clamp" the x value to within the range of min-max
	 * 
	 * @param tilt2
	 * @param d
	 * @param e
	 * @return new value
	 */
	protected double clamp(double tilt2, double d, double e) {
		if (tilt2 > e)
			return e;
		if (tilt2 < d)
			return d;
		return tilt2;
	}

	/**
	 * Draws the Camera in perspective view on the Gl2 given as a parameter
	 * 
	 * @param g - what to draw on.
	 * @param w
	 */
	public void draw(GL2 g, JoglPane joglPane, JFrame w) {
		hasMoved = false;
		// Get the coordinates of the borders of the window
		if (true) {
			int top = w.getBounds().y + 10;
			int windowRight = w.getBounds().x + joglPane.getBounds().width - 10;
			int left = w.getBounds().x + 10;
			int bottom = w.getBounds().y + joglPane.getBounds().height - 10;

			if (!parent.disabled) {
				mouse = MouseInfo.getPointerInfo().getLocation();

				if (pMouse == null)
					pMouse = new Point(mouse.x, mouse.y);

				// means that the mouse went off the screen to the left so move
				// it
				// to the right
				if (mouse.x < left + 2 && (mouse.x - pMouse.x) < 0) {
					robot.mouseMove(windowRight - 2, mouse.y);
					mouse.x = windowRight - 2;
					pMouse.x = windowRight - 2;
				}

				// means that the mouse went off the screen to the right so move
				// it
				// to the left
				if (mouse.x > windowRight - 2 && (mouse.x - pMouse.x) > 0) {
					robot.mouseMove(left + 2, mouse.y);
					mouse.x = left + 2;
					pMouse.x = left + 2;
				}

				// means that the mouse went up off the screen so move it to the
				// bottom
				if (mouse.y < top + 40 && (mouse.y - pMouse.y) < 0) {
					robot.mouseMove(mouse.x, bottom - 5);
					mouse.y = bottom - 5;
					pMouse.y = bottom - 5;
				}

				// means that the mouse went down off the screen so move it to
				// the
				// top
				if (mouse.y > bottom - 5 && (mouse.y - pMouse.y) > 0) {
					robot.mouseMove(mouse.x, top + 10);
					mouse.y = top + 10;
					pMouse.y = top + 10;
				}

				// map the mouse value to the corresponding angle between 0 and
				// 2PI
				// for x
				// rotation(pan) because you have 360º rotation
				pan += MVector.map(mouse.x - pMouse.x, 0, joglPane.getBounds().width, 0, MConstants.TWO_PI) * xSensitivity;
				tilt += -MVector.map(mouse.y - pMouse.y, 0, joglPane.getBounds().height, 0, MConstants.PI) * ySensitivity;
				tilt = clamp(tilt, -MConstants.PI / 2.01f, MConstants.PI / 2.01f);

				// tan of pi/2 or -pi/2 is undefined so if it happens to be
				// exactly
				// that
				// increase it so the code works
				if (tilt == MConstants.PI / 2)
					tilt += 0.001f;
				if (tilt == -MConstants.PI / 2)
					tilt -= 0.001f;
			}

			// tilt*=-1;
			// pan*=-1;
			// Vector representing what forward is relative to the camera right
			// now
			forward = new MVector(Math.cos(pan), Math.tan(tilt), Math.sin(pan));

			// make it a unit vector because the direction is all that matters
			forward.normalize();

			// subtract pi/2 from pan to get the vector perpendicular to forward
			// to show
			// which way is right
			right = new MVector(Math.cos(pan - MConstants.PI / 2), 0, Math.sin(pan - MConstants.PI / 2));

			// have the previous mouse set to the current mouse to use it for
			// the next call
			// to draw()
			pMouse = new Point(mouse.x, mouse.y);

			// account for friction
			velocity.x -= x0;
			velocity.z -= z0;
			velocity.mult(friction);
			velocity.x += x0;
			velocity.z += z0;
			// use velocity to find out location of new position
			if (sprinting) {
				MVector v2 = velocity.copy();
				v2.y = 0;
				v2.x *= .8f;
				v2.z *= .8f;
				position.add(v2);
			}
			MVector normV = velocity.copy();
			// normV.normalize();
			normV.mult(friction);

			if (normV.getMagnitude() <= 0.001) {
				normV = new MVector(0, 0, 0);
				velocity = new MVector(0, 0, 0);
			}
			// System.out.println(position.y);
			position.add(normV);
			if (gravityOn) {
				position.y += vY;
			}
			if (velocity.x != 0 || velocity.y != 0 || velocity.x != 0) {
				Camera.hasMoved = true;
			}
			// center of the sketch is in the direction of forward but
			// translated based on
			// how you moved so you need to take into account position
			center = MVector.add(position, forward);
			// float bob = (float)
			// Math.sin(velocity.z+velocity.x+System.currentTimeMillis());
			// float bob = (float) Math.sin(System.currentTimeMillis()/1000);
			// System.out.println(bob);
		}
		lookAt(position.x, position.y, position.z, center.x, center.y, center.z, 0, 1, 0);
		apply(g);
	}

	/**
	 * move the x vector by dir
	 * 
	 * @param dir
	 */
	public void moveX(int dir) {
		Double f = velocity.y;
		velocity.add(MVector.mult(right, speed * dir));
		if (velocity.getMagnitude() > 3) {
			velocity.y = 0;
			velocity.normalize();
			velocity.mult(3);
		}
		velocity.y = f;
		// velocity.x+=speed*dir*-1;
	}

	/**
	 * Returns the major plane of the velocity vector.
	 * @return
	 */
	public MVector getVelocity() {
		return this.velocity;
	}

	/**
	 * move the z vector by dir
	 * 
	 * @param dir
	 */
	public void moveZ(int dir) {
		Double f = velocity.y;
		velocity.add(MVector.mult(forward, speed * dir));
		if (velocity.getMagnitude() > 3) {
			velocity.y = 0;
			velocity.normalize();
			velocity.mult(3);
		}
		// velocity.z+=speed*dir*-1;
		velocity.y = f;
	}

	/**
	 * Resets the captured mouse statistics.
	 */
	public void clearMouseStats() {
		mouse = MouseInfo.getPointerInfo().getLocation();
		pMouse = mouse;

	}

	/**
	 * returns the raw tilt value.
	 * @return
	 */
	public double getTilt() {
		return this.tilt;
	}

	/**
	 * returns the raw pan value.
	 * @return
	 */
	public double getPan() {
		return this.pan;
	}

}
