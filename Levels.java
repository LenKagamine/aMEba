import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Levels{
	public static int WIDTH, HEIGHT; //Level width and height
	protected Map map;
	protected ArrayList<Enemy> e; //Enemies
	protected ArrayList<Berry> berries; //Berries
	protected ArrayList<Rock> rocks; //Rocks
	protected IconButton pause; //Pause button
	protected boolean paused = false; //If paused or not
	protected Button quit; //Quit button
	protected AudioPlayer bgm; //Background music
	public Levels(String str){
		map = new Map(str); //Set map
		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();
	}
	public abstract void update(); //Abstract methods
	public abstract void draw(Graphics2D g);
	public abstract void mouse(int mx,int my);
	public abstract void click(int mx,int my);
	public boolean stuck(double newx,double newy){ //If coordinates are inside rock
		for(int i=0;i<rocks.size();i++)
			if(rocks.get(i).checkStuck(newx, newy))
				return true;
		return false;
	} 
}
