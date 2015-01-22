import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

public class MapObject{ //Any object in the game (rock, player, enemy, berry)
	protected BufferedImage img;
	protected double x,y; //Coordinates of object in level
	protected double mapx,mapy; //Map offset of level
	protected Map map;
	protected int width,height; //Area
	protected int boxwidth,boxheight; //Hitbox area
	public MapObject(Map map,double x,double y){
		this.map = map;
		this.x = x;
		this.y = y;
	}
	public Rectangle2D getRect(){ //Get area of object
		return new Rectangle2D.Double(x-width/2,y-height/2,width,height);
	}
	public Rectangle2D getBoxRect(){ //Get hitbox area of object
		return new Rectangle2D.Double(x-boxwidth/2,y-boxheight/2,boxwidth,boxheight);
	}
	public Point2D getPos(){ //Position relative to level
		return new Point2D.Double(x,y);
	}
	public Point2D getScreenPos(){ //Position relative to screen
		return new Point2D.Double(x-mapx,y-mapy);
	}
}
