import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
public class Player extends Organism{
    private double mx,my;
    private boolean attacking;
    public Player(double x,double y){
	super(x,y);
	try{
	    img = ImageIO.read(getClass().getResourceAsStream("triangle.png"));
	} catch(Exception e){
	    e.printStackTrace();
	}
	angle = 0.0;
	width = img.getWidth();
	height = img.getHeight();
	health = 10;
	dna = new DNA (3);
	health = (int)dna.getHealth();
	speed = (int)dna.getSpeed();
    }
    public void update(){
	angle = Math.atan2(y-my,x-mx)+Math.PI;
	
	x += speed*Math.cos(angle);
	if(x<width/2) x = width/2;
	if(x>GamePanel.WIDTH-width/2) x = GamePanel.WIDTH-width/2;
	y += speed*Math.sin(angle);
	if(y<height/2) y = height/2;
	if(y>GamePanel.HEIGHT-height/2) y = GamePanel.HEIGHT-height/2;
	attacking = false;
    }
    public void mouse(int mx,int my){
	this.mx = mx;
	this.my = my;
    }
    public void click(int mx,int my){
	attacking = true;
    }
    public void release(int mx,int my){
	attacking = false;
    }
    public Point2D getPos(){
	return new Point2D.Double(x,y);
    }
    public boolean isAttacking(){
	return attacking;
    }
}
