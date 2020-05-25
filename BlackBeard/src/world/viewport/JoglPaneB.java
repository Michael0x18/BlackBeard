package world.viewport;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; // for drawing the sample teapot

import client.net.Client;
import world.constructs.blocks.Block;
import world.constructs.blocks.Clippable;
import world.constructs.blocks.TexLoader;
import world.constructs.projectiles.Projectile;
import world.io.Printer;
import world.constructs.OtherPlayer;
import world.constructs.Ship;
import world.constructs.StructPlayer;
import world.ui.JoglMenu;
import world.viewport.Builder;
import javafx.scene.paint.Color;

/*glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	
glMatrixMode(GL_PROJECTION );
glLoadIdentity();
glOrtho(0,1,0,1,-1,1);
glDisable(GL_DEPTH_TEST);
glDisable(GL_LIGHTING);
glDepthMask(GL_FALSE);
glMatrixMode(GL_MODELVIEW);
glLoadIdentity();
//draw 2D image
glDepthMask(GL_TRUE);
glEnable(GL_DEPTH_TEST);
glEnable(GL_LIGHTING);
glMatrixMode(GL_PROJECTION);
glLoadIdentity();
gluPerspective(45.0, (GLfloat) W_WIDTH / (GLfloat) W_HEIGHT, 0.1, 1000.0);
glMatrixMode(GL_MODELVIEW);
glLoadIdentity();




/**
 * Represents a panel for drawing. The bulk of the graphics is done here.
 */
public class JoglPaneB extends JoglPane
		implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {

	/**
	 * Called as if a main method.
	 * 
	 * @param flags
	 */
	public static void _driver_start(String[] flags) {
		try {
			ClassLoader.getSystemClassLoader().loadClass("world.constructs.blocks.TexLoader");
			TexLoader t = new TexLoader();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Grid.loadTowerOfBabel();
		Grid.load("TBD");
//		Ship sh = new Ship();

		window = new JFrame("JOGL");
		JoglPaneB j = new JoglPaneB();
		pl = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				this.setBackground(new java.awt.Color(0, 0, 0, 0));
				this.setOpaque(false);
				g.fillRect(0, 0, 100, 100);
			}
		};
		window.add(pl);

		window.setContentPane(j);
		window.pack();
		window.setLocation(50, 50);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new java.awt.Point(0, 0),
				"blank cursor");

		// Set the blank cursor to the JFrame.
		j.setCursor(blankCursor);
		// window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// window.setCursor(new Cursor(0));

	}

	/**
	 * yeets the cursor into oblivion
	 */
	public void cursorOff() {
		this.setCursor(blankCursor);
	}

	/**
	 * gets back your mouse
	 */
	public void cursorOn() {
		this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
	}

	private Builder bob = new Builder("TBD");

	/**
	 * returns the player associated with this object. Weird dependencies are ok, as
	 * are multiple JVM's.
	 * 
	 * @return
	 */
	public Player getC() {
		return c;
	}

