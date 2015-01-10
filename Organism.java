import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
public abstract class Organism extends MapObject{
    protected double speed,angle;
    protected int health;
    protected DNA dna;
    private long start;
    public Organism(Map map,double x,double y){
	super(map,x,y);
	speed = Math.random()*3+2;
	angle = Math.random()*360;
	
	start = System.currentTimeMillis();
    }
    public void update(){
	long elapsed = System.currentTimeMillis();
	if(elapsed-start>1000){
	    start = System.currentTimeMillis();
	    health -= dna.getHunger();
	}
    }
    public void draw(Graphics g){
	mapx = map.getX();
	mapy = map.getY();
	g.setColor(Color.black);
	g.drawRect((int)(x-mapx-width/2),(int)(y-mapy-height/2),width,height);
	g.setColor(Color.red);
	g.drawRect((int)(x-mapx-boxwidth/2),(int)(y-mapy-boxheight/2),boxwidth,boxheight);
	AffineTransform tx = AffineTransform.getRotateInstance(angle, width/2, height/2);
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	g.drawImage(op.filter(img,null),(int)(x-mapx-width/2),(int)(y-mapy-height/2),null);
    }
    public void hit(int dmg){
	health = Math.max(health-dmg,0);
    }
    public boolean isDead(){
	return health<=0;
    }
    public DNA getDNA(){
	return dna;
    }
    public void consume(DNA dna2){
	dna.add(dna2);
	this.health += dna2.getHealth()/2;
	if(this.health >= dna.getHealth()) this.health = dna.getHealth();
	this.speed = this.dna.getSpeed();
    }
    public void consume(Berry berry){
	health += berry.recoverHealth();
	if(this.health >= dna.getHealth()) this.health = dna.getHealth();
    }
}
