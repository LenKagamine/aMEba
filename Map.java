import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

public class Map{
    private int viewx,viewy;
    private int speed;
    private BufferedImage bg;
    public Map(){
	viewx = viewy = 0;
	speed = 5;
	try{
	    bg = ImageIO.read(getClass().getResourceAsStream("bg.png"));
	} catch(Exception e){
	    e.printStackTrace();
	}
    }
    public void draw(Graphics g){
	g.drawImage(bg.getSubimage(viewx,viewy,GamePanel.WIDTH,GamePanel.HEIGHT),0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
    }
    public void setPos(Point2D player,Point2D map){
	double x = player.getX()-map.getX(), y = player.getY()-map.getY();
	if(x>GamePanel.WIDTH-100) viewx+=speed;
	else if(x<100) viewx-=speed;
	if(viewx>Level.WIDTH-GamePanel.WIDTH) viewx = Level.WIDTH-GamePanel.WIDTH;
	else if(viewx<0) viewx = 0;
	
	if(y>GamePanel.HEIGHT-100) viewy+=speed;
	else if(y<100) viewy-=speed;
	if(viewy>Level.HEIGHT-GamePanel.HEIGHT) viewy = Level.HEIGHT-GamePanel.HEIGHT;
	else if(viewy<0) viewy = 0;
    }
    public int getX(){
	return viewx;
    }
    public int getY(){
	return viewy;
    }
}
