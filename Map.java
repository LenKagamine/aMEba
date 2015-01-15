import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

public class Map{
    private double viewx,viewy;
    private double speed;
    private BufferedImage bg;
    private int xmax,ymax;
    public Map(){
	viewx = viewy = 0;
	speed = 0.1;
	xmax = Level.WIDTH-GamePanel.WIDTH;
	ymax = Level.HEIGHT-GamePanel.HEIGHT;
	try{
	    bg = ImageIO.read(getClass().getResourceAsStream("gamebg.jpg"));
	} catch(Exception e){
	    e.printStackTrace();
	}
    }
    public void draw(Graphics g){
	g.drawImage(bg.getSubimage((int)viewx,(int)viewy,GamePanel.WIDTH,GamePanel.HEIGHT),0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
    }
    public void setPos(Point2D player){
	double x = player.getX(), y = player.getY();
	if(x<150) viewx += (x-150)*speed;
	else if(x>GamePanel.WIDTH-150) viewx += (x-GamePanel.WIDTH+150)*speed;
	if(y<150) viewy += (y-150)*speed;
	if(y>GamePanel.HEIGHT-150) viewy += (y-GamePanel.HEIGHT+150)*speed;      
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
}
