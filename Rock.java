import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
public class Rock extends MapObject
{
    public Rock(Map map,double x,double y)
    {
	super(map,x,y);
	boxwidth = width = 64;
	boxheight = height = 64;
    }

    public void draw(Graphics g)
    {
	mapx = map.getX();
	mapy = map.getY();
	g.setColor(Color.lightGray);
	g.fillRoundRect((int)(x-mapx-width/2),(int)(y-mapy-height/2),width,height,15,15);
    }

    public boolean checkStuck(double x, double y)
    {
	return (x >= this.x-80 && x <= this.x+80 && y >= this.y-80 && y <= this.y+80);
    }
}
