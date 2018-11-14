import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Track
{
	 private Image trackImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Tracks.png"));
	 private Point trackPosition;
	private int trackHeight;
	private int trackWidth;
	 public Image getTrackImage()
	{
		return trackImage;
	}
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
	}

	 
}
