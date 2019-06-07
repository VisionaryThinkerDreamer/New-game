
import javax.script.ScriptException;
import javax.swing.*;

import org.omg.Messaging.SyncScopeHelper;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import static javax.imageio.ImageIO.read;

/***********************************************************************************************
 * David Frieder's Thomas Game Copyright 2018 David Frieder 2/23/2018 rev 3.5
 * 
 * Added arraylist to tracks method in the track class
 * 
 * Can create tracks wherever we want Trying to consolidate track methods vic
 * 10/9/2018
 ***********************************************************************************************/
public class DavidsBeatEmUpController extends JComponent implements ActionListener, Runnable, KeyListener
{
	public Point upperTrackPosition = new Point(250, 300);
	public boolean isGoingRight = false;
	int trackYPos;
	int upperTrackWidth;
	int lowerTrackYPos;
	int lowerTrackWidth;
	Rectangle upperTrackBox;
	Shape upperTrackShape;
	// private Rectangle lowerTrackBox2;
	// private Shape lowerTrackShape;
	// private AffineTransform lowerTrackTransform;
	int thomasBoxWidth;
	int thomasBoxHeight;
	Rectangle thomasBox;
	Rectangle thomasTrackIntersectionBox;
	Shape thomasShape;
	// private Rectangle2D.Double upperTrackDetectionZone = new
	// Rectangle2D.Double(0, 0, 200, 49);
	private URL thomasThemeAddress = getClass().getResource("ThomasThemeSong.wav");
	private AudioClip thomasThemeSong = JApplet.newAudioClip(thomasThemeAddress);
	private Image[] thomasSpriteImageArray = new Image[8];
	private Image[] reverseThomasImageArray = new Image[8];
	// private Image gun =
	// Toolkit.getDefaultToolkit().createImage(getClass().getResource("Minigun_SU.png"));
	private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	private JFrame mainGameWindow = new JFrame("NewGame");// Makes window with
	// private AffineTransform identityTx = new AffineTransform();
	private AffineTransform thomasTransform = new AffineTransform();// Set
	private AffineTransform backgroundTx = new AffineTransform();
	private AffineTransform upperTrackTransform = new AffineTransform();
	private Timer animationTicker = new Timer(40, this);
	private Timer jumpingTicker = new Timer(800 / 60, this);
	private Image thomasSpriteImage;
	private Image reverseThomasImage;
	private int thomasSpriteImageCounter;
	private Image roadImage;
	// private int groundLevelTrackYPos = (int) (heightOfScreen * 0.809);
	private boolean isGoingLeft;
	private boolean isJumping;
	private boolean isFalling;
	private int initialJumpingVelocity = -31;
	private int initialFallingVelocity = 0;
	public int jumpingVelocity = initialJumpingVelocity;
	public int fallingVelocity = initialFallingVelocity;
	// private int movingVelocity;
	private int gravityAcceleration = 1;
	private Graphics2D g2;
	private int roadWidth;
	
	private int thomasYOffsetFromGround = 0;
	private boolean lastWayFacing = true;
	public DavidsThomas thomas = new DavidsThomas();
//	public Track upperTrack = new Track();

