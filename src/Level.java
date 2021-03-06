import java.awt.*;
import java.util.ArrayList;

public class Level extends Levels{
	private Player p;
	private Button returner;
	private boolean dead = false;
	private long timer, elapsed;
	private int time = 0;
	
	public Level(){
		super("gamebg.jpg");
		returner = new Button((GamePanel.WIDTH/2-100),400,200,50,"Return to Menu");
		p = new Player(map,50,50,8); //player character
		e = new ArrayList<Enemy>(); //various arraylists to store things
		berries = new ArrayList<Berry>(); 
		rocks = new ArrayList<Rock>();
		for(int i=0;i<25;i++) spawnEnemy();
		for(int i=0;i<50;i++) rocks.add(new Rock(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100));
		bgm = new AudioPlayer("gamebgm.mp3"); //music :)
		bgm.loop();
		pause = new IconButton(10,10,"pause.png");
		quit = new Button(GamePanel.WIDTH/2-50,GamePanel.HEIGHT/2+200,100,50,"Quit Game");
		timer = System.currentTimeMillis();
	}
	
	public void update(){
		if(!dead && !paused){
			map.setPos(p.getScreenPos()); //map scrolling
			elapsed = System.currentTimeMillis();
			if(Math.random()*20>berries.size()) berries.add(new Berry(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100)); //berries
			for(int i=0;i<e.size();i++){
				Enemy en = e.get(i);
				en.update();
				en.setFollow(false);
				if(en.isDead()){ //enemy is dead
					e.remove(i);
					spawnEnemy();
				}
				else{
					for(int j=0;j<rocks.size();j++){ //enemy hit rock
						if(en.getBoxRect().intersects(rocks.get(j).getBoxRect())) //checks for rock collision
							en.collide();
					}
					if(en.insight(p.getScreenPos())){ //enemy follows player
						en.setTarget(p.getScreenPos());
						en.setFollow(true);
						en.attack(p);
					}
					else{
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
							if(berries.get(j).getRect().intersects(en.getBoxRect())){ //enemy eat berry
								en.consume(berries.get(j));
								berries.remove(j);
							}
							else if(en.insight(berries.get(j).getScreenPos())){ //enemy sees berry
								en.setTarget(berries.get(j).getScreenPos());
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
			else if(elapsed - timer >= 200){
				time++;
				timer = System.currentTimeMillis();
			}
			for(int j=0;j<berries.size();j++){ //player eat berry
				if(berries.get(j).getRect().intersects(p.getBoxRect())){
					p.consume(berries.get(j));
					berries.remove(j);
				}
			}
			for(int j=0;j<rocks.size();j++){ //player hit rocks
				if(p.getBoxRect().intersects(rocks.get(j).getBoxRect()))
					p.collide();
			}
		}
	}
	
	public void restart(Graphics2D g){ //draws screen
		g.setColor(Color.red);
		g.fillRect(290, 90, GamePanel.WIDTH-580, GamePanel.HEIGHT-280);
		g.setColor(Color.black);
		g.fillRect(300, 100, GamePanel.WIDTH-600, GamePanel.HEIGHT-300);
		g.setColor(Color.white);
		g.setFont(new Font("Tahoma",Font.PLAIN,96));
		g.drawString("Game Over", (GamePanel.WIDTH-g.getFontMetrics().stringWidth("Game Over"))/2, 250);
		g.setFont(new Font("Tahoma",Font.PLAIN,48));
		g.drawString("Score: " + time, (GamePanel.WIDTH-g.getFontMetrics().stringWidth(" Score: " + time))/2, 350);
	}
	
	public void draw(Graphics2D g){ //draws all
		map.draw(g);
		for(int i=0;i<e.size();i++) e.get(i).draw(g);
		for(int i=0;i<berries.size();i++) berries.get(i).draw(g);
		for(int i=0;i<rocks.size();i++) rocks.get(i).draw(g);
		p.draw(g);
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
		else if(dead){ //if dead, draw death screen
			restart(g);
			returner.draw(g);
		}
	}
	
	public void spawnEnemy(){ //spawns enemy
		double newx = Math.random()*(Level.WIDTH-200)+100;
		double newy = Math.random()*(Level.HEIGHT-200)+100;
		while(stuck(newx,newy)){ //prevent enemy from spawning in rock
			newx = Math.random()*(Level.WIDTH-200)+100;
			newy = Math.random()*(Level.HEIGHT-200)+100;
		}
		if(Math.random()*5 <= 1) e.add(new Enemy(map,newx,newy,(int)(Math.random()*5),(int)(p.getDNA().getSize()+Math.random()*2)));
		else if(Math.random()*30 <= 1) e.add(new Enemy(map,newx,newy,(int)(Math.random()*5),(int)(p.getDNA().getSize()+Math.random()*2+3)));
		else e.add(new Enemy(map,newx,newy,(int)(Math.random()*5),(int)(Math.random()*(p.getDNA().getSize()))+1));
	}
	
	public void mouse(int mx,int my){
		p.mouse(mx,my);
	}
	
	public void click(int mx,int my){ 
		p.click(mx,my);
		if(pause.click(mx,my)) paused = !paused; //pauses/unpauses
		if(paused){
			if(quit.click(mx,my)){
				paused = false;
				dead = true;
			}
		}
		if(dead && returner.click(mx,my)){//return to menu
			bgm.stop();
			GamePanel.setLevel(0);
		}
	}
}
