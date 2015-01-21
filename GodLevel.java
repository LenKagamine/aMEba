import java.awt.*;
import java.util.ArrayList;
public class GodLevel{
  private Map map;
  private Button[] btns;
  private boolean buttonActivated;
  private int cursorType;
  private ArrayList e;
  private ArrayList berries;
  private ArrayList rocks;
  private long berrystart;
  private double mx, my;
  public static int WIDTH, HEIGHT;
  public GodLevel(){
    map = new Map("gamebg.jpg");
    WIDTH = map.getWidth();
    HEIGHT = map.getHeight();
    btns = new Button[]{
      new Button(GamePanel.WIDTH/10+50,50,100,50,"Rock"),
      new Button(GamePanel.WIDTH*2/10+50,50,100,50,"Berry")
    };
    cursorType = -1;
    /*cursorType governs what is selected
     * -1 = blank
     * 0 = rock
     * 1 = berry
     */
    //new changes whee
    e = new ArrayList();
    berries = new ArrayList();
    rocks = new ArrayList();
    berrystart = System.currentTimeMillis();
  }
  public void update(){
    map.setPos(mx,my);
    for(int i=0;i<e.size();i++){
      Enemy en = (Enemy)e.get(i);
      en.update();
      if(en.isDead()){
        e.remove(i);
      }
      for(int j=0;j<rocks.size();j++){
        if (en.getBoxRect().intersects(((Rock)rocks.get(j)).getBoxRect()))
          en.collide();
      }
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
            }
          }
          else{
            if(en.insight(((Enemy)e.get(j)).getScreenPos())){
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
    }
  }
  public void draw(Graphics2D g){
    map.draw(g);
    for(int i=0;i<e.size();i++) ((Enemy)e.get(i)).draw(g);
    for(int i=0;i<berries.size();i++) ((Berry)berries.get(i)).draw(g);
    for(int i=0;i<rocks.size();i++) ((Rock)rocks.get(i)).draw(g);
    for(int i=0;i<btns.length;i++) btns[i].draw(g);
  }
  private boolean stuck(double newx,double newy){
    for(int i=0;i<rocks.size();i++)
      if(((Rock)rocks.get(i)).checkStuck(newx, newy))
      return true;
    return false;
  }
  public void mouse(int mx,int my){
    this.mx = mx;
    this.my = my;
  }
  public void click(int mx,int my){
    buttonActivated = false;
    for(int i=0;i<btns.length;i++){
      if(btns[i].click(mx,my)){
        buttonActivated = true;
        switch(i){
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
    }
  }
}
