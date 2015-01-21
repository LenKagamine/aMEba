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
	private double mx, my;
	public static int WIDTH, HEIGHT;
	public GodLevel(){
		map = new Map("gamebg.jpg");
		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();
		btns = new Button[]{
				new Button(GamePanel.WIDTH/10+50,50,100,50,"Rock"),
				new Button(GamePanel.WIDTH*2/10+50,50,100,50,"Berry"),
				new Button(GamePanel.WIDTH*3/10+50,50,100,50,"Enemy")
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
	}
	public void update(){
		map.setPos(mx,my);
		for(int i=0;i<e.size();i++){
			Enemy en = e.get(i);
			en.update();
			if(en.isDead()){
				e.remove(i);
			}
			else{
				for(int j=0;j<rocks.size();j++){
					if (en.getBoxRect().intersects((rocks.get(j)).getBoxRect()))
						en.collide();
				}
				en.setFollow(false);
				for(int j=0;j<e.size();j++){ //enemies attack each other
					if(en.getSpecies() != (e.get(j)).getSpecies()){
						if(en.insight((e.get(j)).getScreenPos())){
							en.setTarget((e.get(j)).getScreenPos());
							en.setFollow(true);
							en.attack((e.get(j)));
							if((e.get(j)).isDead()){
								en.consume(((e.get(j)).getDNA()));
								e.remove(j);
							}
						}
						else{
							if(en.insight((e.get(j)).getScreenPos())){
								en.setTarget((e.get(j)).getScreenPos());
								en.setFollow(true);
								en.mate(e.get(j));
							}
						}
					}
				}
				for(int j=0;j<berries.size();j++){ //enemies eat berry
					/*if(en.insight((berries.get(j)).getScreenPos())){
           en.setTarget((berries.get(j)).getScreenPos());
           en.setFollow(true);
           }*/
					if((berries.get(j)).getRect().intersects(en.getBoxRect())){
						en.consume(berries.get(j));
						berries.remove(j);
					}
				}
			}
		}
	}
	public void draw(Graphics2D g){
		map.draw(g);
		for(int i=0;i<e.size();i++) (e.get(i)).draw(g);
		for(int i=0;i<berries.size();i++) (berries.get(i)).draw(g);
		for(int i=0;i<rocks.size();i++) (rocks.get(i)).draw(g);
		for(int i=0;i<btns.length;i++) btns[i].draw(g);
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
			case 2:
				e.add(new Enemy(map,mx+map.getX(),my+map.getY(),enemySpecies,enemyLevel));
				break;
			}
	}
}
