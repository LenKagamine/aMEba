import java.awt.*;
import javax.imageio.ImageIO;
public class Player extends Organism{
    private double mx,my;
    private boolean attacking;
    public Player(Map map,double x,double y){
	super(map,x,y);
	try{
	    img = ImageIO.read(getClass().getResourceAsStream("triangle.png"));
	} catch(Exception e){
	    e.printStackTrace();
	}
	angle = 0.0;
	width = img.getWidth();
	height = img.getHeight();
	health = 10;
	dna = new DNA(3);
	health = (int)dna.getHealth();
	speed = (int)dna.getSpeed();
    }
    public void update(){
	angle = Math.atan2(y-my,x-mx)+Math.PI;
	
	x += speed*Math.cos(angle);
	if(x<width/2) x = width/2;
	if(x>Level.WIDTH-width/2) x = Level.WIDTH-width/2;
	y += speed*Math.sin(angle);
	if(y<height/2) y = height/2;
	if(y>Level.HEIGHT-height/2) y = Level.HEIGHT-height/2;
	
	attacking = false;
    }
    public void mouse(int mx,int my){
	this.mx = mx+map.getX();
	this.my = my+map.getY();
    }
    public void click(int mx,int my){
	attacking = true;
    }
    public void release(int mx,int my){
	attacking = false;
    }
    public boolean isAttacking(){
	return attacking;
    }
}
