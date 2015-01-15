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
     Enemy en = (Enemy)e.get(i);
     en.update();
     if(en.isDead()){
  e.remove(i);
  spawnEnemy();
     }
     if(en.insight(p.getScreenPos())){
  en.setTarget(p.getScreenPos());
  en.setFollow(true);
  en.attack(p);
     }
     else{
  en.setFollow(false);
  for(int j=0;j<e.size();j++){ //enemies attack each other
      if(en.getSpecies() != ((Enemy)e.get(j)).getSpecies()){
   if(en.insight(((Enemy)e.get(j)).getScreenPos())){
       en.setTarget(((Enemy)e.get(j)).getScreenPos());
       en.setFollow(true);
       en.attack(((Enemy)e.get(j)));
       if(((Enemy)e.get(j)).isDead()){
    en.consume((((Enemy)e.get(j)).getDNA()));
    e.remove(j);
    spawnEnemy();
       }
   }
      }
      else{
        if (en.insight(((Enemy)e.get(j)).getScreenPos())){
          en.setTarget(((Enemy)e.get(j)).getScreenPos());
          en.setFollow(true);
          en.mate((Enemy)e.get(j));
        }
      }
  }
     }
     for(int j=0;j<berries.size();j++){ //enemies eat berry
  /*if(en.insight(((Berry)berries.get(j)).getScreenPos())){
      en.setTarget(((Berry)berries.get(j)).getScreenPos());
      en.setFollow(true);
  }*/
  if(((Berry)berries.get(j)).getRect().intersects(en.getBoxRect())){
      en.consume((Berry)berries.get(j));
      berries.remove(j);
  }
     }
     if(p.isAttacking()){ //player attacks enemy
  if(p.getBoxRect().intersects(en.getBoxRect())) en.hit((int)(p.getDNA().getAttack()));
  if(en.isDead()){
      p.consume(en.getDNA());
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
