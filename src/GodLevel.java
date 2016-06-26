import java.awt.*;
import java.io.*; // allows file access
import javax.swing.*;
import java.util.ArrayList;

public class GodLevel extends Levels{
	private Button[] btns;//Level Controls
	private boolean buttonActivated;
	private int cursorType;
	private int enemyLevel, enemySpecies;
	private double mx, my; // coordinates for moving the screen
	private JFileChooser fc = new JFileChooser(); // file things
	private File file;
	private boolean unsavedFile = true;
	
	public GodLevel(){
		super("gamebg.jpg");
		map.setBorder(100);
		map.setSpeed(0.2);
		btns = new Button[]{ // Create the buttons
				new Button(GamePanel.WIDTH*1/9,100,100,50,"Rock"),
				new Button(GamePanel.WIDTH*2/9,100,100,50,"Berry"),
				new Button(GamePanel.WIDTH*3/9,100,100,50,"Enemy"),
				new Button(GamePanel.WIDTH*4/9,100,50,50,"<"),
				new Button(GamePanel.WIDTH*4/9+50,100,50,50,">"),
				new Button(GamePanel.WIDTH*5/9,100,100,25,"level up"),
				new Button(GamePanel.WIDTH*5/9,125,100,25,"level down"),
				new Button(GamePanel.WIDTH*6/9,100,100,50,"Save"),
				new Button(GamePanel.WIDTH*7/9,100,100,50,"Save As"),
				new Button(GamePanel.WIDTH*8/9,100,100,50,"Open"),
		};
		cursorType = -1;
		/*cursorType governs what is selected
		 * -1 = blank
		 * 0 = rock
		 * 1 = berry
		 * 2 = spawn enemy
		 */
		e = new ArrayList<Enemy>(); //lists for map objects
		berries = new ArrayList<Berry>();
		rocks = new ArrayList<Rock>();
		enemyLevel = 1;
		enemySpecies = 0;
		quit = new Button(GamePanel.WIDTH/2-50,GamePanel.HEIGHT/2+200,100,50,"Quit Game");// pause menu controls
		pause = new IconButton(10,10,"pause.png");
		bgm = new AudioPlayer("gamebgm.mp3");//music
		bgm.loop();
	}
	
