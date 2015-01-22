import java.awt.*;
import java.util.ArrayList;
public class GodLevel extends Levels{
	private Button[] btns;
	private boolean buttonActivated;
	private int cursorType;
	private int enemyLevel, enemySpecies;
	private double mx, my;
	public GodLevel(){
		super("gamebg.jpg");
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
	    e = new ArrayList<Enemy>();
	    berries = new ArrayList<Berry>();
	    rocks = new ArrayList<Rock>();
		enemyLevel = 1;
		enemySpecies = 0;
		quit = new Button(GamePanel.WIDTH/2-50,GamePanel.HEIGHT/2+200,100,50,"Quit Game");
		pause = new IconButton(10,10,"pause.png");
		bgm = new AudioPlayer("intro.mp3");
		bgm.loop();
	}
	public void update(){
		if(!paused){
			map.setPos(mx,my);
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
	}
	public void draw(Graphics2D g){
		map.draw(g);
		for(int i=0;i<e.size();i++) e.get(i).draw(g);
		for(int i=0;i<berries.size();i++) (berries.get(i)).draw(g);
		for(int i=0;i<rocks.size();i++) (rocks.get(i)).draw(g);
		for(int i=0;i<btns.length;i++) btns[i].draw(g);
		pause.draw(g);
		if(paused){
			g.setColor(Color.cyan);
			g.fillRect(290, 90, GamePanel.WIDTH-580, GamePanel.HEIGHT-280);
			g.setColor(Color.black);
			g.fillRect(300, 100, GamePanel.WIDTH-600, GamePanel.HEIGHT-300);
			g.setColor(Color.white);
			g.setFont(new Font("Tahoma",Font.PLAIN,50));
			g.drawString("Paused", (GamePanel.WIDTH-g.getFontMetrics().stringWidth("Paused"))/2, 250);
			quit.draw(g);
		}
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
		if(paused){
			if(quit.click(mx,my)){
				bgm.stop();
				GamePanel.setLevel(0);
			}
		}
		else{
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
}
