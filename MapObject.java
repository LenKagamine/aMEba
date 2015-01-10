import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
public class MapObject{
    protected BufferedImage img;
    protected double x,y;
    protected double mapx,mapy;
    protected Map map;
    protected int width,height;
    protected int boxwidth,boxheight;
    public MapObject(Map map,double x,double y){
	this.map = map;
	this.x = x;
	this.y = y;
    }
    public Rectangle2D getRect(){
	return new Rectangle2D.Double(x-width/2,y-height/2,width,height);
    }
    public Rectangle2D getBoxRect(){
	return new Rectangle2D.Double(x-boxwidth/2,y-boxheight/2,boxwidth,boxheight);
    }
    public Point2D getPos(){
	return new Point2D.Double(x,y);
    }
    public Point2D getScreenPos(){
	return new Point2D.Double(x-mapx,y-mapy);
    }
}
