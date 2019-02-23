import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Track extends Rectangle2D.Float
{
    private Image trackSectionImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("tracks.png"));
    private Point trackPosition;
    private Rectangle2D.Double trackBoundingBox;
    private int numberOfTrackSections;
    private int trackWidth;
	int trackHeight;
	private AffineTransform backgroundTx = new AffineTransform();
	private AffineTransform upperTrackTransform = new AffineTransform();
	private ArrayList<Track> tracks;

   
    /***********************************************************************************************
	 * Draw any tracks
	 ***********************************************************************************************/
	private void drawTracks(double trackXPos, int trackYPos, int numberOfTracks, ArrayList<Track> tracks)
	{
		Image trackImage = getTrackSectionImage();
		trackWidth = trackImage.getWidth(null);
		trackHeight = trackImage.getHeight(null);
		g2.setTransform(backgroundTx);// this is an identity transform
		g2.translate(trackXPos, trackYPos); // center in screen
		g2.scale(1.5, 1.5);
		for (int i = 1; i <= numberOfTracks; i++)
		{
			g2.drawImage(trackImage, 0, 0, null);
			upperTrackWidth = trackImage.getWidth(null);
			upperTrackBox = new Rectangle(0, 0, trackWidth, trackYPos);
			upperTrackShape = upperTrackBox.getBounds();
			upperTrackTransform = g2.getTransform();
			g2.translate(trackWidth, 0);
		}

    public Image getTrackSectionImage()
    {
        return trackSectionImage;
    }
    
    public int getTrackSectionHeight()
    {
    	return trackSectionImage.getHeight(null);
    }

    public Point getTrackPosition()
    {
        return trackPosition;
    }

    public int getNumberOfTrackSections()
    {
        return numberOfTrackSections;
    }

    public Rectangle2D.Double getTrackBoundingBox()
    {
        return trackBoundingBox;
    }
}

