import java.awt.*;
import java.util.ArrayList;
public class Level{
    private Map map;
    private Player p;
    private ArrayList e;
    private ArrayList berries;
    private ArrayList rocks;
    private long berrystart;
    //private AudioPlayer bgm;
    private IconButton pause;
    private boolean paused = false;
    private Button returner;
    private boolean dead = false;
    public static int WIDTH, HEIGHT;
    public Level(){
	map = new Map("gamebg.jpg");
	WIDTH = map.getWidth();
	HEIGHT = map.getHeight();
	returner = new Button((GamePanel.WIDTH/2-100),400,200,50,"Return to Menu");
	p = new Player(map,50,50,8);
	e = new ArrayList();
	berries = new ArrayList();
	rocks = new ArrayList();
	berrystart = System.currentTimeMillis();
	for(int i=0;i<25;i++) spawnEnemy();
	for(int i=0;i<50;i++) rocks.add(new Rock(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100));
	//bgm = new AudioPlayer("intro.mp3");
	//bgm.loop();
	pause = new IconButton(10,10,"pause.png");
    }
    public void update(){
	if(!dead && !paused){
	    map.setPos(p.getScreenPos()); //map scrolling
	    if(Math.random()*20>berries.size()) berries.add(new Berry(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100)); //berries
	    for(int i=0;i<e.size();i++){
		Enemy en = (Enemy)e.get(i);
		en.update();
		en.setFollow(false);
		if(en.isDead()){ //enemy is dead
		    e.remove(i);
		    spawnEnemy();
		}
		else{
		    for(int j=0;j<rocks.size();j++){ //enemy hit rock
			if(en.getBoxRect().intersects(((Rock)rocks.get(j)).getBoxRect()))
			    en.collide();
		    }
		    if(en.insight(p.getScreenPos())){ //enemy follows player
			en.setTarget(p.getScreenPos());
			en.setFollow(true);
			en.attack(p);
		    }
		    else{
			for(int j=0;j<e.size();j++){ //enemies attack each other
			    Enemy en2 = (Enemy)e.get(j);
			    if(en.getSpecies() != en2.getSpecies()){ //different species
				if(en.insight(en2.getScreenPos())){ //enemy sees other enemy
				    en.setTarget(en2.getScreenPos());
				    en.setFollow(true);
				    en.attack(en2);
				    if(en2.isDead()){ //enemy eat enemy
					en.consume(en2.getDNA());
					e.remove(j);
					spawnEnemy();
				    }
				}
				else{
				    if(en.insight(en2.getScreenPos())){
					en.setTarget(en2.getScreenPos());
					en.setFollow(true);
					en.mate(en2);
				    }
				}
			    }
			}
		    }
		    if(!en.following()){
			for(int j=0;j<berries.size();j++){
			    if(((Berry)berries.get(j)).getRect().intersects(en.getBoxRect())){ //enemy eat berry
				en.consume((Berry)berries.get(j));
				berries.remove(j);
			    }
			    else if(en.insight(((Berry)berries.get(j)).getScreenPos())){ //enemy sees berry
				en.setTarget(((Berry)berries.get(j)).getScreenPos());
				en.setFollow(true);
			    }
			}
		    }
		    if(p.isAttacking()){ //player attacks enemy
			if(p.getBoxRect().intersects(en.getBoxRect())){
			    en.hit(p.getDNA().getAttack()); //attack
			    if(en.isDead()){ //kill & eat
				p.consume(en.getDNA());
				e.remove(i);
				spawnEnemy();
			    }
			}
		    }
		}
	    }
	    p.update();
	    if(p.getHealth()<=0) dead = true;
	    for(int j=0;j<berries.size();j++){ //player eat berry
		if(((Berry)berries.get(j)).getRect().intersects(p.getBoxRect())){
		    p.consume((Berry)berries.get(j));
		    berries.remove(j);
		}
	    }
	    for(int j=0;j<rocks.size();j++){ //player hit rocks
		if(p.getBoxRect().intersects(((Rock)rocks.get(j)).getBoxRect()))
		    p.collide();
	    }
	}
    }
    public void restart(Graphics2D g){
	g.setColor(Color.cyan);
	g.fillRect(290, 90, GamePanel.WIDTH-580, GamePanel.HEIGHT-280);
	g.setColor(Color.black);
	g.fillRect(300, 100, GamePanel.WIDTH-600, GamePanel.HEIGHT-300);
	g.setColor(Color.white);
	g.setFont(new Font("Tahoma",Font.PLAIN,96));
	g.drawString("You Died", (GamePanel.WIDTH-g.getFontMetrics().stringWidth("You Died"))/2, 250);
	g.setFont(new Font("Tahoma",Font.PLAIN,48));
	g.drawString("Score:", (GamePanel.WIDTH-g.getFontMetrics().stringWidth("Score:"))/2, 350);
    }
    public void draw(Graphics2D g){
	map.draw(g);
	for(int i=0;i<e.size();i++) ((Enemy)e.get(i)).draw(g);
	for(int i=0;i<berries.size();i++) ((Berry)berries.get(i)).draw(g);
	for(int i=0;i<rocks.size();i++) ((Rock)rocks.get(i)).draw(g);
	p.draw(g);
	pause.draw(g);
	if(dead){
	    restart(g);
	    returner.draw(g);
	}
    }
    public void spawnEnemy(){
	double newx = Math.random()*(Level.WIDTH-200)+100;
	double newy = Math.random()*(Level.HEIGHT-200)+100;
	while(stuck(newx,newy)){
	    newx = Math.random()*(Level.WIDTH-200)+100;
	    newy = Math.random()*(Level.HEIGHT-200)+100;
	}
	if(Math.random()*5 <= 1) e.add(new Enemy(map,newx,newy,(int)(Math.random()*5),(int)(p.getDNA().getSize()+Math.random()*2)));
	else if(Math.random()*30 <= 1) e.add(new Enemy(map,newx,newy,(int)(Math.random()*5),(int)(p.getDNA().getSize()+Math.random()*2+3)));
	else e.add(new Enemy(map,newx,newy,(int)(Math.random()*5),(int)(Math.random()*(p.getDNA().getSize()))+1));
    }
    private boolean stuck(double newx,double newy){
	for(int i=0;i<rocks.size();i++)
	    if(((Rock)rocks.get(i)).checkStuck(newx, newy))
		return true;
	return false;
    }
    public void mouse(int mx,int my){
	p.mouse(mx,my);
    }
    public void click(int mx,int my){
	p.click(mx,my);
	if(pause.click(mx,my)) paused = !paused;
	if(dead && returner.click(mx,my)) GamePanel.setLevel(0);
    }
}
