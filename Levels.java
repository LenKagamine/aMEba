import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Levels{
	public static int WIDTH, HEIGHT;
	protected Map map;
	protected ArrayList<Enemy> e;
	protected ArrayList<Berry> berries;
	protected ArrayList<Rock> rocks;
	protected IconButton pause;
	protected boolean paused = false;
	protected AudioPlayer bgm;
	public Levels(String str){
		map = new Map(str);
		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();
	}
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void mouse(int mx,int my);
	public abstract void click(int mx,int my);
	public boolean stuck(double newx,double newy){
		for(int i=0;i<rocks.size();i++)
			if(rocks.get(i).checkStuck(newx, newy))
				return true;
		return false;
	} 
}