//	 public void paint(Graphics g){
//	this.setBackground(new Color(0,0,0,0));
//	this.setOpaque(false);
//	g.fillRect(0, 0, 100, 100);
//	 }

	/**
	 * new JoglPane. Really only should be used from within this class.
	 */
	JoglPaneB() {
		super();
//		currentLoader = this;
//		GLCapabilities caps = new GLCapabilities(null);
//		display = new GLJPanel(caps);
//		display.setPreferredSize(new Dimension(600, 600));
//		// size here
//		display.addGLEventListener(this);
//		setLayout(new BorderLayout());
//		add(display, BorderLayout.CENTER);
//
//		rotateX = 0; // initialize some variables used in the drawing.
//		rotateY = 0;
//
//		requestFocusInWindow();
//		display.addKeyListener(this);
//
//		// handling
//		display.addMouseListener(this);
//		display.addMouseMotionListener(this);
//
//		// c = new Camera();
//		// c.setScale(3);
//		// c.installTrackball(this);
//		// c.setScale(3);
//		// Ship sh = new Ship();
//		// c = new Player(this,sh);
//		c = new Player(this);
//		c.setScale(0.6);
//		c.moveTo(0, 5, 0);
////		sb = new SensorBlock(c, 1, 0, 0);
//		try {
//		jmb = JoglMenu.applyMenu(window);
//		file = new JMenu("File");
//		jmb.add(file);
//		jmb.add(pointcounter);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		startAnimation();

	}

	// --------------- Methods of the GLEventListener interface -----------

	/**
	 * This method is called when the OpenGL display needs to be redrawn.
	 */
	public void display(GLAutoDrawable drawable) {
		if (!ObjectLoaderV_C.loaded) {
			return;
		}

		GL2 gl = drawable.getGL().getGL2();
		if (this.c.getPosition().y > 0)
			gl.glClearColor((float) Color.LIGHTSKYBLUE.getRed(), (float) Color.LIGHTSKYBLUE.getGreen(),
					(float) Color.LIGHTSKYBLUE.getBlue(), 0);
		else
			gl.glClearColor((float) Color.BLACK.getRed(), (float) Color.BLACK.getGreen(),
					(float) Color.BLACK.getBlue(), 0);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Set up modelview transform.
		if (smoothShading) {
			gl.glEnable(GL2.GL_SMOOTH);
		} else {
			gl.glEnable(GL2.GL_FLAT);
		}
		c.act(Grid.world);
		c.draw(gl, this, window);
		gl.glTranslated(xt, 0, 0);
		gl.glRotatef(rotateY, 0, 1, 0);
		gl.glRotatef(rotateX, 1, 0, 0);

		GL2 gl2 = gl.getGL2();
		// gl.glLoadIdentity();
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_NORMALIZE);
		float[] position = { 0, 20, 0, 1 };
		float[] positions = { (float) c.getPosition().x - 12, (float) c.getPosition().y, (float) c.getPosition().z, 1 };
		float[] positiona = { (float) c.getPosition().x, (float) c.getPosition().y, (float) c.getPosition().z, 1 };
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, position, 0);
		gl2.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, positiona, 0);
		gl2.glLightfv(GL2.GL_LIGHT3, GL2.GL_POSITION, positions, 0);

		gl2.glEnable(GL2.GL_LIGHT1);
		gl2.glEnable(GL2.GL_LIGHT2);
		gl2.glEnable(GL2.GL_LIGHT3);

		// gl.glLoadIdentity();
		for (Clippable c : Grid.world) {
			c.draw(glut, glu, gl, gl.getGL2());
		}
		gl.glColor4d(Color.ROYALBLUE.getRed(), Color.ROYALBLUE.getGreen(), Color.ROYALBLUE.getBlue(), 0.5);
		// gl.glLoadIdentity();
		gl.glTranslated(0, -50.5, 0);
		glut.glutSolidCube(100);
		gl.glTranslated(0, 50.5, 0);
		for (Ship s : Grid.ships) {
			s.draw(glut, glu, gl, gl2);
		}
		// gl.glLoadIdentity();
		for (StructPlayer p : Grid.players) {
			OtherPlayer.draw(p, glut, glu, gl, gl2);
		}
		// gl.glLoadIdentity();
		for (Projectile p : Grid.shots) {
			p.draw(glut, glu, gl, gl2);
		}

		if (Client.listener == null) {
			PlayerTemplate.draw(gl, gl2, glu, glut);
		}

		// pl.repaint();
	}

	/**
	 * This is called when the GLJPanel is first created. It can be used to
	 * initialize the OpenGL drawing context.
	 */
	public void init(GLAutoDrawable drawable) {
		// called when the panel is created
		GL2 gl = drawable.getGL().getGL2();
		ObjectLoaderV_C.load(gl);
		gl.glClearColor(0.8F, 0.8F, 0.8F, 1.0F);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);

	}

	/**
	 * Called when the size of the GLJPanel changes. Note:
	 * glViewport(x,y,width,height) has already been called before this method is
	 * called!
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

	/**
	 * This is called before the GLJPanel is destroyed. It can be used to release
	 * OpenGL resources.
	 */
	public void dispose(GLAutoDrawable drawable) {
	}

	// ------------ Support for keyboard handling ------------

	/**
	 * Called when the user presses any key.
	 */
	public void keyPressed(KeyEvent e) {

//		if (e.getModifiersEx() !=0)
//			return;
		// Keys.add(Integer.valueOf(e.getKeyCode()));
		// if (!Keys.contains(e.getKeyCode())) {
		Keys.add(Integer.valueOf(e.getKeyCode()));
		// }
		repaint();

	}

	/**
	 * Called when the user types a character.
	 */
	public void keyTyped(KeyEvent e) {
		// char ch = e.getKeyChar(); // Which character was typed.
	}

	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_P) {
			int xd = (int) (0.5 + this.c.getPosition().x * 2);
			int yd = (int) (0.5 + this.c.getPosition().y * 2);
			int zd = (int) (0.5 + this.c.getPosition().z * 2);
			System.out.println(xd + " " + yd + " " + zd);
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			c.getRobot().mouseMove(window.getX() + this.getBounds().width / 2,
					window.getY() + this.getBounds().height / 2);
			c.clearMouseStats();
			disabled = !disabled;
			if (this.getCursor().equals(blankCursor))
				this.cursorOn();
			else
				this.cursorOff();

		} else if (e.getKeyCode() == KeyEvent.VK_F) {
			if (fullscreen) {
				fullscreen = false;
				window.dispose();
				window.setUndecorated(false);
				window.pack();
				window.setBounds(0, 0, 800, 600);
				window.setJMenuBar(jmb);
				window.setVisible(true);
			} else {
				fullscreen = true;
				window.dispose();
				window.setUndecorated(true);
				window.pack();
				window.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
						Toolkit.getDefaultToolkit().getScreenSize().height);
				window.setJMenuBar(jmb);
				window.setVisible(true);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_G)
			c.toggleGravity();
		while (Keys.contains(Integer.valueOf(e.getKeyCode())))
			Keys.remove(Integer.valueOf(e.getKeyCode()));
		repaint();
	}

	// --------------------------- animation support ---------------------------

	/**
	 * called each frame.
	 */
	protected void updateFrame() {
		// window.setLayout(null);
		frameNumber++;
		// this.setBounds(-300,-300,window.getWidth()+600,window.getHeight()+600);

	}

	/**
	 * starts the animation timer
	 */
	public void startAnimation() {
		if (!animating) {
			if (animationTimer == null) {
				animationTimer = new Timer(40, this);
			}
			animationTimer.start();
			animating = true;
		}
	}

	/**
	 * stops the animation timer
	 */
	public void pauseAnimation() {
		if (animating) {
			animationTimer.stop();
			animating = false;
		}
	}

	/**
	 * NO CALL!
	 */
	public void actionPerformed(ActionEvent evt) {
		updateFrame();
		pl.repaint();
//		if(frameNumber % 100 == 0) {
//			System.out.println(c.lastShip);
//		}
		int i = 0;
		for (Integer key : Keys) {
			// System.out.println(e);
			if (key == KeyEvent.VK_ESCAPE) {
				break;
			}

			if (key == KeyEvent.VK_W)
				c.moveZ(1);
			else if (key == KeyEvent.VK_S)
				c.moveZ(-1);
			else if (key == KeyEvent.VK_A)
				c.moveX(1);
			else if (key == KeyEvent.VK_D)
				c.moveX(-1);
			else if (key == KeyEvent.VK_ESCAPE)
				i = 1;
			else if (key == KeyEvent.VK_SPACE)
				c.jump();
			else if (key == KeyEvent.VK_ENTER) {
				try {
					Printer p = new Printer("TEMPLE.GRID");
					for (Clippable c : Grid.world) {
						if (c instanceof Block) {
							String s = c.getClass().getSimpleName();
							p.print(s + "\t\t");
							p.print(2 * c.getX() + "\t");
							p.print(2 * c.getY() + "\t");
							p.print(2 * c.getZ() + "\t\n");
						}
					}
					p.flush();
					p.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else if (key == KeyEvent.VK_R)
				Grid.load("TEMPLE.GRID");
			else if (key == KeyEvent.VK_LEFT) {
				if (c.lastShip != null)
					Client.listener.sendMessafe(":port " + c.lastShip.name);
			} else if (key == KeyEvent.VK_RIGHT) {
				if (c.lastShip != null)
					Client.listener.sendMessafe(":star " + c.lastShip.name);
			} else if (key == KeyEvent.VK_UP) {
				if (c.lastShip != null)
					Client.listener.sendMessafe(":acel " + c.lastShip.name);
			} else if (key == KeyEvent.VK_DOWN) {
				if (c.lastShip != null)
					Client.listener.sendMessafe(":deac " + c.lastShip.name);
			}

			if (i == 1) {
				break;
			}
		}
		display.repaint();
		repaint();
	}

	// ---------------------- support for mouse events ----------------------

	/**
	 * Called when the user presses a mouse button on the display.
	 */
	public void mousePressed(MouseEvent evt) {
		if (dragging) {
			return; // don't start a new drag while one is already in progress
		}
		int x = evt.getX();
		int y = evt.getY();
		dragging = true; // might not always be correct!
		prevX = startX = x;
		prevY = startY = y;
		display.repaint(); // only needed if display should change
	}

	/**
	 * Called when the user releases a mouse button after pressing it on the
	 * display.
	 */
	public void mouseReleased(MouseEvent evt) {
		if (!dragging) {
			return;
		}
		dragging = false;

	}

	/**
	 * Called during a drag operation when the user drags the mouse on the display/
	 */
	public void mouseDragged(MouseEvent evt) {
		if (!dragging) {
			return;
		}
		int x = evt.getX();
		int y = evt.getY();
		// TODO: respond to mouse drag to new point (x,y)
		prevX = x;
		prevY = y;
		display.repaint();
	}

	/**
	 * Don't call.
	 */
	public void mouseMoved(MouseEvent evt) { // System.out.println("moved");
		// c.ship.sail();
	}

	/**
	 * Dont call
	 */
	public void mouseClicked(MouseEvent evt) {
		if (evt.getButton() == (MouseEvent.BUTTON1)) {
			System.out.println("left click");
			// Client.listener.sendMessafe(":shootEvent");
			for (int i = 0; i < Grid.world.size(); i++) {
				if (Grid.world.get(i).getSelection()) {
					Grid.world.remove(i);
				}
			}
		} else if (evt.getButton() == (MouseEvent.BUTTON3)) {
			System.out.println("right click");
			for (int i = 0; i < Grid.world.size(); i++) {
				if (Grid.world.get(i).getSelection()) {
					MVector location = MVector.mult(Grid.world.get(i).getCoords(), 2)
							.add(Grid.world.get(i).getDirection(c));
					String entry = "Grass" + "\t" + "\t" + location.x + "\t" + location.y + "\t" + location.z;
					bob.write(entry);

				}
			}
		}
//		try {
//			FileWrapper fr = new FileWrapper("TEMPLE.GRID");
//			String str = "";
//			while(fr.ready()){
//				str += fr.readLine()+"\n";
//			}
//			fr.close();
//			Printer out = new Printer("TEMPLE.GRID");
//			out.print(str);
//			int x = (int)(2*(c.position.x+Math.cos(c.getPan()-1))+0.5);
//			int y = (int)(c.position.y*2);
//			int z = (int)(2*(c.position.z+Math.sin(c.getPan()))+0.5);
//			out.print("Block\t\t"+x+"\t"+y+"\t"+z+"\n");
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * dont call
	 */
	public void mouseEntered(MouseEvent evt) {
	}

	/**
	 * don't cal;
	 */
	public void mouseExited(MouseEvent evt) {
		// c.getRobot().mouseMove(window.getX()+getWidth()/2,
		// window.getY()+getWidth()/2);
	}

//	public static void main(String[] args) {
//		_driver_start(args);
//
//	}

}
