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
	private int border;
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
		speed = 0.05;
		border = 500;
	}
	public void draw(Graphics2D g){
		g.drawImage(bg.getSubimage((int)viewx,(int)viewy,GamePanel.WIDTH,GamePanel.HEIGHT),0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
	}
	public void setPos(Point2D target){
		setPos(target.getX(),target.getY());
	}
	public void setPos(double x,double y){
		if(x<border) viewx += (x-border)*speed;
		else if(x>GamePanel.WIDTH-border) viewx += (x-GamePanel.WIDTH+border)*speed;
		if(y<border) viewy += (y-border)*speed;
		if(y>GamePanel.HEIGHT-border) viewy += (y-GamePanel.HEIGHT+border)*speed;      
		if(viewx<0) viewx = 0;
		if(viewx>xmax) viewx = xmax;
		if(viewy<0) viewy = 0;
		if(viewy>ymax) viewy = ymax;
	}
	public void setBorder(int b){
		border = b;
	}
	public void setSpeed(double s){
		speed = s;
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
