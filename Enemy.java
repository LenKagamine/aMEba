import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
public class Enemy extends Organism{
    private double[] viewx = new double[3],viewy = new double[3];
    private boolean inview = false;
    private double targetx,targety;
    public Enemy(double x,double y){
	super(x,y);
	try{
	    img = ImageIO.read(getClass().getResourceAsStream("biter.png"));
	} catch(Exception e){
	    e.printStackTrace();
	}
	width = img.getWidth();
	height = img.getHeight();
	//health = 5;
	dna = new DNA (5);
	health = (int)dna.getHealth();
	speed = (int)dna.getSpeed();
    }
    public void update(){
	viewx[0] = x;
	viewy[0] = y;
	viewx[1] = (int)(x+200*(Math.cos(angle+Math.PI/6)));
	viewy[1] = (int)(y+200*Math.sin(angle+Math.PI/6));
	viewx[2] = (int)(x+200*(Math.cos(angle-Math.PI/6)));
	viewy[2] = (int)(y+200*Math.sin(angle-Math.PI/6));
	if(inview) angle = Math.atan2(targety-y,targetx-x);
	x += speed*Math.cos(angle);
	y += speed*Math.sin(angle);
	if(x<width/2||x>GamePanel.WIDTH-width/2||
	   y<height/2||y>GamePanel.HEIGHT-height/2)
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
    public void insight(Point2D p){
	targetx = p.getX();
	targety = p.getY();
	double alpha = ((viewy[1] - viewy[2])*(p.getX() - viewx[2]) + (viewx[2] - viewx[1])*(p.getY() - viewy[2])) /
		((viewy[1] - viewy[2])*(viewx[0] - viewx[2]) + (viewx[2] - viewx[1])*(viewy[0] - viewy[2])),
	beta = ((viewy[2] - viewy[0])*(p.getX() - viewx[2]) + (viewx[0] - viewx[2])*(p.getY() - viewy[2])) /
	       ((viewy[1] - viewy[2])*(viewx[0] - viewx[2]) + (viewx[2] - viewx[1])*(viewy[0] - viewy[2]));
	inview = (alpha>0)&&(beta>0)&&(alpha+beta<1);
    }
}
