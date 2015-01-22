import java.awt.*;
import java.util.ArrayList;
public class GodLevel{
  private Map map;
  private Button[] btns;
  private boolean buttonActivated;
  private int cursorType;
  private int enemyLevel, enemySpecies;
  private ArrayList<Enemy> e;
  private ArrayList<Berry> berries;
  private ArrayList<Rock> rocks;
  private AudioPlayer bgm;
  private double mx, my;
  private IconButton pause;
  private boolean paused = false;
  public static int WIDTH, HEIGHT;
  public GodLevel(){
    map = new Map("gamebg.jpg");
    WIDTH = map.getWidth();
    HEIGHT = map.getHeight();
    btns = new Button[]{
      new Button(GamePanel.WIDTH/8,50,100,50,"Rock"),
      new Button(GamePanel.WIDTH*2/8,50,100,50,"Berry"),
      new Button(GamePanel.WIDTH*3/8,50,100,50,"Enemy"),
      new Button(GamePanel.WIDTH*4/8,50,50,50,"<"),
      new Button(GamePanel.WIDTH*4/8+50,50,50,50,">"),
      new Button(GamePanel.WIDTH*5/8,50,100,25,"level up"),
      new Button(GamePanel.WIDTH*5/8,75,100,25,"level down")
    };
    cursorType = -1;
    /*cursorType governs what is selected
     * -1 = blank
     * 0 = rock
     * 1 = berry
     * 2 = spawn enemy
     */
    enemyLevel = 1;
    enemySpecies = 0;
    e = new ArrayList<Enemy>();
    berries = new ArrayList<Berry>();
    rocks = new ArrayList<Rock>();
    pause = new IconButton(10,10,"pause.png");
    bgm = new AudioPlayer("intro.mp3");
    bgm.loop();
  }
  public void update(){
    map.setPos(mx,my);
    if(!paused)
      for(int i=0;i<e.size();i++){
      Enemy en = e.get(i);
      en.update();
      en.setFollow(false);
      if(en.isDead()){
        e.remove(i);
      }
      else{
        for(int j=0;j<rocks.size();j++){
          if (en.getBoxRect().intersects(rocks.get(j).getBoxRect()))
            en.collide();
        }
        for(int j=0;j<e.size();j++){ //enemies attack each other
          Enemy en2 = e.get(j);
          if(en.getSpecies() != en2.getSpecies()){ //different species
            if(en.insight(en2.getScreenPos())){ //enemy sees other enemy
              en.setTarget(en2.getScreenPos());
              en.setFollow(true);
              en.attack(en2);
              if(en2.isDead()){ //enemy eat enemy
                en.consume(en2.getDNA());
                e.remove(j);
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
        for(int j=0;j<berries.size();j++){
          if((berries.get(j)).getRect().intersects(en.getBoxRect())){ //enemy eat berry
            en.consume(berries.get(j));
            berries.remove(j);
          }
          else if(en.insight((berries.get(j)).getScreenPos())){ //enemy sees berry
            en.setTarget((berries.get(j)).getScreenPos());
            en.setFollow(true);
          }
        }
      }
    }
  }
  public void draw(Graphics2D g){
    map.draw(g);
    for(int i=0;i<e.size();i++) e.get(i).draw(g);
    for(int i=0;i<berries.size();i++) (berries.get(i)).draw(g);
    for(int i=0;i<rocks.size();i++) (rocks.get(i)).draw(g);
    for(int i=0;i<btns.length;i++) btns[i].draw(g);
    pause.draw(g);
  }
  private boolean stuck(double newx,double newy){
    for(int i=0;i<rocks.size();i++)
      if((rocks.get(i)).checkStuck(newx, newy))
      return true;
    return false;
  }
  public void mouse(int mx,int my){
    this.mx = mx;
    this.my = my;
  }
  public void click(int mx,int my){
    buttonActivated = false;
    if(pause.click(mx,my)){
      paused = !paused;
      buttonActivated = true;
    }
    for(int i=0;i<btns.length;i++){
      if(btns[i].click(mx,my)){
        buttonActivated = true;
        switch(i){
          case 3:
            enemySpecies+=4;
            enemySpecies%=5;
            break;
          case 4:
            enemySpecies++;
            enemySpecies%=5;
            break;
          case 5:
            enemyLevel++;
            enemyLevel%=100;
            break;
          case 6:
            enemyLevel+=99;
            enemyLevel%=100;
            break;
          default: 
            cursorType = i;
            break;
        }
      }
    }
    if(!buttonActivated)
      switch(cursorType){
      case 0:
        rocks.add(new Rock(map,mx+map.getX(),my+map.getY()));
        break;
      case 1:
        berries.add(new Berry(map,mx+map.getX(),my+map.getY()));
        break;
      case 2:
        e.add(new Enemy(map,mx+map.getX(),my+map.getY(),enemySpecies,enemyLevel));
        break;
    }
  }
}
