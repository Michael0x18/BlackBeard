package world.viewport;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; // for drawing the sample teapot

import world.constructs.blocks.Clippable;
import world.constructs.blocks.SensorBlock;
import world.constructs.Ship;
import world.io.FileWrapper;
import world.io.Printer;
import world.ui.JoglMenu;
import javafx.scene.paint.Color;

/**
 * REEEEEREEEEEEEEEtard
 */
public class JoglPane extends JPanel
		implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, ActionListener {
	/**
	 * UID-no use
	 */
	private static final long serialVersionUID = 6999533994092038855L;
	private static JFrame window;
	private static Cursor blankCursor;
	private static JPanel pl;
	private CopyOnWriteArrayList<Integer> Keys = new CopyOnWriteArrayList<Integer>();
	private GLU glu = new GLU();
	private boolean isFullScreen;
	public static JoglPane currentLoader;

	public static void _driver_start(String[] flags) {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Grid.loadTowerOfBabel();
		Grid.load("TEMPLE.GRID");
//		Ship sh = new Ship();
		
		window = new JFrame("JOGL");
		JoglPane j = new JoglPane();
//		pl = new JPanel() {
//			public void paintComponent(Graphics g) {
//				this.setBackground(new java.awt.Color(0, 0, 0, 0));
//				this.setOpaque(false);
//				g.fillRect(0, 0, 100, 100);
//			}
//		};
		// window.add(pl);

		window.setContentPane(j);
		window.pack();
		window.setLocation(50, 50);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		j.setCursor(blankCursor);
		// window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// window.setCursor(new Cursor(0));

	}

	public void cursorOff() {
		this.setCursor(blankCursor);
	}

	public void cursorOn() {
		this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
	}

	private GLJPanel display;
	private Timer animationTimer;
	private float rotateX, rotateY; // rotation amounts about axes, controlled
									// by keyboard
	private Player c;
	private SensorBlock sb;
	private JMenuBar jmb;
	private JMenu file;

	public Player getC() {
		return c;
	}

//	 public void paint(Graphics g){
//	this.setBackground(new Color(0,0,0,0));
//	this.setOpaque(false);
//	g.fillRect(0, 0, 100, 100);
//	 }

	public JoglPane() {
		currentLoader = this;
		GLCapabilities caps = new GLCapabilities(null);
		display = new GLJPanel(caps);
		display.setPreferredSize(new Dimension(600, 600)); // TODO: set display
															// size here
		display.addGLEventListener(this);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		// TODO: Other components could be added to the main panel.

		rotateX = 0; // initialize some variables used in the drawing.
		rotateY = 0;

		// TODO: Uncomment the next two lines to enable keyboard event handling
		requestFocusInWindow();
		display.addKeyListener(this);

		// TODO: Uncomment the next one or two lines to enable mouse event
		// handling
		display.addMouseListener(this);
		display.addMouseMotionListener(this);

		// c = new Camera();
		// c.setScale(3);
		// c.installTrackball(this);
		// c.setScale(3);
		//Ship sh = new Ship();
		//c = new Player(this,sh);
		c = new Player(this);
		c.setScale(0.6);
		c.moveTo(0, 5, 0);
		sb = new SensorBlock(c,1,0,0);
		jmb = JoglMenu.applyMenu(window);
		file = new JMenu("File");
		jmb.add(file);
		// TODO: Uncomment the following line to start the animation
		startAnimation();

	}

	// --------------- Methods of the GLEventListener interface -----------

	private GLUT glut = new GLUT(); // for drawing the teapot
	private double xt;
	boolean disabled;
	private boolean fullscreen;

	/**
	 * This method is called when the OpenGL display needs to be redrawn.
	 */
	public void display(GLAutoDrawable drawable) {
		// called when the panel needs to be drawn

		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor((float) Color.DEEPSKYBLUE.getRed(), (float) Color.DEEPSKYBLUE.getGreen(),
				(float) Color.DEEPSKYBLUE.getBlue(), 40);

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		//
		// gl.glMatrixMode(GL2.GL_PROJECTION); // TODO: Set up a better
		// projection?
		// gl.glEnable(GL2.GL_PERSPECTIVE_CORRECTION_HINT);
		//
		// gl.glLoadIdentity();
		// gl.glOrtho(-1, 1, -1, 1, -2, 2);
		// GLU glu = new GLU();
		// glu.gluPerspective(5, 5, 0.1, 1000.0);
		// gl.glMatrixMode(GL2.GL_MODELVIEW);
		//
		gl.glLoadIdentity(); // Set up modelview transform.
		// c.setOrthographic(true);
		// c.apply(gl);
		c.act(Grid.world);
		c.draw(gl, this, window);
		gl.glTranslated(xt, 0, 0);
		gl.glRotatef(rotateY, 0, 1, 0);
		gl.glRotatef(rotateX, 1, 0, 0);

		GL2 gl2 = gl.getGL2();

		for (Clippable c : Grid.world) {
			c.draw(glut, glu, gl, gl.getGL2());
		}
//		for(Ship s : Grid.newShips) {
//			for(Ship sh : Grid.ships) {
//				if(sh.name.equals(s.name)) {
//					Grid.ships.remove(sh);
//				}
//			}
//			Grid.newShips.add(s);
//		}
//		Grid.ships = Grid.newShips;
//		Grid.newShips = new CopyOnWriteArrayList<Ship>();
		for(Ship s : Grid.ships) {
			gl.glRotated(s.rot, 0, 1, 0);
			gl.glTranslated(s.x, s.y, s.z);
			for(Clippable c : s.blocks) {
				c.draw(glut, glu, gl, gl2);
			}
			gl.glTranslated(s.x, s.y, s.z);
			gl.glRotated(-s.rot, 0,1, 0);
		}
//		sb.draw(glut, glu, gl, gl2);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_NORMALIZE);
		//c.ship.draw(glut, glu, gl, gl2);
		//
		// // float[] ambientLight = { 0.1f, 0.f, 0.f,0f }; // weak RED ambient
		// // gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		//
		// float[] diffuseLight = { 1f,2f,1f,0f }; // multicolor diffuse
		// gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);

		//

		float[] position = { 0, 20, 0, 1 };
		//float[] position = {(float) c.getPosition().x,(float) c.getPosition().y,(float) c.getPosition().z,1};
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);

		gl2.glEnable(GL2.GL_LIGHT0);
		// glu.glutSwapBuffers();
		// gl.glPushMatrix();
//		gl.glPushAttrib(1);
//		gl2.glMatrixMode(gl2.GL_MODELVIEW);
//		gl2.glLoadIdentity();
//		gl2.glMatrixMode(gl2.GL_PROJECTION);
//		gl2.glLoadIdentity();
//		glu.gluOrtho2D(-100, 100, -100, 100);
//		gl.glDisable(gl.GL_DEPTH_TEST);
//		gl.glDisable(gl.GL_CULL_FACE);
//		gl.glDisable(gl2.GL_TEXTURE_2D);
//		gl.glDisable(gl2.GL_LIGHTING);
//		gl2.glColor3f(1, 1, 1);
//		gl2.glPushMatrix();
//		gl2.glBegin(gl2.GL_QUADS);
//		gl2.glVertex3f(-1.0f, 5.0f, 0.0f);
//		gl2.glVertex3f(-1.0f, -5.0f, 0.0f);
//		gl2.glVertex3f(1.0f, -5.0f, 0.0f);
//		gl2.glVertex3f(1.0f, 5.0f, 0.0f);
//		gl2.glEnd();
//		gl2.glBegin(gl2.GL_QUADS);
//		gl2.glVertex3f(-5.0f, 1.0f, 0.0f);
//		gl2.glVertex3f(-5.0f, -1.0f, 0.0f);
//		gl2.glVertex3f(5.0f, -1.0f, 0.0f);
//		gl2.glVertex3f(5.0f, 1.0f, 0.0f);
//		gl2.glEnd();
//		gl2.glEnable(gl2.GL_DEPTH_TEST);
//		gl.glPopAttrib();

//		gl2.glMatrixMode(gl.GL_PROJECTION);
//		gl2.glLoadIdentity();
//		glu.gluPerspective(60, (float)window.getWidth()/window.getHeight(), 0.01f, 100.0f);
		// gl.glPopMatrix();

	}

	/**
	 * This is called when the GLJPanel is first created. It can be used to
	 * initialize the OpenGL drawing context.
	 */
	public void init(GLAutoDrawable drawable) {
		// called when the panel is created
		GL2 gl = drawable.getGL().getGL2();
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
		
		if(e.getModifiersEx() != 0)
			return;
		Keys.add(Integer.valueOf(e.getKeyCode()));
		repaint();

	}

	/**
	 * Called when the user types a character.
	 */
	public void keyTyped(KeyEvent e) {
		char ch = e.getKeyChar(); // Which character was typed.
	}

	public void keyReleased(KeyEvent e) {
		
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
		}
		else if (e.getKeyCode() == KeyEvent.VK_G)
				c.toggleGravity();
		while (Keys.contains(Integer.valueOf(e.getKeyCode())))
			Keys.remove(Integer.valueOf(e.getKeyCode()));
		repaint();
	}

	// --------------------------- animation support ---------------------------

	private int frameNumber = 0;

	private boolean animating;

	private void updateFrame() {
		// window.setLayout(null);
		// TODO: add any other updating required for the next frame.
		frameNumber++;
		// this.setBounds(-300,-300,window.getWidth()+600,window.getHeight()+600);

	}

	public void startAnimation() {
		if (!animating) {
			if (animationTimer == null) {
				animationTimer = new Timer(40, this);
			}
			animationTimer.start();
			animating = true;
		}
	}

	public void pauseAnimation() {
		if (animating) {
			animationTimer.stop();
			animating = false;
		}
	}

	public void actionPerformed(ActionEvent evt) {
		updateFrame();
		int i;
		for (Integer key : Keys) {
			// System.out.println(e);
			if (key == KeyEvent.VK_LEFT)
				rotateY -= 5;
			else if (key == KeyEvent.VK_RIGHT)
				rotateY += 5;
			else if (key == KeyEvent.VK_DOWN)
				rotateX += 5;
			else if (key == KeyEvent.VK_UP)
				rotateX -= 5;
			else if (key == KeyEvent.VK_HOME)
				rotateX = rotateY = 0;
			else if (key == KeyEvent.VK_W)
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
			else if (key == KeyEvent.VK_R)
				Grid.load("TEMPLE.GRID");
			
			

		}
		display.repaint();
		repaint();
	}

	// ---------------------- support for mouse events ----------------------

	private boolean dragging; // is a drag operation in progress?

	private int startX, startY; // starting location of mouse during drag
	private int prevX, prevY; // previous location of mouse during drag

	/**
	 * Called when the user presses a mouse button on the display.
	 */
	public void mousePressed(MouseEvent evt) {
		if (dragging) {
			return; // don't start a new drag while one is already in progress
		}
		int x = evt.getX();
		int y = evt.getY();
		// TODO: respond to mouse click at (x,y)
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
		
		// TODO: finish drag (generally nothing to do here)
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

	public void mouseMoved(MouseEvent evt) { // System.out.println("moved");
		//c.ship.sail();
	}

	public void mouseClicked(MouseEvent evt) {
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

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseExited(MouseEvent evt) {
		// c.getRobot().mouseMove(window.getX()+getWidth()/2,
		// window.getY()+getWidth()/2);
	}

//	public static void main(String[] args) {
//		_driver_start(args);
//
//	}

}
