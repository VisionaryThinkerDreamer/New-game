import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class DavidsTrack
{
	 private Image trackImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Tracks.png"));
	 private Point trackPosition;
	 DavidsTrack(Point trackPosition)
		{
		 this.trackPosition = trackPosition;
		}
	 public Image getTrackImage()
	{
		return trackImage;
	}
	
}