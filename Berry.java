import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;

public class Berry{ //purple berries give an amount of health back, red berries heal much more
    private int recover;
    private double x, y;
    private Map map;
    private BufferedImage berry;
    private static Image[] berries;
    private double mapx,mapy;
    private int width,height;
    public Berry (Map map,double x, double y){
	this.map = map;
	this.x = x;
	this.y = y;
	if(Math.random()*15 < 1){ //super
	    recover = 120;
	    try{
		berry = ImageIO.read(getClass().getResourceAsStream("superberry.png"));
	    } catch(Exception e){
		e.printStackTrace();
	    }
	}
	else{
	    recover = 12;
	    try{
		berry = ImageIO.read(getClass().getResourceAsStream("berry.png"));
	    } catch(Exception e){
		e.printStackTrace();
	    }

	}
	width = berry.getWidth();
	height = berry.getHeight();
    }

    public int recoverHealth(){
	return recover;
    }

    public Rectangle2D getRect(){
	return new Rectangle2D.Double(x-width/2,y-height/2,width,height);
    }

    public void draw(Graphics g){
	mapx = map.getX();
	mapy = map.getY();
	g.drawImage(berry, (int)(x-mapx-width/2), (int)(y-mapy-height/2), null);
    }
}
