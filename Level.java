import java.awt.*;
import java.util.ArrayList;
public class Level{
    private Player p;
    private ArrayList e;
    private Map map;
    public static final int WIDTH = 2560, HEIGHT = 1600;
    public Level(){
	p = new Player(320,240);
	e = new ArrayList();
	for(int i=0;i<5;i++){
	    e.add(new Enemy(Math.random()*(GamePanel.WIDTH-200)+100,Math.random()*(GamePanel.HEIGHT-200)+100));
	}
	map = new Map();
    }
    public void update(){
	for(int i=0;i<e.size();i++){
	    ((Enemy)(e.get(i))).update();
	    ((Enemy)(e.get(i))).insight(p.getPos());
	    if(p.isAttacking()){
		if(p.getRect().intersects(((Enemy)(e.get(i))).getRect())) ((Enemy)(e.get(i))).hit((int)(p.getDNA().getAttack()));
		if(((Enemy)(e.get(i))).isDead()) e.remove(i);
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
	p.release(mx,my);
    }
}
