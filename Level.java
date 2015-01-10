import java.awt.*;
import java.util.ArrayList;
public class Level{
    private Map map;
    private Player p;
    private ArrayList e;
    private ArrayList berries;
    private long berrystart;
    public static final int WIDTH = 1600, HEIGHT = 1000;
    public Level(){
	map = new Map();
	p = new Player(map,320,240,8);
	e = new ArrayList();
	berries = new ArrayList();
	berrystart = System.currentTimeMillis();
	for(int i=0;i<15;i++) spawnEnemy();
    }
    public void update(){
	map.setPos(p.getScreenPos());
	long berryelapsed = System.currentTimeMillis();
	if(berries.size()<=10 && berryelapsed-berrystart>3000){
	    berries.add(new Berry(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100));
	    berrystart = System.currentTimeMillis();
	}
	for(int i=0;i<e.size();i++){
	    ((Enemy)e.get(i)).update();
	    if(((Enemy)e.get(i)).insight(p.getScreenPos())){
		((Enemy)e.get(i)).setTarget(p.getScreenPos());
		((Enemy)e.get(i)).setFollow(true);
		((Enemy)e.get(i)).attack(p);
	    }
	    else ((Enemy)e.get(i)).setFollow(false);
	    for(int j=0;j<berries.size();j++){
		/*if(((Enemy)e.get(i)).insight(((Berry)berries.get(j)).getScreenPos())){
		    ((Enemy)e.get(i)).setTarget(((Berry)berries.get(j)).getScreenPos());
		    ((Enemy)e.get(i)).setFollow(true);
		}*/
		if(((Berry)berries.get(j)).getRect().intersects(((Enemy)e.get(i)).getBoxRect())){
		    ((Enemy)e.get(i)).consume((Berry)berries.get(j));
		    berries.remove(j);
		}
	    }
	    if(p.isAttacking()){
		if(p.getBoxRect().intersects(((Enemy)e.get(i)).getBoxRect())) ((Enemy)e.get(i)).hit((int)(p.getDNA().getAttack()));
		if(((Enemy)e.get(i)).isDead()){
		    p.consume(((Enemy)e.get(i)).getDNA());
		    e.remove(i);
		    spawnEnemy();
		}
	    }
	}
	for(int j=0;j<berries.size();j++){
	    if(((Berry)berries.get(j)).getRect().intersects(p.getBoxRect())){
		p.consume((Berry)berries.get(j));
		berries.remove(j);
	    }
	}
	p.update();
    }
    public void draw(Graphics g){
	map.draw(g);
	for(int i=0;i<e.size();i++) ((Enemy)e.get(i)).draw(g);
	for(int i=0;i<berries.size();i++) ((Berry)berries.get(i)).draw(g);
	p.draw(g);
    }
    public void spawnEnemy(){
	e.add(new Enemy(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100,(int)(Math.random()*5)));
    }
    public void mouse(int mx,int my){
	p.mouse(mx,my);
    }
    public void click(int mx,int my){
	p.click(mx,my);
    }
    public void release(int mx,int my){
	//p.release(mx,my);
    }
}