	/***********************************************************************************************
	 * Main
	 ***********************************************************************************************/
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new DavidsBeatEmUpController());
	}

	/***********************************************************************************************
	 * Run
	 ***********************************************************************************************/
	@Override
	public void run()
	{
		loadImages();
		setUpMainGameWindow();
		thomasThemeSong.loop();
		animationTicker.start();
		jumpingTicker.start();
	}

	/***********************************************************************************************
	 * Paint
	 ***********************************************************************************************/
	public void paint(Graphics g)
	{
		g2 = (Graphics2D) g;
		drawThomas();
		drawRoad();
		drawObstacle();
//		drawTracks(0, heightOfScreen / 2, 3);// ...Draw upper tracks left half
//		drawTracks(0, 3 * heightOfScreen / 4, 5);
		if (testIntersection(thomasShape, upperTrackShape))
		{
			if (jumpingVelocity > 0 && thomasYOffsetFromGround < trackYPos)
			{
				jumpingVelocity = initialJumpingVelocity;
				isJumping = false;
				isFalling = false;
				g2.setTransform(thomasTransform);
			}
		} else if (testIntersection(thomasShape, upperTrackShape) == false)
		{
			isFalling = true;
			if (thomasYOffsetFromGround > 0)
			{
				jumpingVelocity = initialJumpingVelocity;
				thomasYOffsetFromGround = 0;
				isJumping = false;
			}
			repaint();
		}
	}

	private void drawObstacle()
	{
		g2.setTransform(backgroundTx);
		g2.translate(-widthOfScreen, heightOfScreen - 400);
		g2.fillRect(-200, 0, 50, 300);
	}

	/***********************************************************************************************
	 * Draw road
	 ***********************************************************************************************/
	private void drawRoad()
	{
		g2.setTransform(backgroundTx);
		g2.translate(-widthOfScreen, heightOfScreen - 200);
		g2.scale(1.5, 1.5);
		for (int i = 0; i < (2 * (widthOfScreen / roadImage.getWidth(null))) + 2; i++) // fits
		{
			g2.drawImage(roadImage, 0, 0, null);
			g2.translate(roadImage.getWidth(null), 0);
		}
		if (Math.abs(backgroundTx.getTranslateX()) > 4000)
		{
			System.out.println("activated");
			for (int i = 0; i < 3; i++)
			{
				g2.drawImage(roadImage, 0, 0, null);
				g2.drawOval(0, 0, 50, 50);
			}
		}
	}

	/***********************************************************************************************
	 * Draw any tracks
	 ***********************************************************************************************/
