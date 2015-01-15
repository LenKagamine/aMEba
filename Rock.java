import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
public class Rock extends MapObject
{
    public Rock(Map map,double x,double y)
    {
        super(map,x,y);
        boxwidth = 64;
        boxheight = 64;
        //boxwidth = 64;
        //boxheight = 64;
    }

    public void draw(Graphics g)
    {
        mapx = map.getX();
        mapy = map.getY();
        g.setColor(Color.lightGray);
        g.fillRoundRect((int)(x-mapx-32),(int)(y-mapy-32),64,64,15,15);
    }

    public boolean checkStuck(double x, double y)
    {
        if (x >= this.x-80 && x <= this.x+80 && y >= this.y-80 && y <= this.y+80)
            return true;
        else
            return false;
    }
}
