import java.awt.*;
import java.awt.geom.Point2D;

public class Enemy extends Organism{
	private double[] viewx = new double[3],viewy = new double[3];
	private boolean inview = false;
	private int species = 5;
	private long atkstart,hitstart,elapsed; //these make it so that enemies have attack cooldown so they don't always attack
	private double targetx,targety;
	private double prevx, prevy;
	
	public Enemy(Map map,double x,double y,int species,int level){
		super(map,x,y,species,level);
		atkstart = System.currentTimeMillis();
		hitstart = -1;
		prevx = 100; 
		prevy = 100;
	}
	
	public void update(){
		super.update();
		prevx = x;//keeps prev position
		prevy = y;
		elapsed = System.currentTimeMillis();
		if(hitstart>=0 && elapsed-hitstart>250){ //may turn around
			angle += Math.PI;
			hitstart = -1;
		}
		viewx[0] = x-mapx; //calculates view triangle
		viewy[0] = y-mapy;
		viewx[1] = (int)(x-mapx+250*(Math.cos(angle+Math.PI/6)));
		viewy[1] = (int)(y-mapy+250*Math.sin(angle+Math.PI/6));
		viewx[2] = (int)(x-mapx+250*(Math.cos(angle-Math.PI/6)));
		viewy[2] = (int)(y-mapy+250*Math.sin(angle-Math.PI/6));
		if(inview) angle = Math.atan2(targety-y+mapy,targetx-x+mapx); //moves organism based on speed and angle
		else angle += (Math.random()-0.5)/8;
		x += speed*Math.cos(angle); 
		y += speed*Math.sin(angle);
		if(x<width/2||x>Levels.WIDTH-width/2||y<height/2||y>Levels.HEIGHT-height/2) angle++; //wiggles path
	}
	
	public void draw(Graphics2D g){ //draws enemy
		super.draw(g);
		g.setColor(Color.black);
		g.fillRect((int)(x-mapx-width/2),(int)(y-mapy+height/2),width,5);
		g.setColor(Color.red);
		g.fillRect((int)(x-mapx-width/2),(int)(y-mapy+height/2),(int)(1.0*health/dna.getHealth()*width),5);
	}
	
	public boolean insight(Point2D point){ //finds if enemy sees point
		double alpha = ((viewy[1] - viewy[2])*(point.getX() - viewx[2]) + (viewx[2] - viewx[1])*(point.getY() - viewy[2])) /
				((viewy[1] - viewy[2])*(viewx[0] - viewx[2]) + (viewx[2] - viewx[1])*(viewy[0] - viewy[2])),
				beta = ((viewy[2] - viewy[0])*(point.getX() - viewx[2]) + (viewx[0] - viewx[2])*(point.getY() - viewy[2])) /
				((viewy[1] - viewy[2])*(viewx[0] - viewx[2]) + (viewx[2] - viewx[1])*(viewy[0] - viewy[2]));
		return (alpha>0)&&(beta>0)&&(alpha+beta<1);
	}
	
	public void setTarget(Point2D point){ //sets new target
		targetx = point.getX();
		targety = point.getY();
	}
	
	public void setFollow(boolean follow){
		inview = follow;
	}
	
	public boolean following(){
		return inview;
	}
	
	public void attack(Organism org){ //attacks if not on cooldown
		if(elapsed-atkstart>100){
			atkstart = System.currentTimeMillis();
			if(getBoxRect().intersects(org.getBoxRect())){
				org.hit(dna.getAttack());
			}
		}
	}
	
	public void mate(Organism org){
		if(org.getSpecies() == species && getBoxRect().intersects(org.getBoxRect()))
			System.out.println("Mate");
	}
	
	public void hit(int dmg){
		super.hit(dmg);
		if(hitstart<0) hitstart = System.currentTimeMillis();
	}
	
	public void collide(){ //prevents organism from entering rock
		x = prevx;
		y = prevy;
		angle++;
	}
}
