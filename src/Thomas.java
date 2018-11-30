import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Thomas
{
    public Image[] leftFacingThomasSpriteImageArray = new Image[8];
    public Image[] rightFacingThomasSpriteImageArray = new Image[8];
    private int thomasSpriteImageCounter;
    private Rectangle2D.Double thomasBoundingBox;
    private Point thomasPosition;
    private Shape thomasBoundingBoxShape;

    public Thomas(Point thomasPosition)
    {
        int thomasBoundingBoxWidth;
        int thomasBoundingBoxHeight;
        try
        {
            leftFacingThomasSpriteImageArray[0] = read(getClass().getResource("Thomas1.png"));
            leftFacingThomasSpriteImageArray[1] = read(getClass().getResource("Thomas2.png"));
            leftFacingThomasSpriteImageArray[2] = read(getClass().getResource("Thomas3.png"));
            leftFacingThomasSpriteImageArray[3] = read(getClass().getResource("Thomas4.png"));
            leftFacingThomasSpriteImageArray[4] = read(getClass().getResource("Thomas5.png"));
            leftFacingThomasSpriteImageArray[5] = read(getClass().getResource("Thomas6.png"));
            leftFacingThomasSpriteImageArray[6] = read(getClass().getResource("Thomas7.png"));
            leftFacingThomasSpriteImageArray[7] = read(getClass().getResource("Thomas8.png"));
            rightFacingThomasSpriteImageArray[0] = read(getClass().getResource("Reversed Thomas1.png"));
            rightFacingThomasSpriteImageArray[1] = read(getClass().getResource("Reversed Thomas2.png"));
            rightFacingThomasSpriteImageArray[2] = read(getClass().getResource("Reversed Thomas3.png"));
            rightFacingThomasSpriteImageArray[3] = read(getClass().getResource("Reversed Thomas4.png"));
            rightFacingThomasSpriteImageArray[4] = read(getClass().getResource("Reversed Thomas5.png"));
            rightFacingThomasSpriteImageArray[5] = read(getClass().getResource("Reversed Thomas6.png"));
            rightFacingThomasSpriteImageArray[6] = read(getClass().getResource("Reversed Thomas7.png"));
            rightFacingThomasSpriteImageArray[7] = read(getClass().getResource("Reversed Thomas8.png"));
            thomasBoundingBoxWidth = leftFacingThomasSpriteImageArray[0].getWidth(null);
            thomasBoundingBoxHeight = leftFacingThomasSpriteImageArray[0].getHeight(null);
            this.thomasPosition = thomasPosition;
            thomasBoundingBox = new Rectangle2D.Double(thomasPosition.x + 80, thomasPosition.y, thomasBoundingBoxWidth-160, thomasBoundingBoxHeight);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Image nextThomasSpriteImage(boolean goingRight, boolean goingLeft)
    {
        if (goingRight)
        {
            thomasSpriteImageCounter++;
            thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
            return rightFacingThomasSpriteImageArray[thomasSpriteImageCounter];
        }
        if (goingLeft)
        {
        	thomasSpriteImageCounter++;
            thomasSpriteImageCounter = thomasSpriteImageCounter % 8;
            return leftFacingThomasSpriteImageArray[thomasSpriteImageCounter];
        }
        else{
        	return null;
        }
    }

    public Image[] getForwardThomasSpriteImageArray()
    {
        return leftFacingThomasSpriteImageArray;
    }
    
    public Image[] getBackwardThomasSpriteImageArray()
    {
        return rightFacingThomasSpriteImageArray;
    }

    public void setThomasPosition(Point thomasPosition)
    {
        this.thomasPosition = thomasPosition;
    }

    public Point getThomasPosition()
    {
        return thomasPosition;
    }


    public Rectangle2D.Double getThomasBoundingBox()
    {
        return thomasBoundingBox;
    }

    public void setThomasBoundingBox(Rectangle2D.Double thomasBoundingBox)
    {
        this.thomasBoundingBox = thomasBoundingBox;
        thomasBoundingBoxShape = thomasBoundingBox.getBounds();
    }
}


