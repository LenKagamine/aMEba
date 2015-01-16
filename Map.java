import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

public class Map{
    private double viewx,viewy;
    private double speed;
    private BufferedImage bg;
    private int xmax,ymax;
    private int width,height;
    public Map(String img){
	try{
	    bg = ImageIO.read(getClass().getResourceAsStream(img));
	    width = bg.getWidth();
	    height = bg.getHeight();
	} catch(Exception e){
	    e.printStackTrace();
	}
	xmax = width-GamePanel.WIDTH;
	ymax = height-GamePanel.HEIGHT;
	viewx = viewy = 0;
	speed = 0.1;
    }
    public void draw(Graphics g){
	g.drawImage(bg.getSubimage((int)viewx,(int)viewy,GamePanel.WIDTH,GamePanel.HEIGHT),0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
    }
    public void setPos(Point2D target){
	setPos(target.getX(),target.getY());
    }
    public void setPos(double x,double y){
	if(x<300) viewx += (x-300)*speed;
	else if(x>GamePanel.WIDTH-300) viewx += (x-GamePanel.WIDTH+300)*speed;
	if(y<300) viewy += (y-300)*speed;
	if(y>GamePanel.HEIGHT-300) viewy += (y-GamePanel.HEIGHT+300)*speed;      
	if(viewx<0) viewx = 0;
	if(viewx>xmax) viewx = xmax;
	if(viewy<0) viewy = 0;
	if(viewy>ymax) viewy = ymax;
    }
    public double getX(){
	return viewx;
    }
    public double getY(){
	return viewy;
    }
    public int getWidth(){
	return width;
    }
    public int getHeight(){
	return height;
    }
}
