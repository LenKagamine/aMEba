import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.geom.Rectangle2D;
public abstract class Organism{
    protected BufferedImage img;
    protected double x,y;
    protected int width,height;
    protected double speed,angle;
    protected int health;
    protected DNA dna;
    public Organism(double x,double y){
	this.x = x;
	this.y = y;
	speed = Math.random()*3+2;
	angle = Math.random()*360;
    }
    public abstract void update();
    public void draw(Graphics g){
	g.setColor(Color.black);
	g.drawRect((int)(x-width/2),(int)(y-height/2),width,height);
	AffineTransform tx = AffineTransform.getRotateInstance(angle, width/2, height/2);
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	g.drawImage(op.filter(img,null),(int)(x-width/2),(int)(y-height/2),null);
    }
    public Rectangle2D getRect(){
	return new Rectangle2D.Double(x-width/2,y-height/2,width,height);
    }
    public void hit(int dmg){
	health = Math.max(health-dmg,0);
    }
    public boolean isDead(){
	return health<=0;
    }
    public DNA getDNA()
    {
        return dna;
    }
}
