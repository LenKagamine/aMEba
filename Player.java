import java.awt.*;
import javax.imageio.ImageIO;
public class Player extends Organism{
    private double mx,my;
    private double prevx, prevy;
    private boolean attacking;
    public Player(Map map,double x,double y,int species){
	super(map,x,y,species,1);
	angle = 0.0;
	prevx = 100;
	prevy = 100;
    }
    public void update(){
	super.update();
	angle = Math.atan2(y-mapy-my,x-mapx-mx)+Math.PI;
	prevx = x;
	prevy = y;
	
	x += speed*Math.cos(angle);
	if(x<width/2) x = width/2;
	if(x>Level.WIDTH-width/2) x = Level.WIDTH-width/2;
	y += speed*Math.sin(angle);
	if(y<height/2) y = height/2;
	if(y>Level.HEIGHT-height/2) y = Level.HEIGHT-height/2;
	
	attacking = false;
    }
    public void draw(Graphics2D g){
	super.draw(g);
	g.setColor(Color.black);
	g.fillRoundRect((int)(GamePanel.WIDTH/4)-5,(int)(GamePanel.HEIGHT-100)-5,(int)(GamePanel.WIDTH/2)+10,50+10,20,20);
	g.setColor(Color.red);
	g.fillRoundRect((int)(GamePanel.WIDTH/4),(int)(GamePanel.HEIGHT-100),(int)(1.0*health/dna.getHealth()*GamePanel.WIDTH/2),50,20,20);
	g.setColor(Color.yellow);
	g.drawString((int)health+"/"+(int)dna.getHealth(),(int)(GamePanel.WIDTH/2),(int)(GamePanel.HEIGHT-100)+25);
    }
    public void mouse(int mx,int my){
	this.mx = mx;
	this.my = my;
    }
    public void click(int mx,int my){
	attacking = true;
    }
    public boolean isAttacking(){
	return attacking;
    }
    public void collide(){
	x = prevx;
	y = prevy;
    }
    public int getHealth()
    {
        return (int)health;
    }
}