//	private void drawTracks(double trackXPos, int trackYPos, int numberOfTracks)
//	{
//		Image trackImage = upperTrack.getTrackSectionImage();
//		int trackWidth = trackImage.getWidth(null);
//		int trackHeight = trackImage.getHeight(null);
//		g2.setTransform(backgroundTx);// this is an identity transform
//		g2.translate(trackXPos, trackYPos); // center in screen
//		g2.scale(1.5, 1.5);
//		for (int i = 1; i <= numberOfTracks; i++)
//		{
//			g2.drawImage(trackImage, 0, 0, null);
//			upperTrackWidth = trackImage.getWidth(null);
//			upperTrackBox = new Rectangle(0, 0, trackWidth, trackYPos);
//			upperTrackShape = upperTrackBox.getBounds();
//			upperTrackTransform = g2.getTransform();
//			g2.translate(trackWidth, 0);
//		}
//	}

	/***********************************************************************************************
	 * Draw Thomas with sprite files
	 ***********************************************************************************************/
	public void drawThomas()
	{
		g2.setTransform(backgroundTx);
		thomasTransform.setToTranslation(widthOfScreen / 3, heightOfScreen - 420);
		g2.setTransform(thomasTransform);
		try
		{
			thomasSpriteImageArray = thomas.getThomasSpriteImageArray();
			reverseThomasImageArray = thomas.getReverseThomasImageArray();
			thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
			thomasSpriteImage = thomasSpriteImageArray[thomasSpriteImageCounter];
			reverseThomasImage = reverseThomasImageArray[thomasSpriteImageCounter];
			thomasTransform.setToTranslation(widthOfScreen / 3, heightOfScreen - 420 + thomasYOffsetFromGround);
			g2.setTransform(thomasTransform);
			thomasBox = new Rectangle(0, 0, thomasBoxWidth, thomasBoxHeight);
			thomasTrackIntersectionBox = new Rectangle(0, thomasBoxHeight, thomasBoxWidth, -thomasBoxHeight / 5);
			thomasShape = thomasBox.getBounds();

			if (isGoingLeft || lastWayFacing == true)
			{
				g2.drawImage(thomasSpriteImage, 0, 0, null);
				lastWayFacing = true;
				thomasBoxWidth = thomasSpriteImage.getWidth(null);
				thomasBoxHeight = thomasSpriteImage.getHeight(null);
			}
			if (isGoingRight || lastWayFacing == false)
			{
				g2.drawImage(reverseThomasImage, 0, 0, null);
				lastWayFacing = false;
				thomasBoxWidth = thomasSpriteImage.getWidth(null);
				thomasBoxHeight = thomasSpriteImage.getHeight(null);
			}
		} catch (Exception ex)
		{
			System.out.println("error reading thomas thomasSpriteImage from thomas sprite thomasSpriteImage array");
		}
	}

	/***********************************************************************************************
	 * Get .png files, convert to Image and load sprite array
	 ***********************************************************************************************/
	private void loadImages()
	{
		roadImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("ground.png"));

		roadWidth = roadImage.getWidth(null);
	}

	/***********************************************************************************************
	 * Set up main JFrame
	 ***********************************************************************************************/
	private void setUpMainGameWindow()
	{
		mainGameWindow.setTitle("Thomas the tank");
		mainGameWindow.setSize(widthOfScreen, heightOfScreen);
		mainGameWindow.add(this);// Adds the paint method to the JFrame
		mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGameWindow.getContentPane().setBackground(new Color(200, 235, 255));
		mainGameWindow.setVisible(true);
		mainGameWindow.addKeyListener(this);
	}

	/***********************************************************************************************
	 * Check for intersections
	 ***********************************************************************************************/
	public boolean testIntersection(Shape shapeA, Shape shapeB)
	{
		Area areaA = null;
		Area areaB = null;
		areaA = new Area(shapeA);
		if (shapeB != null)// put this in to fix compile problem
		{
			areaB = new Area(shapeB);
		}
		areaA.transform(thomasTransform);
		areaB.transform(upperTrackTransform);
		if (shapeB != null)// put this in to fix compile problem
		{
			areaA.intersect(areaB);
		}
		return !areaA.isEmpty();
	}

	/***********************************************************************************************
	 * Respond to key typed
	 ***********************************************************************************************/
	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	/***********************************************************************************************
	 * Respond to key pressed
	 ***********************************************************************************************/
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) // going right
		{
			isGoingRight = true;
			isGoingLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) // going left
		{
			isGoingLeft = true;
			isGoingRight = false;
			animationTicker.start();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			isGoingLeft = false;
			isGoingRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			isJumping = true;
		}
	}

	/***********************************************************************************************
	 * Respond to key released
	 ***********************************************************************************************/
	@Override
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) // going right
		{
			isGoingRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) // going left
		{
			isGoingLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
		}
	}

	/***********************************************************************************************
	 * Action Performed.....Respond to animation ticker and paint ticker
	 ***********************************************************************************************/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		repaint();
		thomasTransform.setToTranslation(0, thomasYOffsetFromGround);
		if (e.getSource() == animationTicker)
		{
			if (isGoingLeft == true)
			{
				thomasSpriteImageCounter++;
				backgroundTx.setToTranslation(backgroundTx.getTranslateX() + 30, 0);
				if (backgroundTx.getTranslateX() > widthOfScreen)
				{
					// backgroundTx.setToTranslation(-widthOfScreen, 0);
				}
			}
			if (isGoingRight == true)
			{
				thomasSpriteImageCounter++;
				if (thomasSpriteImageCounter < 0)
				{
					thomasSpriteImageCounter = 7;
				}
				backgroundTx.setToTranslation(backgroundTx.getTranslateX() - 30, 0);
				if (backgroundTx.getTranslateX() < -widthOfScreen)
				{
					// backgroundTx.setToTranslation(widthOfScreen, 0);
				}
			}
			System.out.println(backgroundTx.getTranslateX());
			repaint();
		}
		if (isJumping == true)
		{
			jump(e);
		}
		if (isFalling == true)
		{
			fall(e);
		}
	}

	public void jump(ActionEvent e)
	{
		if (e.getSource() == jumpingTicker)
		{
			if (g2 != null)
			{
				thomasYOffsetFromGround += jumpingVelocity;
				jumpingVelocity += gravityAcceleration;
			}
			if (thomasYOffsetFromGround > 0)
			{
				jumpingVelocity = initialJumpingVelocity;
				thomasYOffsetFromGround = 0;
				isJumping = false;
				isFalling = false;
			}
			repaint();
		}
	}
	public void fall(ActionEvent e)
	{
		if (e.getSource() == jumpingTicker && !isJumping)
		{
			if (g2 != null)
			{
				thomasYOffsetFromGround += fallingVelocity;
				fallingVelocity += gravityAcceleration;
			}
			if ((thomasYOffsetFromGround > 0 || testIntersection(thomasShape, upperTrackShape)))
			{
				fallingVelocity = initialFallingVelocity;
				thomasYOffsetFromGround = 0;
				isFalling = false;
				isJumping = false;
			}
			isFalling = false;
			repaint();
		}
	}
	public void getScreenHeight()
	{
		int heightOfScreen = this.heightOfScreen;
	}
	public void getScreenWidth()
	{
		int widthOfScreen = this.widthOfScreen;
	}
	public void getBackGroundTx()
	{
		AffineTransform backgroundTx = this.backgroundTx;
	}

}