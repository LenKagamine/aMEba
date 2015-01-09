import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
public class Enemy extends Organism{
    private double[] viewx = new double[3],viewy = new double[3];
    private boolean inview = false;
    private int species = 5;
    private int cooldown = 200;
    private long atkstart,hitstart,elapsed;
    private double targetx,targety;
    public Enemy(Map map,double x,double y){
	super(map,x,y);
	try{
	    img = ImageIO.read(getClass().getResourceAsStream("biter.png"));
	} catch(Exception e){
	    e.printStackTrace();
	}
	width = img.getWidth();
	height = img.getHeight();
	boxwidth = width/2;
	boxheight = height/2;
	dna = new DNA (5);
	health = (int)dna.getHealth();
	speed = (int)dna.getSpeed();
	atkstart = System.currentTimeMillis();
	hitstart = -1;
    }
    public void update(){
	super.update();
	long elapsed = System.currentTimeMillis();
	if(hitstart>=0 && elapsed-hitstart>1500){
	    angle += Math.PI;
	    hitstart = -1;
	}
	viewx[0] = x-mapx;
	viewy[0] = y-mapy;
	viewx[1] = (int)(x-mapx+200*(Math.cos(angle+Math.PI/6)));
	viewy[1] = (int)(y-mapy+200*Math.sin(angle+Math.PI/6));
	viewx[2] = (int)(x-mapx+200*(Math.cos(angle-Math.PI/6)));
	viewy[2] = (int)(y-mapy+200*Math.sin(angle-Math.PI/6));
	if(inview) angle = Math.atan2(targety-y+mapy,targetx-x+mapx);
	else{
	    angle += (Math.random()-0.5)/8;
	}
	x += speed*Math.cos(angle);
	y += speed*Math.sin(angle);
	if(x<width/2||x>Level.WIDTH-width/2||
	   y<height/2||y>Level.HEIGHT-height/2)
	    angle++;
    }
    public void draw(Graphics g){
	int[] viewxi = new int[3],viewyi = new int[3];
	for(int i=0;i<3;i++){
	    viewxi[i] = (int)viewx[i];
	    viewyi[i] = (int)viewy[i];
	}
	if(inview){
	    g.setColor(Color.green);
	    g.fillPolygon(viewxi,viewyi,3);
	}
	g.setColor(Color.black);
	g.drawPolygon(viewxi,viewyi,3);
	super.draw(g);
    }
    public void insight(Point2D player){
	targetx = player.getX();
	targety = player.getY();
	double alpha = ((viewy[1] - viewy[2])*(targetx - viewx[2]) + (viewx[2] - viewx[1])*(targety - viewy[2])) /
		((viewy[1] - viewy[2])*(viewx[0] - viewx[2]) + (viewx[2] - viewx[1])*(viewy[0] - viewy[2])),
	beta = ((viewy[2] - viewy[0])*(targetx - viewx[2]) + (viewx[0] - viewx[2])*(targety - viewy[2])) /
	       ((viewy[1] - viewy[2])*(viewx[0] - viewx[2]) + (viewx[2] - viewx[1])*(viewy[0] - viewy[2]));
	inview = (alpha>0)&&(beta>0)&&(alpha+beta<1);
    }
    public void attack(Organism org){
	if(inview && elapsed-atkstart>cooldown){
	    atkstart = System.currentTimeMillis();
	    if(getRect().contains(org.getPos())){
		//rather wide attack box
		int damage = dna.getAttack() - org.getDNA().getDefense();
		if(damage >= 0) org.hit(damage);
	    }
	}
    }
    public void hit(int dmg){
	super.hit(dmg);
	if(hitstart<0) hitstart = System.currentTimeMillis();
    }
}
