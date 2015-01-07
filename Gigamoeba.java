import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.Point2D;

public class Gigamoeba extends Organism
{
    private boolean inview = false;
    private double targetx,targety;
    public Gigamoeba (double x, double y)
    {
        super(x,y);
        try{
            img = ImageIO.read(getClass().getResourceAsStream("Smaller pics/gigamoeba.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
        angle = 0.0;
        width = img.getWidth();
        height = img.getHeight();
    }

    public void update()
    {
        //angle = Math.atan2(yspeed,xspeed);

    }

    public void draw(Graphics g)
    {
        super.draw(g);
        
    }
}