	public void update(){
		if(!paused){
			if(Math.random()*20>berries.size()) berries.add(new Berry(map,Math.random()*(Level.WIDTH-200)+100,Math.random()*(Level.HEIGHT-200)+100)); // update berries
			map.setPos(mx,my);
			for(int i=0;i<e.size();i++){ // for each enemy
				Enemy en = e.get(i);
				en.update();
				en.setFollow(false);
				if(en.isDead()){
					e.remove(i);
				}
				else{
					for(int j=0;j<rocks.size();j++){ // take care of rock collisions
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
									for (int v = 0; v< 2;v++)
										if (Math.random()*5 <= 1)
											spawnEnemy(en.getSpecies());
								}
							}
							else{ // mating
								if(en.insight(en2.getScreenPos())){
									en.setTarget(en2.getScreenPos());
									en.setFollow(true);
									en.mate(en2);
								}
							}
						}
					}
					for(int j=0;j<berries.size();j++){ // spawning with berries
						if((berries.get(j)).getRect().intersects(en.getBoxRect())){ //enemy eat berry
							en.consume(berries.get(j));
							if (Math.random()*10<=1) spawnEnemy(en.getSpecies());//mating
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
	
	public void spawnEnemy(int num){ // spawn random enemy
		boolean stuck = true;
		double newx = 100;
		double newy = 100;
		if (e.size() <= 150)
		{
			while (stuck)
			{
				stuck = false;
				newx = Math.random()*(Level.WIDTH-200)+100;
				newy = Math.random()*(Level.HEIGHT-200)+100;
				for (int i = 0; i< rocks.size(); i++)
				{
					if (rocks.get(i).checkStuck(newx, newy))
						stuck = true;
				}
			}
			e.add(new Enemy(map,newx,newy,num,1));
			e.get(e.size()-1).setHealth();
		}
	}
	
	public void draw(Graphics2D g){
		map.draw(g); // regular components
		for(int i=0;i<e.size();i++) e.get(i).draw(g);
		for(int i=0;i<berries.size();i++) (berries.get(i)).draw(g);
		for(int i=0;i<rocks.size();i++) (rocks.get(i)).draw(g);
		for(int i=0;i<btns.length;i++) btns[i].draw(g);
		pause.draw(g);
		if(paused){ // pause menu
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
	
	public void mouse(int mx,int my){ //update mouse
		this.mx = mx;
		this.my = my;
	}
	
	public void click(int mx,int my){ // spawning, buttons, etc. caused by a click
		buttonActivated = false;
		if(pause.click(mx,my)){ // pause button
			paused = !paused;
			buttonActivated = true;
		}
		if(paused){ // quit button
			if(quit.click(mx,my)){
				bgm.stop();
				GamePanel.setLevel(0);
			}
		}
		else{
			for(int i=0;i<btns.length;i++){ // regular buttons
				if(btns[i].click(mx,my)){
					buttonActivated = true;
					switch(i){
					case 3: // species down
						enemySpecies+=4;
						enemySpecies%=5;
						break;
					case 4: // species up
						enemySpecies++;
						enemySpecies%=5;
						break;
					case 5: // level up 
						enemyLevel++;
						enemyLevel%=100;
						break;
					case 6: // level down
						enemyLevel+=99;
						enemyLevel%=100;
						break;
					case 7: // save file
						try {
							saveFile();
						}
						catch (IOException ioe){
							System.out.println("Couldn't save file");
						}
						break;
					case 8: // save as file
						try {
							saveAsFile();
						}
						catch (IOException ioe){
							System.out.println("Couldn't save file");
						}
						break;
					case 9: // open file
						try {
							openFile();
						}
						catch (IOException ioe){
							System.out.println("Couldn't save file");
						}
						break;
					default: // set cursor type 
						cursorType = i;
						break;
					}
				}
			}
			if(!buttonActivated) //add things to the map
				switch(cursorType){
				case 0: // rocks
					rocks.add(new Rock(map,mx+map.getX(),my+map.getY()));
					break;
				case 1: // berries
					berries.add(new Berry(map,mx+map.getX(),my+map.getY()));
					break;
				case 2: // enemies
					e.add(new Enemy(map,mx+map.getX(),my+map.getY(),enemySpecies,enemyLevel));
					break;
				}
		}
	}
	
	public void saveAsFile () throws IOException{ // save as
		int returnVal = fc.showSaveDialog(null); // show save dialog box
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			unsavedFile = false;
			saveFile();
		}
	}
	
	public void saveFile () throws IOException{ // save file
		if (unsavedFile){
			saveAsFile();
		}
		else{
			PrintWriter fileout = new PrintWriter (new FileWriter (file.getName()));
			for (int r = 0; r < rocks.size(); r++) // print rock coordinates
				fileout.println(rocks.get(r).getPos().getX() + "," + rocks.get(r).getPos().getY());
			fileout.println("="); // print partition
			for (int r = 0; r < e.size(); r++) // print enemy coordinates, level and species
				fileout.println(e.get(r).getPos().getX() + "," + e.get(r).getPos().getY() + ";" + e.get(r).getSpecies() + ":" + e.get(r).getDNA().getSize());
			fileout.println("=");
			fileout.println();
			fileout.close ();
		}
	}
	
	public void openFile () throws IOException{
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) { // open dialog
			file = fc.getSelectedFile();
			unsavedFile = false;
			rocks.clear();
			e.clear();
			double x,y;
			int species,level;
			int div1, div2, div3;
			int section = 0;
			BufferedReader filein = new BufferedReader (new FileReader (file.getName()));
			String line;
			while ((line = filein.readLine ()) != null){ // file has not ended
				if(line.indexOf('=') != -1)
					section++;
				else if (section == 0){ // read rock coordinates
					div1 = line.indexOf(',');
					x = Double.parseDouble(line.substring(0,div1));
					y = Double.parseDouble(line.substring(div1+1,line.length()));
					rocks.add(new Rock (map, x, y));
				}
				else if (section == 1){ // read enemy info
					div1 = line.indexOf(',');
					div2 = line.indexOf(';');
					div3 = line.indexOf(':');
					x = Double.parseDouble(line.substring(0,div1));
					y = Double.parseDouble(line.substring(div1+1,div2));
					species = Integer.parseInt(line.substring(div2+1,div3));
					level = (int) Double.parseDouble(line.substring(div3+1,line.length()));
					e.add(new Enemy (map, x, y, species, level));
				}
				else
					break;
			}
			filein.close ();
		}
	}
}
