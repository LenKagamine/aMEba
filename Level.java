import java.awt.*;
import java.util.ArrayList;
public class Level{
    private Map map;
    private Player p;
    private ArrayList e;
    public static final int WIDTH = 2560, HEIGHT = 1600;
    public Level(){
	map = new Map();
	p = new Player(map,320,240);
	e = new ArrayList();
	for(int i=0;i<5;i++){
	    e.add(new Enemy(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100));
	}
    }
    public void update(){
	map.setPos(p.getPos(),p.getMapPos());
	for(int i=0;i<e.size();i++){
	    ((Enemy)e.get(i)).update();
	    ((Enemy)e.get(i)).insight(p.getPos(),p.getMapPos());
	    ((Enemy)e.get(i)).attack(p);
	    if(p.isAttacking()){
		if(p.getRect().intersects(((Enemy)(e.get(i))).getRect())) ((Enemy)(e.get(i))).hit((int)(p.getDNA().getAttack()));
		if(((Enemy)(e.get(i))).isDead()){
		    p.consume(((Enemy)e.get(i)).getDNA());
		    e.remove(i);
		}
	    }
	}
	p.update();
    }
    public void draw(Graphics g){
	map.draw(g);
	for(int i=0;i<e.size();i++) ((Enemy)(e.get(i))).draw(g);
	p.draw(g);
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
