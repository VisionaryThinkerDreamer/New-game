import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class OriginalTrack
{
	 private Image trackImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("Tracks.png"));
	 private Point trackPosition;
	public Image getTrackImage()
	{
		return trackImage;
	}

	 
}