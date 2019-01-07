import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Track
{
    private Image trackSectionImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("tracks.png"));
    private Point trackPosition;
    private Rectangle2D.Double trackBoundingBox;
    private int numberOfTrackSections;

   

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

