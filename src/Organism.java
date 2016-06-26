import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public abstract class Organism extends MapObject{
	protected double speed,angle; // general stats
	protected double health;
	protected DNA dna;
	protected int species;
	private long start;
	
	public Organism(Map map,double x,double y,int species,int level){
		super(map,x,y);
		this.species = species;
		try{ // get species-specific image
			if (species == 0)
				img = ImageIO.read(getClass().getResourceAsStream("buggy.png"));
			else if (species == 1)
				img = ImageIO.read(getClass().getResourceAsStream("gooey.png"));
			else if (species == 2)
				img = ImageIO.read(getClass().getResourceAsStream("aqua.png"));
			else if (species == 3)
				img = ImageIO.read(getClass().getResourceAsStream("biter.png"));
			else if (species == 4)
				img = ImageIO.read(getClass().getResourceAsStream("diatom.png"));
			else if (species == 8) // Player
				img = ImageIO.read(getClass().getResourceAsStream("triangle.png"));
		} catch(Exception e){
			e.printStackTrace();
		} // create special DNA for the player
		if(species == 8) dna = new DNA(3,level);
		else dna = new DNA(species,level);
		health = dna.getHealth();
		speed = dna.getSpeed();

		width = (int)(img.getWidth()*(dna.getSize()+9)/10);
		height = (int)(img.getHeight()*(dna.getSize()+9)/10);
		boxwidth = width/2;
		boxheight = height/2;

		angle = Math.random()*360;

		start = System.currentTimeMillis();
	}
	
	public void update(){ // General Update method
		long elapsed = System.currentTimeMillis();
		if(elapsed-start>1000){
			start = System.currentTimeMillis();
			if(health > 0) health -= dna.getHunger();
		}
	}
	
	public void draw(Graphics2D g){ // scale, rotate, and draw
		mapx = map.getX();
		mapy = map.getY();
		if(x-mapx+width/2 > 0 && y-mapy+height/2 > 0 && x-mapx-width/2 < GamePanel.WIDTH && y-mapy-height/2 < GamePanel.HEIGHT){ 
			AffineTransform tx = AffineTransform.getRotateInstance(angle, width/2, height/2); // rotate
			tx.scale((dna.getSize()+9)/10,(dna.getSize()+9)/10); // scale
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter(img,null),(int)(x-mapx-width/2),(int)(y-mapy-height/2),null); // draw image
			g.setColor(Color.white); // draw level
			g.setFont(new Font("Tahoma", Font.BOLD, 20));
			g.drawString((int)(dna.getSize())+"",(int)(x-mapx-width/2),(int)(y-mapy+height/2));
		}
	}
	
	public void hit(double dmg){ // deal damage
		if(dmg > getDNA().getDefense()) health = Math.max(health-dmg+getDNA().getDefense(),0);
	}
	
	public int getSpecies(){ // get the species of the organism
		return species;
	}
	
	public boolean isDead(){ // return whether or not the organsim has died
		return health<=0;
	}
	
	public DNA getDNA(){ // return DNA file
		return dna;
	}
	
	public void setHealth(){ // Reset health to DNA max
		health = dna.getHealth();
	}
	
	public void consume(DNA dna2){ // amalgamate another DNA file with this one
		if(species == 8) dna.playerAdd(dna2);
		else dna.add(dna2);
		this.health += dna2.getHealth()/2;
		if(this.health >= dna.getHealth()) this.health = dna.getHealth();
		this.speed = this.dna.getSpeed();

		width = (int)(img.getWidth()*(dna.getSize()+9)/10);
		height = (int)(img.getHeight()*(dna.getSize()+9)/10);
		boxwidth = width/2;
		boxheight = height/2;
	}
	
	public void consume(Berry berry){ // replenish health by proper amount for a berry
		health += berry.recoverHealth();
		if(this.health >= dna.getHealth()) this.health = dna.getHealth();
	}
}
